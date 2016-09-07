package activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nganthoi.salai.tabgen.R;

import java.util.ArrayList;
import java.util.List;

import Utils.ConstantValues;
import Utils.Methods;
import Utils.NetworkHelper;
import Utils.PreferenceHelper;
import adapter.CmeFlipperAdapter;
import adapter.FlipAdapter;
import models.cme_tab_model.CmeFlipModel;
import models.cme_tab_model.Response;
import network.NetworkJob;
import network.NetworkRequest;
import network.NetworkResponse;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;
import sharePreference.SharedPreference;
import threading.BackgroundJobClient;

/**
 * Created by atul on 28/7/16.
 */
public class CmeFlipperActivity extends AppCompatActivity implements BackgroundJobClient, FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {

    private FlipView mFlipView;
    private CmeFlipperAdapter mAdapter;
    String user_id;
    List<Response> outputLiist = new ArrayList<>();
    PreferenceHelper preferenceHelper;
    Toolbar toolbar;
    String tab_id;
    TextView nodatafound;
    SharedPreference sp;
    String token_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize shared prefrence class
        sp = new SharedPreference();
        preferenceHelper = new PreferenceHelper(this);

        //set layout on activity
        setContentView(R.layout.activity_news_flipper);
        token_id = sp.getTokenPreference(CmeFlipperActivity.this);
        Log.e("cme article",ConstantValues.CME_FLIPPER_ITEM + "" +tab_id+"&"+"token=" + token_id);
        //get data using intent
        tab_id = getIntent().getStringExtra("TAB_ID");
        Log.v("TAB_ID", "TAB_ID:::" + tab_id);

        //set status bar on activity
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
        nodatafound = (TextView) findViewById(R.id.nodatafound);
        nodatafound.setVisibility(View.GONE);

        //function for get data from server
        if (NetworkHelper.isOnline(this)) {
            getCmeArticle();
        } else {
            Methods.showSnackbar("Sorry! You have lost the connection", this);
        }

        mFlipView = (FlipView) findViewById(R.id.flip_view);

    }

    private void getCmeArticle() {
        token_id = sp.getTokenPreference(CmeFlipperActivity.this);
        preferenceHelper = new PreferenceHelper(this);
        Methods.showProgressDialog(this, "Please wait..");
        user_id = preferenceHelper.getString("LOGIN_USER_ID");
        Log.e("token id", token_id);
        NetworkRequest.Builder builder = new NetworkRequest.Builder(NetworkRequest.MethodType.GET, ConstantValues.CME_FLIPPER_ITEM + "" +tab_id+"&"+"token=" + token_id, ConstantValues.NEWS_ARTICLE_RESPONSE);
        NetworkRequest networkRequest = builder.build();
        builder.setContentType(NetworkRequest.ContentType.FORM_ENCODED);
        NetworkJob networkJob = new NetworkJob(this, networkRequest);
        networkJob.execute();
    }

    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {

//        mAdapter.
//        if(position > mFlipView.getPageCount()-3 && mFlipView.getPageCount()<30){
//            mAdapter.addItems(5);
//        }

    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
    }

    //get response from server
    @Override
    public void onBackgroundJobComplete(int requestCode, Object result) {
        Methods.closeProgressDialog();
        try {
            if (result != null) {
                if (ConstantValues.NEWS_ARTICLE_RESPONSE == requestCode) {
                    CmeFlipModel newsModel = new Gson().fromJson(((NetworkResponse) result).getResponseString(), CmeFlipModel.class);
                    if (newsModel != null) {
                        outputLiist = newsModel.getResponse();
                        if (outputLiist != null) {
                            if (outputLiist.size() > 0) {
                                mAdapter = new CmeFlipperAdapter(this, outputLiist);
//                        mAdapter.setCallback(this);
                                mFlipView.setAdapter(mAdapter);
                                mFlipView.setOnFlipListener(this);
                                mFlipView.peakNext(false);
                                mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
                                //        mFlipView.setEmptyView(findViewById(R.id.empty_view));
                                mFlipView.setOnOverFlipListener(this);

                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            nodatafound.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onBackgroundJobAbort(int requestCode, Object reason) {
        Methods.closeProgressDialog();
        Methods.showSnackbar("Sorry! You have lost the connection", this);
    }

    @Override
    public void onBackgroundJobError(int requestCode, Object error) {
        Methods.closeProgressDialog();
        Methods.showSnackbar("Sorry! You have lost the connection", this);
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

    @Override
    public boolean needAsyncResponse() {
        return true;
    }

    @Override
    public boolean needResponse() {
        return true;
    }
}


