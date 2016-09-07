package Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nganthoi.salai.tabgen.BoomkarkActivity;
import com.nganthoi.salai.tabgen.MainActivity;
import com.nganthoi.salai.tabgen.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import custom_menu.MyPopupMenu;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sharePreference.SharedPreference;

//custom static type, progress bar ,Snackbar,Toast class
public class Methods {
    private static ProgressDialog progressDialog;
    private static Dialog pgDialog;
    private static Context context;
    static SharedPreference sp;

    /***
     * This method is used to check email id is valid or not
     ***/
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * Convert image to base64 string.
     * <p/>
     * //	 * @param Bitmap image
     *
     * @return String stringConvertedImage
     */
    public static String convertImageToBase64(Bitmap img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String temp = null;
        try {
            temp = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /***
     * converts dip to pixels
     ***/
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                metrics);
    }

    /**
     * This method is used to show short toast
     **/
    public static void toastShort(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
//custom toast ,which is call by using class name
    public static void customToastLong(String msg, Context ctx, float size) {
        TextView textView = new TextView(ctx);
        textView.setText(msg);
        textView.setTextSize(size);
//		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ctx.getResources().getDimension(R.dimen.app_new));
//		textView.setTextSize(ctx.getResources().getDimension(R.dimen.text_size_small));
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        Toast toast = new Toast(ctx);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(textView);
        toast.show();
    }

    /**
     * This method is used to show long toast
     **/
    public static void toastLong(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
    //custom snackbar ,which is call by using class name
    public static void showSnackbar(String msg, Activity activity) {
        SnackbarManager.show(
                Snackbar.with(activity.getApplicationContext()) // context
                        .type(SnackbarType.MULTI_LINE) // Set is as a multi-line snackbar
                        .text("" + msg) // text to be displayed
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT) // make it shorter
                        .animation(false) // don't animate it
                , activity); // where it is displayed
    }


    /**
     * This method is used to show progress dialog
     **/
    public static void showProgressDialog(final Context ctx) {
        context = ctx;
        closeProgressDialog();
        pgDialog = new Dialog(ctx);
        pgDialog.setCancelable(false);

        pgDialog.show();
    }

    /**
     * This method is used to show progress dialog for payments only
     **/
    public static void showProgressDialog(Context ctx, String message) {
        context = ctx;
        closeProgressDialog();
        progressDialog = ProgressDialog.show(context, "", "Please Wait", true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * This method is used to close progress dialog
     **/
    public static void closeProgressDialog() {
        if (pgDialog != null) {
            pgDialog.dismiss();
        }
        if (progressDialog != null && progressDialog.isShowing() && context != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static BigDecimal convertTaxtoDollar(String price, String tax) {
        double dPrice = Double.valueOf(price);
        if (tax == null) {
            tax = "0.0";
        }
        double dTax = Double.valueOf(tax);
        double totalTax = (dPrice) * (dTax / 100);
        BigDecimal bdTotalTax = new BigDecimal(totalTax).setScale(2, RoundingMode.HALF_UP);
        return (bdTotalTax);
    }

    /**
     * Perform tax calculations and calculate Total Amount**
     * //	 * @param String price
     * //	 * @param String tax
     * //	 * @param String quantity
     *
     * @return BigDecimal calculated total amount of each product including tax in BigDecimal type
     */
    public static BigDecimal calculateTotalOfEachProduct(String price, String tax, String quantity) {

        double dPrice = Double.valueOf(price);
        double dTax = Double.valueOf(tax);
        double dQuantity = Double.valueOf(quantity);

        double totalTax = (dPrice) * (dTax / 100);

        double totalPrice = (((dPrice) + (totalTax)) * dQuantity);

        BigDecimal bdTotalPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);

        return (bdTotalPrice);
    }

    /**
     * Perform tax calculations and calculate Total Amount of each product
     * including tax only and excluding quantity**
     * //	 * @param String price
     * //	 * @param String tax
     *
     * @return calculated total amount of each product including tax and excluding quantity
     */
    public static BigDecimal calculateTotalOfEachProduct(String price, String tax) {

        double dPrice = Double.valueOf(price);
        if (tax == null) {
            tax = "0.0";
        }
        double dTax = Double.valueOf(tax);

        double totalTax = (dPrice) * (dTax / 100);

        double totalPrice = (((dPrice) + (totalTax)));

        BigDecimal bdTotalPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);

        return (bdTotalPrice);
    }

    //-----------------********SERVER LOGOUT FUNCTION*********----------------//

    public static String sendLogoutServer(String matermost_token, String firebase_token) {
        // TODO: Implement this method to send token to your app server.


        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("token", matermost_token)
                .add("fcm_token", firebase_token)
                .build();

        Request request = new Request.Builder()
                .url("http://128.199.111.18/TabGenAdmin/logout.php")
                .post(formBody)
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

    //-----------------********SERVER LOGOUT FUNCTION END*********----------------//


    public static void logout(final Activity activity) {
        sp = new SharedPreference();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Logout ?");
        alertDialogBuilder.setMessage("Are you sure to logout?");
        alertDialogBuilder.setIcon(R.drawable.failure_icon);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                final String matermost_token_server = sp.getTokenPreference(activity);
                final String fcm_token_server = sp.getFcmToken(activity);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String responseserver = Methods.sendLogoutServer(matermost_token_server, fcm_token_server);
                        Log.e("responseserver", responseserver);

                    }
                }).start();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                sp.clearPreference(activity);
            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showPopup(final Activity activity) {
        View menuItemView = activity.findViewById(R.id.action_settings);
        MyPopupMenu popup = new MyPopupMenu(activity, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.menu_popup, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new MyPopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.profile) {

                } else if (id == R.id.bookmark) {
                    Intent bookmarkintent = new Intent(activity, BoomkarkActivity.class);
                    activity.startActivity(bookmarkintent);

                } else if (id == R.id.notification) {

                } else if (id == R.id.accountsetting) {

                } else if (id == R.id.signout) {
                    Methods.logout(activity);
                }

                return false;
            }
        });

    }

    //--------------------------*************bookmark article**************---------------------------------//

    public static String bookmarkArticleServer(Context activity,String article_id) {
        // TODO: Implement this method to send token to your app server.

        sp = new SharedPreference();
        final String token = sp.getTokenPreference(activity);
        final String server_ip=sp.getServerIP_Preference(activity);
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("article_id", article_id)
                .build();

        Request request = new Request.Builder()
                .url("http://"+server_ip+"/TabGenAdmin/bookmark_an_article.php")
                .addHeader("Authorization", token)
                .post(formBody)
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

    //--------------------------*************like article**************---------------------------------//

    public static String likeArticleServer(Context activity,String article_id) {
        // TODO: Implement this method to send token to your app server.

        sp = new SharedPreference();
        final String token = sp.getTokenPreference(activity);
        final String server_ip=sp.getServerIP_Preference(activity);
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("article_id", article_id)
                .build();

        Request request = new Request.Builder()
                .url("http://"+server_ip+"/TabGenAdmin/like_an_article.php")
                .addHeader("Authorization", token)
                .post(formBody)
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


}
