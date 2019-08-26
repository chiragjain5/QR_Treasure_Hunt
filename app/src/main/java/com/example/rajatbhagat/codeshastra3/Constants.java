package com.example.rajatbhagat.codeshastra3;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rajatbhagat on 19/1/17.
 */

public class Constants {

//    final static String ROOT_URL = "http://treasure-online.herokuapp.com";
    final static String ROOT_URL = "http://10.0.2.2:8000/";
    final static String LOGIN_URL = ROOT_URL + "login/";
    final static String REGISTER_URL = ROOT_URL + "register/";
    final static String CLUE_DETAILS_URL = ROOT_URL + "update/";
    final static String GET_ALL_CLUES = ROOT_URL + "scanned/";

    final static String KEY_USERNAME = "username";
    final static String KEY_EMAIL = "email";
    final static String KEY_FIRST_NAME = "firstName";
    final static String KEY_LAST_NAME = "lastName";
    final static String KEY_LOGIN_STATUS = "loginStatus";

    static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        return preferences.getString("username", null);
    }

}
