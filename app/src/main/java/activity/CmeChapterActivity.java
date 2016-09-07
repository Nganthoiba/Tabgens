package activity;

import android.app.Dialog;
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

import com.google.gson.Gson;
import com.nganthoi.salai.tabgen.R;

import java.util.ArrayList;
import java.util.List;

import Utils.ConstantValues;
import Utils.Methods;
import Utils.NetworkHelper;
import Utils.PreferenceHelper;
import adapter.CmeChapterAdapter;
import models.cme_chapter_model.CmeChapterModel;
import models.cme_chapter_model.Filename;
import network.NetworkJob;
import network.NetworkRequest;
import network.NetworkResponse;
import threading.BackgroundJobClient;

/**
 * Created by atul on 28/7/16.
 */
public class CmeChapterActivity extends AppCompatActivity implements BackgroundJobClient {

    String detail_url, article_id, external_url;
    Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private List<Filename> cmeFileList = new ArrayList<>();
    CmeChapterAdapter cmeChapterAdapter;
    FloatingActionButton fab;
    Dialog cmechapteractivitydialog;
    private ProgressBar progressBar;
    private WebView webView;
    TextView attachmenttext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cme_chapter);

        //get data using intent
        detail_url = getIntent().getStringExtra("DETAIL_URL");
        article_id = getIntent().getStringExtra("ARTICLE_ID");
        external_url = getIntent().getStringExtra("EXTERNAL_URL");

        //set status bar color
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

        //get floating button id from xml
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        //if detail url is not null grt value from servers
        if (detail_url != null) {
            if (NetworkHelper.isOnline(this)) {
                getCmeArticle(detail_url);
            } else {
                Methods.showSnackbar("Sorry! You have lost the connection", this);
            }

        }

        //progress bar on screen
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        //get id and setting value on webview
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClientDemo());
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.getSettings().setJavaScriptEnabled(true);

        //load external url on webview
        if (external_url != null) {
            webView.loadUrl(external_url);
        }

        //Floating button click Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cmechapteractivitydialog = new Dialog(CmeChapterActivity.this);
                DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                cmechapteractivitydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cmechapteractivitydialog.setContentView(R.layout.activity_cmechapteradapter_attactmentdialog);
                cmechapteractivitydialog.getWindow().setLayout((8 * width) / 11, (10 * height) / 22);
                cmechapteractivitydialog.show();
                ListView lvChapterList = (ListView) cmechapteractivitydialog.findViewById(R.id.lvChapterList);
                attachmenttext = (TextView) cmechapteractivitydialog.findViewById(R.id.attachmenttext);
                //adapter for set value on expandable listview
                lvChapterList.setAdapter(cmeChapterAdapter);
                if (cmeFileList.size() > 0) {
                    attachmenttext.setText(cmeFileList.size() + " " + "Attachments");
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
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);

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


    private void getCmeArticle(String detail_url) {
        preferenceHelper = new PreferenceHelper(this);
        Methods.showProgressDialog(this, "Please wait..");
//        user_id=preferenceHelper.getString("LOGIN_USER_ID");
        NetworkRequest.Builder builder = new NetworkRequest.Builder(NetworkRequest.MethodType.GET, detail_url, ConstantValues.CME_CHAPTER_RESPONSE);
        NetworkRequest networkRequest = builder.build();
        builder.setContentType(NetworkRequest.ContentType.FORM_ENCODED);
        NetworkJob networkJob = new NetworkJob(this, networkRequest);
        networkJob.execute();
    }

    //methods for  get response from server
    @Override
    public void onBackgroundJobComplete(int requestCode, Object result) {
        Methods.closeProgressDialog();
//        try {
        if (result != null) {
            if (ConstantValues.CME_CHAPTER_RESPONSE == requestCode) {
                CmeChapterModel newsModel = new Gson().fromJson(((NetworkResponse) result).getResponseString(), CmeChapterModel.class);
                if (newsModel != null) {
                    cmeFileList = newsModel.getResponse().get(0).getFilenames();
                    if (cmeFileList.size() > 0) {
                        cmeChapterAdapter = new CmeChapterAdapter(this, 0, cmeFileList);
                        fab.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


    }

    @Override
    public void onBackgroundJobAbort(int requestCode, Object reason) {
        Methods.closeProgressDialog();
    }

    @Override
    public void onBackgroundJobError(int requestCode, Object error) {
        Methods.closeProgressDialog();
    }

    @Override
    public boolean needAsyncResponse() {
        return true;
    }

    @Override
    public boolean needResponse() {
        return true;
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
