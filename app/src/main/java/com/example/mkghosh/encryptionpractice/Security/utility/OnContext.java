package com.example.mkghosh.encryptionpractice.Security.utility;

import android.content.Context;

/**
 * Created by Mithun on 4/21/16.
 */
public class OnContext {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context get(Context context) {
        if (mContext == null && context != null) {
            mContext = context.getApplicationContext();
        }
        return mContext;
    }
}