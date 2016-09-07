package activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.nganthoi.salai.tabgen.R;
import com.nganthoi.salai.tabgen.UserActivity;

import java.util.ArrayList;

import Utils.ConstantValues;
import Utils.Methods;
import Utils.NetworkHelper;
import Utils.PreferenceHelper;
import adapter.CmeAdapter;
import models.cmetabmodel.CmeTabModel;
import models.cmetabmodel.OrgUnit;
import network.NetworkJob;
import network.NetworkRequest;
import network.NetworkResponse;
import threading.BackgroundJobClient;

/**
 * Created by atul on 6/7/16.
 */
public class CmeLandingActivity extends AppCompatActivity implements BackgroundJobClient {
    ExpandableListView expandableListView;
    ArrayList<OrgUnit> orgUnitList = new ArrayList<>();
    CmeAdapter cmeAdapter;
    View cmeView;
    PreferenceHelper preferenceHelper;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cme_layout);
      //set system bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        } else {
        }

        //set toolbar on activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewCME);

        //function for get data from server
        if (NetworkHelper.isOnline(this)) {
            getCmeList();
        } else {
            Methods.showSnackbar("Please check your internet connection..", this);
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        expandableListView.setIndicatorBounds(width - 120, width);



    }


    private void getCmeList() {
        preferenceHelper = new PreferenceHelper(this);
        Methods.showProgressDialog(this, "Please wait..");
        NetworkRequest.Builder builder = new NetworkRequest.Builder(NetworkRequest.MethodType.GET, ConstantValues.CME_TAB_URL + "" + preferenceHelper.getString("LOGIN_USER_ID"), ConstantValues.CME_TAB_RESPONSE);
        NetworkRequest networkRequest = builder.build();
        builder.setContentType(NetworkRequest.ContentType.FORM_ENCODED);
        NetworkJob networkJob = new NetworkJob(this, networkRequest);
        networkJob.execute();
    }

    @Override
    public void onBackgroundJobComplete(int requestCode, Object result) {
        Methods.closeProgressDialog();
        if (result != null) {
            if (ConstantValues.CME_TAB_RESPONSE == requestCode) {
                CmeTabModel cmeTabModel = new Gson().fromJson(((NetworkResponse) result).getResponseString(), CmeTabModel.class);
                if (cmeTabModel != null) {
                    orgUnitList = (ArrayList<OrgUnit>) cmeTabModel.getResponse().getOrgUnits();
                    if (orgUnitList.size() > 0) {
                        cmeAdapter = new CmeAdapter(this, orgUnitList);
                        expandableListView.setAdapter(cmeAdapter);
                    }
                    Log.v("CmeFragment", "CmeFragment:::" + cmeTabModel.getResponse().getOrgUnits().get(0).getName());
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cme_menu, menu);
        return true;
    }

    //set action bar component event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Methods.showPopup(CmeLandingActivity.this);
            return true;
        } else if (id == R.id.converse) {
            Intent intent = new Intent(CmeLandingActivity.this, UserActivity.class);
            startActivity(intent);
        } else if (id == R.id.refrence) {
            Intent intent = new Intent(CmeLandingActivity.this, ReferenceTabActivity.class);
            startActivity(intent);
        } else if (id == R.id.news) {
            Intent intent = new Intent(CmeLandingActivity.this, NewsTabActivity.class);
            startActivity(intent);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
