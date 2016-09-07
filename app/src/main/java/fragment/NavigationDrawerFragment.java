package fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.nganthoi.salai.tabgen.Bookmark_Article_Activity;
import com.nganthoi.salai.tabgen.BoomkarkActivity;
import com.nganthoi.salai.tabgen.MainActivity;
import com.nganthoi.salai.tabgen.OrganisationDetails;
import com.nganthoi.salai.tabgen.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utils.Methods;
import Utils.PreferenceHelper;
import adapter.RecycleAdapter;
import bean.InformationRecycleView;
import sharePreference.SharedPreference;

//navigation drawer fragment
public class NavigationDrawerFragment extends Fragment {
    ProgressDialog progressDialog;
    ActionBarDrawerToggle mdrawerTogle;
    DrawerLayout mdrawerLayout;
    boolean mUserLearnedDrawer;
    boolean mFromSavedInstanceState;
    View containerView;
    RecyclerView recyclerView;
    Context context;
    static ArrayList<String> arrayListTitle;
    int positionTabed;
    RecycleAdapter recycleAdapter;
    static Dialog customDialog;
    String usertype, regStatus, UserId, verifyStatus, UserName;
    TextView username, usermail, userrole;
    SharedPreference sp;
    PreferenceHelper preferenceHelper;
    String role;//user role
    String team, user_id;//team name
    List<String> list;
    ArrayList<String> stringArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), "keyUserLearnedDrawer", "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_xml_drawer, container, false);
        loadSavedPreferences();
        preferenceHelper = new PreferenceHelper(getActivity());
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        username = (TextView) layout.findViewById(R.id.username);
        usermail = (TextView) layout.findViewById(R.id.user_email);
        userrole = (TextView) layout.findViewById(R.id.user_role);
        sp = new SharedPreference();
        String user_details = sp.getPreference(getActivity());
        try {
            JSONObject jsonObject = new JSONObject(user_details);
            //set user name on navigation drawer
            username.setText(jsonObject.getString("username"));
            //set mail on navigation drawer
            usermail.setText(jsonObject.getString("email"));
            role = jsonObject.getString("roles");
            user_id = jsonObject.getString("id");
            preferenceHelper.addString("LOGIN_USER_ID", user_id);
            userrole.setText(role);
            team = sp.getTeamNamePreference(getActivity());
            System.out.println("Team Name: " + team + "\n");
            new GetTemplates(team).execute(user_id);

        } catch (JSONException e) {
            System.out.println("Exception :" + e.toString());
        }


        recycleAdapter = new RecycleAdapter(getActivity(), getData(positionTabed));

        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        //recycler view click listener- acccording to position ,for going another activity
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent) {


                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mdrawerLayout.closeDrawers();
                    switch (arrayListTitle.get(recyclerView.getChildPosition(child))) {
                        case "My Bookmark Chat":

                            Intent bookmark = new Intent(getActivity(), BoomkarkActivity.class);
                            getActivity().startActivity(bookmark);

                            break;

                        case "My Bookmark Article":
                      Intent bookmarkarticle = new Intent(getActivity(), Bookmark_Article_Activity.class);
                            getActivity().startActivity(bookmarkarticle);

                            break;

                        case "Notification":

                            break;

                        case "Account Setting":


                            break;


                        case "Sign out":

                            logout();

                            break;


                    }


                    return true;

                }

                return false;

            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return layout;


    }


    public static List<InformationRecycleView> getData(int positionTabed) {
        List<InformationRecycleView> data = new ArrayList<>();
        arrayListTitle = new ArrayList<>();
        arrayListTitle.clear();
        int[] icon = {R.drawable.profile, R.drawable.bookmark_navigation_menu, R.drawable.bookmark_navigation_menu, R.drawable.notification_navigation_menu, R.drawable.setting_navigation_menu, R.drawable.logout};
        String[] title = {"Profile", "My Bookmark Chat", "My Bookmark Article","Notification", "Account Setting", "Sign out"};
        for (int i = 0; i < title.length && i < icon.length; i++) {
            // if (i != positionTabed) {
            InformationRecycleView current = new InformationRecycleView();
            current.iconId = icon[i];
            current.title = title[i];
            arrayListTitle.add(title[i]);
            data.add(current);
            // }
        }
        return data;
    }


    public void setUp(int fragmentId, final DrawerLayout drawerLayout, final Toolbar toolbar, final Context context, int positionTabed) {
        this.context = context;
        this.positionTabed = positionTabed;
        mdrawerLayout = drawerLayout;
        containerView = getActivity().findViewById(fragmentId);
        recycleAdapter = new RecycleAdapter(getActivity(), getData(positionTabed));
        recyclerView.setAdapter(recycleAdapter);
        mdrawerTogle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    SavePreferences(getActivity(), "keyUserLearnedDrawer", mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //  System.out.println("slideOffset="+slideOffset);

                if (slideOffset == 1) {

                    toolbar.setAlpha(slideOffset);
                }
            }


        };


        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mdrawerLayout.closeDrawer(containerView);
        }


        mdrawerLayout.setDrawerListener(mdrawerTogle);

        mdrawerLayout.post(new Runnable() {

            @Override
            public void run() {
                mdrawerTogle.syncState();

            }
        });


    }


    public static void SavePreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("savePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(preferenceName, preferenceValue);
        editor.apply();


    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("savePref", Context.MODE_PRIVATE);

        return sharedpreferences.getString(preferenceName, defaultValue);


    }


    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        usertype = sharedPreferences.getString("UserType", "Blank");
        regStatus = sharedPreferences.getString("regStatus", "Blank");
        UserName = sharedPreferences.getString("UserName", "Guest");
        UserId = sharedPreferences.getString("UserId", "Blank");
        verifyStatus = sharedPreferences.getString("VerifyStatus", "Blank");

    }

    public void savePreferences(String key, String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor sp_edit = sp.edit();
        sp_edit.putString(key, value);
        sp_edit.commit();
    }


    public class GetTemplates extends AsyncTask<String, String, List<String>> {
        String team_name;

        public GetTemplates(String team) {
            team_name = team;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading your Templates");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected List<String> doInBackground(String... user_id) {
            publishProgress("");
            list = OrganisationDetails.getListOfTemplates(getActivity(), user_id[0]);
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

    public void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Logout ?");
        alertDialogBuilder.setMessage("Are you sure to logout?");
        alertDialogBuilder.setIcon(R.drawable.failure_icon);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final String matermost_token_server = sp.getTokenPreference(getActivity());
                final String fcm_token_server = sp.getFcmToken(getActivity());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String responseserver = Methods.sendLogoutServer(matermost_token_server, fcm_token_server);
                        Log.e("responseserver", responseserver);

                    }
                }).start();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                sp.clearPreference(getActivity());
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

}
