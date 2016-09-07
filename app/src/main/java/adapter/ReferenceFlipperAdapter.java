package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nganthoi.salai.tabgen.BuildConfig;
import com.nganthoi.salai.tabgen.R;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.Methods;
import activity.CmeChapterActivity;
import models.cme_tab_model.Response;
import sharePreference.SharedPreference;

/**
 * Created by atul on 1/8/16.
 */
public class ReferenceFlipperAdapter extends BaseAdapter implements View.OnClickListener {

    public interface NewsCallback {
        public void onPageRequested(int page);
    }

    String ip, token;
    private LayoutInflater inflater;
    private NewsCallback callback;
    Context context;
    private List<Response> items = new ArrayList<Response>();

    public ReferenceFlipperAdapter(Context context, List<models.cme_tab_model.Response> items) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.context = context;
        SharedPreference sp = new SharedPreference();
        ip = sp.getServerIP_Preference(context);
        token = sp.getTokenPreference(context);
    }

    public void setCallback(NewsCallback callback) {
        this.callback = callback;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
//		items.get(position).getId()
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    //if  1,2,3 article on activity.then there is one article avaiable then case one is working,if 2 then case 2 and if 3 then case 3 is work
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
//        try {
        if (convertView == null) {
            holder = new ViewHolder();
            switch (items.get(position).getItemCount()) {
                case 1:
                    Log.v("ITEMS", "POSITION :" + items.get(position).getItemCount());
                    convertView = inflater.inflate(R.layout.news_item3, null);
                    //TODO set a text with the id as well
                    holder.imgContentSingle = (ImageView) convertView.findViewById(R.id.imgContentSingle);
                    holder.txtHeaderSingle = (TextView) convertView.findViewById(R.id.txtHeaderSingle);
                    holder.txtHeadlinesSingle = (TextView) convertView.findViewById(R.id.txtHeadlinesSingle);
                    holder.cardviewsix = (CardView) convertView.findViewById(R.id.cardviewsix);
                    holder.newsdetailwebviewclickfifth = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclickfifth);
                    holder.share_news_fifth = (ImageView) convertView.findViewById(R.id.share_news_fifth);
                    holder.like_news_fifth = (ImageView) convertView.findViewById(R.id.like_news_fifth);
                    holder.bookmark_news_fifth = (ImageView) convertView.findViewById(R.id.bookmark_news_fifth);


                    break;
                case 2:
                    Log.v("ITEMS", "POSITION :" + items.get(position).getItemCount());
                    convertView = inflater.inflate(R.layout.news_item1, null);
                    //TODO set a text with the id as well
                    holder.imgContent = (ImageView) convertView.findViewById(R.id.imgContent);
                    holder.txtHeader = (TextView) convertView.findViewById(R.id.txtHeader);
                    holder.txtHeadlines = (TextView) convertView.findViewById(R.id.txtHeadlines);
                    holder.imgContent1 = (ImageView) convertView.findViewById(R.id.imgContent1);
                    holder.txtHeader1 = (TextView) convertView.findViewById(R.id.txtHeader1);
                    holder.txtHeadlines1 = (TextView) convertView.findViewById(R.id.txtHeadlines1);
                    holder.cardfourth = (CardView) convertView.findViewById(R.id.cardfourth);
                    holder.cardfive = (CardView) convertView.findViewById(R.id.cardfive);
                    holder.newsdetailwebviewclickfourth = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclickfourth);
                    holder.newsdetailwebviewclickthird = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclickthird);
                    holder.share_news_third = (ImageView) convertView.findViewById(R.id.share_news_third);
                    holder.share_news_fourth = (ImageView) convertView.findViewById(R.id.share_news_fourth);
                    holder.like_news_third = (ImageView) convertView.findViewById(R.id.like_news_third);
                    holder.like_news_fourth = (ImageView) convertView.findViewById(R.id.like_news_fourth);
                    holder.bookmark_news_third = (ImageView) convertView.findViewById(R.id.bookmark_news_third);
                    holder.bookmark_news_fourth = (ImageView) convertView.findViewById(R.id.bookmark_news_fourth);
                    break;
                case 3:
                    Log.v("ITEMS", "POSITION :" + items.get(position).getItemCount());
                    convertView = inflater.inflate(R.layout.news_item2, null);
                    //TODO set a text with the id as well
                    holder.imgContent2 = (ImageView) convertView.findViewById(R.id.imgContent2);
                    holder.txtHeader2 = (TextView) convertView.findViewById(R.id.txtHeader2);
                    holder.txtHeadlines2 = (TextView) convertView.findViewById(R.id.txtHeadlines2);
                    holder.imgContent3 = (ImageView) convertView.findViewById(R.id.imgContent3);
                    holder.txtHeader3 = (TextView) convertView.findViewById(R.id.txtHeader3);
                    holder.txtHeadlines3 = (TextView) convertView.findViewById(R.id.txtHeadlines3);
                    holder.imgContent4 = (ImageView) convertView.findViewById(R.id.imgContent4);
                    holder.txtHeader4 = (TextView) convertView.findViewById(R.id.txtHeader4);
                    holder.txtHeadlines4 = (TextView) convertView.findViewById(R.id.txtHeadlines4);
                    holder.newsdetailwebviewclicksecond = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclicksecond);
                    holder.newsdetailwebviewclickfirst = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclickfirst);
                    holder.newsdetailwebviewclick = (LinearLayout) convertView.findViewById(R.id.newsdetailwebviewclick);
                    holder.like_news = (ImageView) convertView.findViewById(R.id.like_news);
                    holder.bookmark_news = (ImageView) convertView.findViewById(R.id.bookmark_news);
                    holder.share_news = (ImageView) convertView.findViewById(R.id.share_news);
                    holder.like_news_first = (ImageView) convertView.findViewById(R.id.like_news_first);
                    holder.bookmark_news_first = (ImageView) convertView.findViewById(R.id.bookmark_news_first);
                    holder.share_news_first = (ImageView) convertView.findViewById(R.id.share_news_first);
                    holder.like_news_second = (ImageView) convertView.findViewById(R.id.like_news_second);
                    holder.bookmark_news_second = (ImageView) convertView.findViewById(R.id.bookmark_news_second);
                    holder.share_news_second = (ImageView) convertView.findViewById(R.id.share_news_second);
                    holder.cardone = (CardView) convertView.findViewById(R.id.cardone);
                    holder.cardsecond = (LinearLayout) convertView.findViewById(R.id.cardsecond);
                    holder.cardthird = (LinearLayout) convertView.findViewById(R.id.cardthird);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OkHttpClient picassoClient1 = new OkHttpClient();
        picassoClient1.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        });
        switch (items.get(position).getItemCount()) {
            case 1:
                holder.txtHeaderSingle.setText(items.get(position).getItems().get(0).getTextualContent() + "");
                holder.txtHeadlinesSingle.setText(items.get(position).getItems().get(0).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)
                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(0).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContentSingle);
                } catch (Exception e) {

                }
                //click listener for going CmeChapterActivity
                holder.newsdetailwebviewclickfifth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callIntent(items.get(position).getItems().get(0).getDetailUrl() + "", items.get(position).getItems().get(0).getId() + "", context, items.get(position).getItems().get(0).getExternalLinkUrl());
                    }
                });
                holder.share_news_fifth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news_fifth.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(0).getDetailUrl() + "", holder.cardviewsix);
                    }
                });

                //-----------------**************Like start***********-----------------------------//
                holder.like_news_fifth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(0).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");

                                    Log.e("num of like",""+items.get(position).getItems().get(0).getNo_of_likes());

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news_fifth.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news_fifth.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                if(items.get(position).getItems().get(0).getIs_liked_by_you()=="true")
                {
                    holder.like_news_fifth.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news_fifth.setImageResource(R.drawable.like_news_off);
                }
                //-----------------**************Like end*************----------------------------//

                //-----------**********bookmark start*********----------------//

                holder.bookmark_news_fifth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(0).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news_fifth.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news_fifth.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });

                if(items.get(position).getItems().get(0).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news_fifth.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news_fifth.setImageResource(R.drawable.bookmark_news_off);
                }

                //-----------*********bookmark end***********----------------//
                break;
            case 2:
                holder.txtHeader.setText(items.get(position).getItems().get(0).getTextualContent() + "");
                holder.txtHeadlines.setText(items.get(position).getItems().get(0).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)
                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(0).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContent);
                } catch (Exception e) {

                }
                //click listener for going CmeChapterActivity
                holder.newsdetailwebviewclickthird.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callIntent(items.get(position).getItems().get(0).getDetailUrl() + "", items.get(position).getItems().get(0).getId() + "", context, items.get(position).getItems().get(0).getExternalLinkUrl());
                    }
                });

                holder.txtHeader1.setText(items.get(position).getItems().get(1).getTextualContent() + "");
                holder.txtHeadlines1.setText(items.get(position).getItems().get(1).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)
                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(1).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContent1);
                } catch (Exception e) {

                }
                //click listener for going CmeChapterActivity
                holder.newsdetailwebviewclickfourth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callIntent(items.get(position).getItems().get(1).getDetailUrl() + "", items.get(position).getItems().get(1).getId() + "", context, items.get(position).getItems().get(1).getExternalLinkUrl());
                    }
                });
                //click listener for share screen shot
                holder.share_news_third.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news_third.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(0).getDetailUrl() + "", holder.cardfourth);
                    }
                });
                //click listener for share screen shot
                holder.share_news_fourth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news_fourth.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(1).getDetailUrl() + "", holder.cardfive);
                    }
                });
                //-----------*************bookmark article star********---------------//

                holder.bookmark_news_third.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(0).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news_third.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news_third.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });



                holder.bookmark_news_fourth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(1).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news_fourth.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news_fourth.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });
                if(items.get(position).getItems().get(0).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news_third.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news_third.setImageResource(R.drawable.bookmark_news_off);
                }

                if(items.get(position).getItems().get(1).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news_fourth.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news_fourth.setImageResource(R.drawable.bookmark_news_off);
                }
                //-----------*************bookmark article end********----------------//

                //-----------------**************Like start***********-----------------------------//
                holder.like_news_third.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(0).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");
                                    Log.e("num of like",""+items.get(position).getItems().get(0).getNo_of_likes());
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news_third.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news_third.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

                holder.like_news_fourth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(1).getId());
                                    Log.e("responseserver like two",responseserver);
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");
                                    Log.e("num of like",""+items.get(position).getItems().get(1).getNo_of_likes());

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news_fourth.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news_fourth.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

                if(items.get(position).getItems().get(0).getIs_liked_by_you()=="true")
                {
                    holder.like_news_third.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news_third.setImageResource(R.drawable.like_news_off);
                }

                if(items.get(position).getItems().get(1).getIs_liked_by_you()=="true")
                {
                    holder.like_news_fourth.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news_fourth.setImageResource(R.drawable.like_news_off);
                }


                //-----------------**************Like end*************----------------------------//

                break;
            case 3:
                holder.txtHeader2.setText(items.get(position).getItems().get(0).getTextualContent() + "");
                holder.txtHeadlines2.setText(items.get(position).getItems().get(0).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)
                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(0).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContent2);
                } catch (Exception e) {

                }
                holder.newsdetailwebviewclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callIntent(items.get(position).getItems().get(0).getDetailUrl() + "", items.get(position).getItems().get(0).getId() + "", context, items.get(position).getItems().get(0).getExternalLinkUrl());
                    }
                });

                holder.txtHeader3.setText(items.get(position).getItems().get(1).getTextualContent() + "");
                holder.txtHeadlines3.setText(items.get(position).getItems().get(1).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)
                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(1).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContent3);
                } catch (Exception e) {

                }
                //click listener for going CmeChapterActivity
                holder.newsdetailwebviewclickfirst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        callIntent(items.get(position).getItems().get(1).getDetailUrl() + "", items.get(position).getItems().get(1).getId() + "", context, items.get(position).getItems().get(1).getExternalLinkUrl());
                    }
                });


                holder.txtHeader4.setText(items.get(position).getItems().get(2).getTextualContent() + "");
                holder.txtHeadlines4.setText(items.get(position).getItems().get(2).getShortDescription() + "");
                try {
                    new Picasso.Builder(context).loggingEnabled(BuildConfig.DEBUG)

                            .downloader(new OkHttpDownloader(picassoClient1)).build()
                            .load("" + items.get(position).getItems().get(2).getImagesUrl())
                            .fit()
                            .error(R.drawable.username)
                            .into(holder.imgContent4);
                } catch (Exception e) {

                }
                ///--------------------------------********************bookmark article start****************-----------------------------------------//
                holder.bookmark_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(0).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });



                holder.bookmark_news_first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(1).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news_first.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news_first.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });


                holder.bookmark_news_second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String bookmarked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.bookmarkArticleServer(context, items.get(position).getItems().get(2).getId());
                                    jsonObject = new JSONObject(responseserver);
                                    bookmarked = jsonObject.getString("bookmark_status");

                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (bookmarked.equalsIgnoreCase("bookmarked")) {
                                                holder.bookmark_news_second.setImageResource(R.drawable.bookmark_news_on);
                                            } else {
                                                holder.bookmark_news_second.setImageResource(R.drawable.bookmark_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                });

                if(items.get(position).getItems().get(0).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news.setImageResource(R.drawable.bookmark_news_off);
                }

                if(items.get(position).getItems().get(1).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news_first.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news_first.setImageResource(R.drawable.bookmark_news_off);
                }

                if(items.get(position).getItems().get(2).getIs_bookmarked_by_you().equalsIgnoreCase("true"))
                {
                    holder.bookmark_news_second.setImageResource(R.drawable.bookmark_news_on);
                }
                else{
                    holder.bookmark_news_second.setImageResource(R.drawable.bookmark_news_off);
                }

//--------------------------------********************bookmark article start****************-----------------------------------------//

                //click listener for going CmeChapterActivity
                holder.newsdetailwebviewclicksecond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callIntent(items.get(position).getItems().get(2).getDetailUrl() + "", items.get(position).getItems().get(2).getId() + "", context, items.get(position).getItems().get(2).getExternalLinkUrl());
                    }
                });
                //click listener for share screen shot
                holder.share_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(0).getDetailUrl() + "", holder.cardone);
                    }
                });
                //click listener for share screen shot
                holder.share_news_first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news_first.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(1).getDetailUrl() + "", holder.cardsecond);
                    }
                });
                //click listener for share screen shot
                holder.share_news_second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.share_news_second.setImageResource(R.drawable.share_news_on);
                        takeScreenshot(holder, items.get(position).getItems().get(2).getDetailUrl() + "", holder.cardthird);
                    }
                });

                //-----------------**************Like start***********-----------------------------//
                holder.like_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(0).getId());
                                    Log.e("responseserver like",responseserver);
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");
                                    Log.e("num of like",""+items.get(position).getItems().get(0).getNo_of_likes());
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

                holder.like_news_first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(1).getId());
                                    Log.e("responseserver like",responseserver);
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");
                                    Log.e("num of like",""+items.get(position).getItems().get(1).getNo_of_likes());
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news_first.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news_first.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

                holder.like_news_second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                final String liked;
                                JSONObject jsonObject = null;
                                try {
                                    String responseserver = Methods.likeArticleServer(context, items.get(position).getItems().get(2).getId());
                                    Log.e("responseserver like th",responseserver);
                                    jsonObject = new JSONObject(responseserver);
                                    liked = jsonObject.getString("liked_status");
                                    Log.e("num of like",""+items.get(position).getItems().get(2).getNo_of_likes());
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (liked.equalsIgnoreCase("liked")) {
                                                holder.like_news_second.setImageResource(R.drawable.like_news_on);
                                            } else {
                                                holder.like_news_second.setImageResource(R.drawable.like_news_off);
                                            }

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

                if(items.get(position).getItems().get(0).getIs_liked_by_you()=="true")
                {
                    holder.like_news.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news.setImageResource(R.drawable.like_news_off);
                }

                if(items.get(position).getItems().get(1).getIs_liked_by_you()=="true")
                {
                    holder.like_news_first.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news_first.setImageResource(R.drawable.like_news_off);
                }

                if(items.get(position).getItems().get(2).getIs_liked_by_you()=="true")
                {
                    holder.like_news_second.setImageResource(R.drawable.like_news_on);
                }
                else{
                    holder.like_news_second.setImageResource(R.drawable.like_news_off);
                }


                //-----------------**************Like end*************----------------------------//

                break;
        }

        return convertView;
//        }catch (Exception e){
//            return null;
//        }
    }

    public static class ViewHolder {
        ImageView imgContent, imgContent1, imgContent2, imgContent3, imgContent4, imgContentSingle;
        TextView txtHeader, txtHeadlines, txtHeader1, txtHeadlines1, txtHeader2,
                txtHeadlines2, txtHeader3, txtHeadlines3, txtHeader4, txtHeadlines4, txtHeaderSingle, txtHeadlinesSingle;
        ImageView like_news, bookmark_news, share_news, like_news_first, bookmark_news_first, share_news_first,
                like_news_second, bookmark_news_second, share_news_second, share_news_third, share_news_fourth,
                like_news_third, like_news_fourth, bookmark_news_third, bookmark_news_fourth, share_news_fifth, like_news_fifth, bookmark_news_fifth;
        CardView cardone, cardfourth, cardfive, cardviewsix;

        LinearLayout newsdetailwebviewclicksecond, newsdetailwebviewclickfirst, newsdetailwebviewclick,
                cardsecond, cardthird, newsdetailwebviewclickfourth, newsdetailwebviewclickthird, newsdetailwebviewclickfifth;
    }

    public void callIntent(String url, String news_id, Context context, String externalUrl) {
        Intent intent = new Intent(context, CmeChapterActivity.class);
        intent.putExtra("DETAIL_URL", url + "");
        intent.putExtra("ARTICLE_ID", news_id + "");
        intent.putExtra("EXTERNAL_URL", externalUrl + "");
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
    //function for taking screen shot of particuler article and share it to another application
    private void takeScreenshot(ViewHolder holder, String link, View v) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = v;
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile, link);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile, String link) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, link);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));

        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));

    }


}


