package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nganthoi.salai.tabgen.Bookmark_Article_WebView;
import com.nganthoi.salai.tabgen.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import Utils.Methods;
import activity.NewsDetailWebviewActivity;
import bean.Bookmark_Article_Bean;

/**
 * Created by Developer on 07-09-2016.
 */
public class Bookmark_article_adapter extends BaseAdapter {
    private Context context;
    LayoutInflater inflate = null;
    ArrayList<Bookmark_Article_Bean> bookmarkarticle;

    public Bookmark_article_adapter(final Context context, ArrayList<Bookmark_Article_Bean> bookmarkarticle) {
        inflate = LayoutInflater.from(context);
        this.bookmarkarticle = bookmarkarticle;
        this.context = context;

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Methods.showProgressDialog(context, "Please wait..");

            }
        });

    }


    public int getCount() {
        return bookmarkarticle.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView numberoflike, articlename, articledetail;
        ImageView imgLike;
        LinearLayout sharearticle;
        CardView cdCardView;
    }


    public View getView(final int position, View rowView, ViewGroup parent) {
        final Holder holder;
        if (rowView == null) {
            holder = new Holder();
            rowView = inflate.inflate(R.layout.bookmark_article_row, null);
            holder.numberoflike = (TextView) rowView.findViewById(R.id.numberoflike);
            holder.articlename = (TextView) rowView.findViewById(R.id.articlename);
            holder.imgLike = (ImageView) rowView.findViewById(R.id.imgLike);
            holder.articledetail = (TextView) rowView.findViewById(R.id.txtDetails);
            holder.sharearticle = (LinearLayout) rowView.findViewById(R.id.sharearticle);
            holder.cdCardView = (CardView) rowView.findViewById(R.id.cdCardView);

            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();
        }

        Methods.closeProgressDialog();
        if (bookmarkarticle != null) {
            if (bookmarkarticle.get(position).getNo_of_likes() != "0") {
                holder.numberoflike.setText("" + bookmarkarticle.get(position).getNo_of_likes());
                holder.imgLike.setImageResource(R.drawable.icon_love);
            } else {
                holder.imgLike.setImageResource(R.drawable.icon_love_gray);
            }

            if (!bookmarkarticle.get(position).getName().equalsIgnoreCase("")) {
                holder.articlename.setText(bookmarkarticle.get(position).getName());
            }

            if (!bookmarkarticle.get(position).getArticle_detail().equalsIgnoreCase("")) {
                holder.articledetail.setText(bookmarkarticle.get(position).getArticle_detail());
            }

            holder.sharearticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeScreenshot(holder, bookmarkarticle.get(position).getDetail_url() + "", holder.cdCardView);
                }
            });

            holder.cdCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Bookmark_Article_WebView.class);
                    intent.putExtra("DETAIL_URL", bookmarkarticle.get(position).getDetail_url()+ "");
                    context.startActivity(intent);
                }
            });


        }
        return rowView;
    }

    private void takeScreenshot(Holder holder, String link, View v) {
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


