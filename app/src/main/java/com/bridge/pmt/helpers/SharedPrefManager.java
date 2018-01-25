package com.bridge.pmt.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.bridge.pmt.models.Activity;
import com.bridge.pmt.models.ProjectList;
import com.bridge.pmt.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public static final String SHARED_PREF_NAME = "bridgepmt";


    public static final String KEY_USER_ID = "id";
    private static final String KEY_CREATE_DAY = "create_day";
    public static final String KEY_USER_NAME = "name";
    private static final String KEY_EMP_CODE = "employee_code";
    private static final String KEY_TITLE_IDS = "title_IDS";
    private static final String KEY_BODY_IDT = "body_IDT";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_BIRTH = "birth";
    private static final String KEY_COMPANY_ID = "company_id";
    private static final String KEY_ADDRESS_IDT = "address_IDT";
    private static final String KEY_JOB_TITLE_IDS = "job_title_IDS";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PERSONAL_EMAIL_ID = "personal_email_id";
    private static final String KEY_SKYPE_ID= "skype_id";
    private static final String KEY_JOINING_DATE_= "joining_date";
    private static final String KEY_CONFIRMATION_DATE_= "confirmation_date";
    private static final String KEY_CODE= "code";
    private static final String KEY_STATUS_ID= "status_id";
    private static final String KEY_STATUS_NAME= "status_name";
    private static final String KEY_ACTIVE_USER= "active_user";
    private static final String KEY_EMPLOYMENT_STATUS= "employment_status";
    private static final String KEY_PRODUCTION= "production";
    private static final String KEY_ROLE_ID= "role_id";
    public static final String KEY_USER_TOKEN= "token";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }



    public boolean userDetails(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
      // editor.putString(KEY_CREATE_DAY, user.getEmail());
      editor.putString(KEY_USER_NAME, user.getName());
//        editor.putString(KEY_EMP_CODE, user.getName());
//        editor.putString(KEY_TITLE_IDS, user.getName());
//        editor.putString(KEY_BODY_IDT, user.getName());
//        editor.putString(KEY_LOGIN, user.getName());
//        editor.putString(KEY_PASSWORD, user.getName());
      editor.putString(KEY_EMAIL, user.getEmail());
//        editor.putString(KEY_GENDER, user.getName());
//        editor.putString(KEY_BIRTH, user.getName());
//        editor.putString(KEY_COMPANY_ID, user.getName());
//        editor.putString(KEY_ADDRESS_IDT, user.getName());
//        editor.putString(KEY_JOB_TITLE_IDS, user.getName());
//        editor.putString(KEY_PHONE, user.getName());
//        editor.putString(KEY_ADDRESS, user.getName());
//        editor.putString(KEY_PERSONAL_EMAIL_ID, user.getName());
//        editor.putString(KEY_SKYPE_ID, user.getName());
//        editor.putString(KEY_JOINING_DATE_, user.getName());
//        editor.putString(KEY_CONFIRMATION_DATE_, user.getName());
//        editor.putString(KEY_CODE, user.getName());
//        editor.putString(KEY_STATUS_ID, user.getName());
//        editor.putString(KEY_STATUS_NAME, user.getName());
//        editor.putString(KEY_ACTIVE_USER, user.getName());
//        editor.putString(KEY_EMPLOYMENT_STATUS, user.getName());
//        editor.putString(KEY_PRODUCTION, user.getName());
//        editor.putString(KEY_ROLE_ID, user.getName());
        editor.putString(KEY_USER_TOKEN, user.getToken());

        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Toast.makeText(mCtx,   sharedPreferences.getString(KEY_USER_TOKEN,null), Toast.LENGTH_LONG).show();


        if (sharedPreferences.getString(KEY_USER_TOKEN, null) != null)
            return true;
        return false;
    }

//    public User getUser() {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return new User(
//                sharedPreferences.getInt(KEY_USER_ID, 0),
//                sharedPreferences.getString(KEY_USER_NAME, null),
//                sharedPreferences.getString(KEY_USER_EMAIL, null),
//                sharedPreferences.getString(KEY_USER_GENDER, null)
//        );
//    }

    public  boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


//    public  void pushStringList( List list, String uniqueListName) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        for (int i = 0; i < list.size(); i++) {
//            editor.remove(uniqueListName + i);
//            editor.putString(uniqueListName + i, list.get(i).toString());
//        }
//        editor.apply();
//    }

    public  void pushactivityList( List<Activity> list) {
        String key = "activityList";
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(key, json);
        editor.apply();
    }

    public  void pushprojectList( List<ProjectList> list) {
        String key = "projectList";
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

        public  List<Activity> pullactivityList() {
            String key = "activityList";
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            List<Activity> result ;
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, "");
            Type type = new TypeToken<List<Activity>>(){}.getType();
            result = gson.fromJson(json,type);
            if(result==null)
            {
                result = new ArrayList<>();
            }
        return result;
    }
    public  List<ProjectList> pullprojectList() {
            String key = "projectList";
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            List<ProjectList> result ;
            Gson gson = new Gson();
            String json = sharedPreferences.getString(key, "");
            Type type = new TypeToken<List<ProjectList>>(){}.getType();
            result = gson.fromJson(json,type);
            if(result==null)
            {
                result = new ArrayList<>();
            }
        return result;
    }

//    public  List<String> pullStringList(String uniqueListName) {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        List<String> result = new ArrayList<>();
//        int size = sharedPreferences.getInt(uniqueListName + "_size", 0);
//
//        for (int i = 0; i < size; i++) {
//            result.add(sharedPreferences.getString(uniqueListName + i, null));
//        }
//        return result;
//    }
}
