package com.example.mkghosh.encryptionpractice.Security.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.w3e02.encryptionpractice.R;

/**
 * Created by Mithun on 4/21/16.
 */
public class SecuredPrefManager {

    public static final String mIsPrivateKey = "_privateUserKey";
    public static final String mUsername = "_queName";
    public static final String mIsNewUser = "_newUser";
//    public static final String mUSERNAME = "_queName";

    private SecuredPrefManager(Context context) {
        mContext = context;
    }

    private static SecuredPrefManager mOnPrefManager;
    private static Context mContext;
    private static SharedPreferences mSharedPref;
    private static SharedPreferences.Editor mPrefEditor;

    public static SecuredPrefManager init(Context context) {
        if (mOnPrefManager == null) {
            mOnPrefManager = new SecuredPrefManager(context);
        }
        initPref(context);
        return mOnPrefManager;
    }

    private static SharedPreferences initPref(Context context) {
        if (mSharedPref == null) {
            mSharedPref = context.getSharedPreferences(context.getResources().getString(R.string.secured_shared_preference), Context.MODE_PRIVATE);
            mPrefEditor = mSharedPref.edit();
        }

        if (mPrefEditor == null) {
            mPrefEditor = mSharedPref.edit();
        }

        return mSharedPref;
    }

    public static SharedPreferences getPref(Context context) {
        if (mSharedPref == null) {
            mSharedPref = context.getApplicationContext().getSharedPreferences(context.getApplicationContext().getResources().getString(R.string.secured_shared_preference), Context.MODE_PRIVATE);
        }
        return mSharedPref;
    }

    public synchronized void setIsPrivateKey(String mIsPrivateKey, boolean bool) {
        mPrefEditor.putBoolean(mIsPrivateKey,bool);
        mPrefEditor.apply();
    }

    public synchronized void setIsNewUser(String mIsNewUser, boolean bool) {
        mPrefEditor.putBoolean(mIsNewUser,bool);
        mPrefEditor.apply();
    }

    public synchronized boolean getIsPrivateKeyPresent (String mIsPrivateKey) {
        return mSharedPref.getBoolean(mIsPrivateKey, false);
    }

    public synchronized boolean getIsNewUser() {
        return mSharedPref.getBoolean(mIsNewUser, true);
    }

    public synchronized void setUserName(String userName) {
        mPrefEditor.putString(mUsername, userName);
        mPrefEditor.commit();
    }

    public synchronized String getUserName() {
        return mSharedPref.getString(mUsername, mUsername);
    }
}
