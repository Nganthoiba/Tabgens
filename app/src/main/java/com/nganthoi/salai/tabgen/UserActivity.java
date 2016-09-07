package com.nganthoi.salai.tabgen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.Methods;
import activity.CmeLandingActivity;
import activity.NewsTabActivity;
import activity.ReferenceTabActivity;
import expandableLists.ExpandableListAdapter;
import expandableLists.ExpandableListDataPump;
import sharePreference.SharedPreference;

public class UserActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Context _context = this;
    SharedPreference sp;
    List<String> list;
    //chat data
     /*For Chatting List View*/
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    public final static String CHANNEL_NAME = "com.nganthoi.salai.tabgen.MESSAGE";
    public final static String TEAM_NAME = "team_name";
    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //set toolbar on conversation activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //chat layout coding
        expandableListView = (ExpandableListView) findViewById(R.id.chatExpandableListView);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        expandableListView.setIndicatorBounds(width - 120, width);
        showChatLists();


    }

    public void showChatLists() {
        /*Setting chat list View*/
        expandableListDetail = ExpandableListDataPump.getData(UserActivity.this);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(UserActivity.this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        //listview click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(UserActivity.this, ConversationActivity.class);
                intent.putExtra(TEAM_NAME, expandableListTitle.get(groupPosition));
                intent.putExtra(CHANNEL_NAME, expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
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
            Methods.showPopup(UserActivity.this);
            return true;
        } else if (id == R.id.cme) {
            Intent intent = new Intent(_context, CmeLandingActivity.class);
            startActivity(intent);
        } else if (id == R.id.refrence) {
            Intent intent = new Intent(_context, ReferenceTabActivity.class);
            startActivity(intent);
        } else if (id == R.id.news) {
            Intent intent = new Intent(_context, NewsTabActivity.class);
            startActivity(intent);
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
