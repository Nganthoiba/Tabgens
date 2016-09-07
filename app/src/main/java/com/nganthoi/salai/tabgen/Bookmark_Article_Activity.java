package com.nganthoi.salai.tabgen;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Utils.Methods;
import adapter.Bookmark_article_adapter;
import bean.Bookmark_Article_Bean;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sharePreference.SharedPreference;

public class Bookmark_Article_Activity extends AppCompatActivity {

    Toolbar toolbar;

   static  SharedPreference sp;
    Bookmark_Article_Bean bookmark_article_bean;
    ArrayList<Bookmark_Article_Bean> bookmarkarray;
    Bookmark_article_adapter bookmark_article_adapter;
    ListView bookmarklistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark__article_);

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


        sp = new SharedPreference();
        bookmark_article_bean=new Bookmark_Article_Bean();
        bookmarklistview= (ListView) findViewById(R.id.bookmarklistview);

        new Thread(new Runnable() {

            JSONObject jsonObject = null;
            @Override
            public void run() {

                try {

                 String response=bookmarkArticleServer(Bookmark_Article_Activity.this);
                    jsonObject = new JSONObject(response);
                    Log.e("response",response);
                    JSONArray jsonarray=jsonObject.getJSONArray("response");
                    bookmarkarray = new ArrayList<Bookmark_Article_Bean>();
                    for (int i = 0; i < jsonarray.length(); i++) {

                        JSONObject jsonChildNode = jsonarray.getJSONObject(i);
                        String id = jsonChildNode.getString("Id").toString();
                        String createat = jsonChildNode.getString("CreateAt").toString();
                        String name = jsonChildNode.getString("Name").toString();
                        String articledetail = jsonChildNode.getString("article_detail").toString();
                        String imageurl = jsonChildNode.getString("images_url").toString();
                        String detailurl = jsonChildNode.getString("detail_url").toString();
                        String nooflike = jsonChildNode.getString("no_of_likes").toString();
                        String bookmarkbyyou = jsonChildNode.getString("is_bookmarked_by_you").toString();
                        String likebyyou = jsonChildNode.getString("is_liked_by_you").toString();

                       Bookmark_Article_Bean beanarticle=new Bookmark_Article_Bean(id,name,articledetail,imageurl,detailurl,nooflike);
                        bookmarkarray.add(beanarticle);

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookmark_article_adapter=new Bookmark_article_adapter(Bookmark_Article_Activity.this,bookmarkarray);
                            bookmarklistview.setAdapter(bookmark_article_adapter);
                        }
                    });





                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //----------------*******************ARTICLE FATCH DATA FROM SERVER START***************--------------------//


    public static String bookmarkArticleServer(Context activity) {
        // TODO: Implement this method to send token to your app server.


        final String token = sp.getTokenPreference(activity);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://128.199.111.18/TabGenAdmin/getBookmarkedArticles.php?token="+token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();

            // Do something with the response.
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    //----------------*******************ARTICLE FATCH DATA FROM SERVER END****************--------------------//


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
