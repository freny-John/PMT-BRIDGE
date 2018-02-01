package com.bridge.pmt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bridge.pmt.activities.SignInActivity;
import com.bridge.pmt.api.APIService;
import com.bridge.pmt.api.APIUrl;
import com.bridge.pmt.fragments.HoursFragment;
import com.bridge.pmt.fragments.LeaveFragment;
import com.bridge.pmt.fragments.NewsFragment;
import com.bridge.pmt.fragments.AccountFragment;
import com.bridge.pmt.helpers.ConnectivityReceiver;
import com.bridge.pmt.helpers.SharedPrefManager;
import com.bridge.pmt.models.Activity;
import com.bridge.pmt.models.BaseResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import client.yalantis.com.foldingtabbar.FoldingTabBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView mTextMessage;
    TextView title;
    ActionBar ab;
    FloatingActionButton add_reg;
    FloatingActionButton logout;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        ab = getSupportActionBar();
        // Create a TextView programmatically.
        title = new TextView(getApplicationContext());
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        // Apply the layout parameters to TextView widget
        title.setLayoutParams(lp);
        // Set text to display in TextView
        title.setText("NEWS");
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        // Set the text color of TextView
        title.setTextColor(Color.WHITE);
        // Set TextView text alignment to center
        title.setGravity(Gravity.CENTER);
        // Set the ActionBar display option
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        ab.setCustomView(title);



        //Folding Tab Bar
        FoldingTabBar tabBar = (FoldingTabBar) findViewById(R.id.folding_tab_bar);
        add_reg = (FloatingActionButton) findViewById(R.id.add_reg);
        logout = (FloatingActionButton) findViewById(R.id.logout);
        add_reg.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        changeFragment(new NewsFragment());

        tabBar.setOnFoldingItemClickListener(new FoldingTabBar.OnFoldingItemSelectedListener() {
            @Override
            public boolean onFoldingItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ftb_menu_nearby:
                        changeFragment(new NewsFragment());
                        title.setText("NEWS");
                        add_reg.setVisibility(View.GONE);
                        logout.setVisibility(View.GONE);


                        break;
                    case R.id.ftb_menu_new_chat:
                        changeFragment(new HoursFragment());
                        title.setText("HOURS");
                        add_reg.setVisibility(View.VISIBLE);
                        logout.setVisibility(View.GONE);


                        break;
                    case R.id.ftb_menu_profile:
                        changeFragment(new LeaveFragment());
                        title.setText("LEAVE & HOLIDAYS");
                        add_reg.setVisibility(View.GONE);
                        logout.setVisibility(View.GONE);


                        break;
                    case R.id.ftb_menu_settings:
                        changeFragment(new AccountFragment());
                        title.setText("ACCOUNT");
                        add_reg.setVisibility(View.GONE);
                        logout.setVisibility(View.VISIBLE);


                        break;
                }

                return false;
            }
        });

//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setItemIconTintList(null);
//
//
//
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
//        for (int i = 0; i < menuView.getChildCount(); i++) {
//            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
//            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
//            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
//            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, displayMetrics);
//            iconView.setLayoutParams(layoutParams);
//            itemView.setShiftingMode(false);
//            itemView.setChecked(false);
//        }



        //loading home fragment by default
       // displaySelectedScreen(R.id.nav_news);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(SharedPrefManager.KEY_USER_TOKEN, null) != null) {
//            TextView txtUserName   = (TextView)header.findViewById(R.id.txtUserNameId);
//            TextView txtUserEmail = (TextView)header.findViewById(R.id.txtUserEmailId);
//
//            txtUserName.setText( sharedPreferences.getString(SharedPrefManager.KEY_USER_NAME, null));
//            txtUserEmail.setText( sharedPreferences.getString(SharedPrefManager.KEY_EMAIL, null));


          //  Toast.makeText(getApplicationContext(), sharedPreferences.getString(SharedPrefManager.KEY_USER_NAME, null), Toast.LENGTH_LONG).show();
        }
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }

    }


//Pick fragment for each screens
    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation view item clicks here.
            displaySelectedScreen(item.getItemId());
            return true;
        }
    };



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            // launch settings activity
//            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//
//            return true;
//
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_news:
                fragment = new NewsFragment();

                break;
            case R.id.nav_hours:
                fragment = new HoursFragment();


                break;

            case R.id.nav_leave:
                fragment = new LeaveFragment();
                           break;

            case R.id.nav_account:

                fragment = new AccountFragment();

                break;

               // logout();

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           // ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }


    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

       if(ConnectivityReceiver.isConnected()) {
           getProject_Activity_List();
    }
else{
           Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();

       }}




    private void getProject_Activity_List()

    {
        //Geting the user token from sharedpreference
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(SharedPrefManager.KEY_USER_TOKEN, null);
        int userId = sharedPreferences.getInt(SharedPrefManager.KEY_USER_ID,0);

        if (token != null) {
            //Web service to get the activity list
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Geting Activities...");
            progressDialog.show();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<BaseResponse> call = service.getUserActivity(token);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    progressDialog.dismiss();

                    Log.e("SERVER_RESPONSE_data","ACTIVTY :"+ String.valueOf((response.body().getData().getActivity() )));

                    List <Activity> activityList=response.body().getData().getActivity();

                    // activity list to sharedpreferences
                    SharedPrefManager.getInstance(getApplicationContext()).pushactivityList(activityList);

                    Log.e("ACTIVITY_LIST ", String.valueOf(activityList ));

                    // get activitylist from sharedpreferences
                    Log.e("SP_LIST ", String.valueOf(         SharedPrefManager.getInstance(getApplicationContext()).pullactivityList() ));
                    //Toast.makeText(getApplicationContext(), ""+ response.body().getData().getActivity(), Toast.LENGTH_LONG).show();

                    if ( String.valueOf(response.body().getStatus() ).equals("1")) {


//                        SharedPrefManager.getInstance(getApplicationContext()).userActivity(response.body().getData().getUser());
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            call = service.getUserProjects(token,userId);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    progressDialog.dismiss();

                    Log.e("SERVER_RESPONSE_data", "pROJECT LIST "+String.valueOf((response.body().getData().getProjectList() )));

                    List projectList=response.body().getData().getProjectList();

                    // activity list to sharedpreferences
                    SharedPrefManager.getInstance(getApplicationContext()).pushprojectList(projectList);

                    Log.e("PROJECT_LIST ", String.valueOf(projectList ));

                    // get activitylist from sharedpreferences
                    Log.e("SP_LIST ", String.valueOf(         SharedPrefManager.getInstance(getApplicationContext()).pullactivityList() ));
                    // Toast.makeText(getApplicationContext(), ""+ response.body().getData().getProjectList(), Toast.LENGTH_LONG).show();

                    if ( String.valueOf(response.body().getStatus() ).equals("1")) {


//                        SharedPrefManager.getInstance(getApplicationContext()).userActivity(response.body().getData().getUser());
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {



    }
}
