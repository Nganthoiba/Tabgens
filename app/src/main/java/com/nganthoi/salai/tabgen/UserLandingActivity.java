package com.nganthoi.salai.tabgen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utils.Methods;
import Utils.PreferenceHelper;
import activity.CmeLandingActivity;
import activity.NewsTabActivity;
import activity.ReferenceTabActivity;
import fragment.NavigationDrawerFragment;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sharePreference.SharedPreference;


public class UserLandingActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Context _context = this;
    //ListView templateList;
    //ArrayAdapter<String> arrayAdapter;
    //TemplateAdapter templateAdapter;
    public final static String templateListExtra = "TEMPLATE_LIST";
    public final static String tabPosition = "TAB_POSITION";
    Button chat, cme, ref, news;
    Intent intent;
    SharedPreference sp;
    PreferenceHelper preferenceHelper;
    String role;//user role
    String team, user_id;//team name
    List<String> list;
    ArrayList<String> stringArray;
    NavigationDrawerFragment drwaerFragment;
    FragmentManager mFragmentManager;
    Toolbar toolbar;
    FragmentTransaction mFragmentTransaction;
    boolean fcmstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = new PreferenceHelper(_context);
        setContentView(R.layout.activity_user_landing);

        //set tool bar on activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set navigation drawer on home screen
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        drwaerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.fragment_drawer);
        drwaerFragment.setUp(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar, UserLandingActivity.this, 0);

        /* Getting textView IDs from the drawer */
        sp = new SharedPreference();
        // sp.saveFCMPreference(UserLandingActivity.this,false);
        String user_details = sp.getPreference(_context);
        try {
            JSONObject jsonObject = new JSONObject(user_details);

            role = jsonObject.getString("roles");
            user_id = jsonObject.getString("id");
            preferenceHelper.addString("LOGIN_USER_ID", user_id);

            team = sp.getTeamNamePreference(_context);
            System.out.println("Team Name: " + team + "\n");
            new GetTemplates(team).execute(user_id);

        } catch (JSONException e) {
            System.out.println("Exception :" + e.toString());
        }

        //FOR FCM NOTIFICATION
        if (fcmstatus == false) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FirebaseMessaging.getInstance().subscribeToTopic("test");
                    final String token = FirebaseInstanceId.getInstance().getToken();
                    if (token != null || !token.equalsIgnoreCase("")) {
                        Log.e("token", token);
                        String data = sendRegistrationToServer(token);
                        fcmstatus = true;
                        Log.e(" ok http data", data);
                    }
                }
            }).start();

        }
        //Getting Button Ids
        //click listener for going converserse activity
        chat = (Button) findViewById(R.id.landing_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, UserActivity.class);
                startActivity(intent);
            }
        });
        //click listener for going reference activity
        ref = (Button) findViewById(R.id.landing_reference);
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, ReferenceTabActivity.class);
                startActivity(intent);

            }
        });

        //click listener for going CME activity
        cme = (Button) findViewById(R.id.landing_cme);
        cme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, CmeLandingActivity.class);
                startActivity(intent);

            }
        });

        //click listener for going news activity
        news = (Button) findViewById(R.id.landing_news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, NewsTabActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        Methods.logout(UserLandingActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_landing, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Methods.showPopup(UserLandingActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class GetTemplates extends AsyncTask<String, String, List<String>> {
        String team_name;

        public GetTemplates(String team) {
            team_name = team;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(_context);
            progressDialog.setMessage("Loading your Templates");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... user_id) {
            publishProgress("");
            list = OrganisationDetails.getListOfTemplates(_context, user_id[0]);
            return list;
        }

        protected void onProgressUpdate(String str) {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<String> list) {
            //templateAdapter = new TemplateAdapter(UserLandingActivity.this,list);
            //templateList.setAdapter(templateAdapter);
            stringArray = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                stringArray.add(list.get(i));
            }
            progressDialog.dismiss();
        }
    }


    //-----------------********FCM REGISTRATION FUNCTION START*********----------------//

    public String sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

        preferenceHelper = new PreferenceHelper(this);
        String user_id = preferenceHelper.getString("LOGIN_USER_ID");
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("user_id", user_id)
                .add("token_id", token)
                .build();

        Request request = new Request.Builder()
                .url("http://128.199.111.18/TabGenAdmin/fcm_register.php")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.e("Response is", response.message());
            return response.body().string();

            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //-----------------********FCM REGISTRATION FUNCTION END*********----------------//


}
