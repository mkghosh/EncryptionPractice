package com.example.mkghosh.encryptionpractice.Security.symmetric;

import android.content.Context;

import com.example.mkghosh.encryptionpractice.Security.utility.OnContext;
import com.example.mkghosh.encryptionpractice.Security.utility.SecuredPrefManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>For saving the private key of each and every friends.</h1>
 * Created by Mithun on 4/21/16.
 */
public class SaveKeys {

    private SaveKeys() {
    }

    private static Context mContext;

    public static final String PRIVATEKEY = "_privateUserKey";

    public synchronized static void setContext(Context context) {
        mContext = context;
    }

    public synchronized static void writeObject(String key, Object object) throws IOException {

        if (mContext == null) return;

        if (object != null) {

            FileOutputStream fos = mContext.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();

			/*commit successful flag*/
            if (key == PRIVATEKEY) {
                SecuredPrefManager.init(OnContext.get(mContext)).setIsPrivateKey(SecuredPrefManager.mIsPrivateKey, true);
            }

        }
    }

    public synchronized static Object readObject(String key) throws IOException, ClassNotFoundException {


        if (isObject(key)) {

            FileInputStream fis = OnContext.get(mContext).openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        }

        return null;
    }

    public synchronized static boolean isObject(String key) {
        boolean isStored = false;
        CharSequence isKey = null;

        if (key == PRIVATEKEY) {
            isKey = SecuredPrefManager.mIsPrivateKey;
        }

        if (isKey != null) {
            isStored = SecuredPrefManager.init(OnContext.get(mContext)).getIsPrivateKeyPresent(isKey.toString());
        }
//
        return isStored;
    }
}

