package activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nganthoi.salai.tabgen.R;

import java.util.ArrayList;

import Utils.ConstantValues;
import Utils.PreferenceHelper;
import adapter.CmeNewsAttachmentAdapter;
import models.news_model.Attachment;

/**
 * Created by atul on 22/7/16.
 */
public class NewsDetailWebviewActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;
    String web_url, news_id;
    Toolbar toolbar;
    FloatingActionButton fab;
    CmeNewsAttachmentAdapter cmeChapterAdapter;
    Dialog cmechapteractivitydialog;
    PreferenceHelper preferenceHelper;
    TextView attachmenttext;
    Bundle bundle;
    ArrayList<Attachment> attachments;
    Context context;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //get data using intent
        bundle = new Bundle();
        bundle = getIntent().getBundleExtra("BUNDLE");
        attachments = (ArrayList<Attachment>) bundle.getSerializable("List_attachment");
        web_url = getIntent().getStringExtra("WEB_URL");
        news_id = getIntent().getStringExtra("NEWS_ID");

        //set xml on activity
        setContentView(R.layout.activity_news_detail_webview);

         //set system bar on activithy
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        } else {
        }

        //set tool bar on activity
        toolbar = (Toolbar) findViewById(R.id.toolbarConversation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       //floating action button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //set up progress bar on activity
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        //set web view on activity
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClientDemo());
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.getSettings().setJavaScriptEnabled(true);
        if (web_url != null) {
            if (news_id != null) {
                webView.loadUrl(ConstantValues.NEWS_DETAIL_PAGE + "" + news_id);
            }
        }

        if (attachments.size() > 0) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }


        //floating button click listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cmechapteractivitydialog = new Dialog(NewsDetailWebviewActivity.this);
                DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                cmechapteractivitydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialogchooseplan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cmechapteractivitydialog.setContentView(R.layout.activity_cmechapteradapter_attactmentdialog);

                cmechapteractivitydialog.getWindow().setLayout((8 * width) / 11, (10 * height) / 22);
                cmechapteractivitydialog.show();
                ListView lvChapterList = (ListView) cmechapteractivitydialog.findViewById(R.id.lvChapterList);
                attachmenttext = (TextView) cmechapteractivitydialog.findViewById(R.id.attachmenttext);
                cmeChapterAdapter = new CmeNewsAttachmentAdapter(context, 0, attachments);
                lvChapterList.setAdapter(cmeChapterAdapter);
                if (attachments.size() > 0) {
                    attachmenttext.setText(attachments.size() + " " + "Attachments");
                }


            }
        });


    }


    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }

    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
