package com.example.mkghosh.encryptionpractice.Security.symmetric;

import android.content.Context;
import android.util.Log;

import com.example.mkghosh.encryptionpractice.activity.MainActivity;
import com.example.mkghosh.encryptionpractice.Security.utility.SecuredPrefManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * <h1>The intention of this class is to process all encryption related works needs to be done.</h1>
 * <p>The message encryption will be done within this class and the key encryption will be done by the class.</p>
 * <p>When the message will come to this class to encrypt this class will check if the private key is available for that user.</p>
 * <p>If the key is available then the message gets encrypted with that key and gives control back to its caller otherwise it will</p>
 * <p>generate a private key for that user and instructs the to encrypt the key with the users public key </P>
 * <p>and save that key to the local database and give the encrypted key to the controller for sending the key to the user first.</p>
 * Created by Mithun on 4/20/16.
 */
public class SymmetricManager {

    private Context mContext;
    private SecuredPrefManager prefManager;

    private Key encryptedKey;
    private byte[] privateKey;
    SymEncryptionProcessor symProcessor;

    public Key usersPublicKey;

    //Non-Static block of code to initialize symProcessor when a new instance of the class is created.
    public SymmetricManager (Context mContext){
        this.mContext = mContext;
        symProcessor = new SymEncryptionProcessor();
    }


//    private SharedPreferences getSharedPreferences() {
//        return SecuredPrefManager.getDefaultSharedPreferences(mContext);
//    }
//
//    private byte[] getPrivateKey(SharedPreferences sharedPrefs) throws NoSuchAlgorithmException {
//        String privateKeyStr = sharedPrefs.getString("_private",null);
//
//        if(privateKeyStr != null) {
//            return privateKeyStr.getBytes();
//        }
//        else {
//            privateKey = symProcessor.generateSymmetricKey();
//
//            final SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
//            symProcessor.savePrivateKey(prefsEditor, new String(privateKey));
//            prefsEditor.apply();
//            return privateKey;
//        }
//    }

    public String getEncryptedMessage(String message, String id) {

        prefManager = SecuredPrefManager.init(mContext);
        byte[] privateKey;
        try {
            if(prefManager.getIsPrivateKeyPresent(SecuredPrefManager.mIsPrivateKey)) {
                Map<String, byte[]> userPrivateKey = (Map<String, byte[]>) SaveKeys.readObject(SecuredPrefManager.mIsPrivateKey);

                if(userPrivateKey.containsKey(id)) {
                    privateKey = userPrivateKey.get(id);
                } else {
                    privateKey = symProcessor.generateSymmetricKey();
                    userPrivateKey.put(id, privateKey);
                    SaveKeys.setContext(mContext);
                    SaveKeys.writeObject(SecuredPrefManager.mIsPrivateKey, userPrivateKey);
                }

            } else {
                privateKey = symProcessor.generateSymmetricKey();
                Map<String, byte[]> userPrivateKeyObject = new HashMap<>();
                userPrivateKeyObject.put(id, privateKey);
                SaveKeys.setContext(mContext);
                SaveKeys.writeObject(SecuredPrefManager.mIsPrivateKey, userPrivateKeyObject);
                prefManager.setIsPrivateKey(SecuredPrefManager.mIsPrivateKey, true);
                Log.d(MainActivity.TAG, "The value of private key is " + privateKey.toString());
            }

            return symProcessor.encryptWithAESKey(message, privateKey);
        } catch (NoSuchAlgorithmException noAlgEx) {
            noAlgEx.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.d("Exception : ", e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDecryptedMessage (String encodedMessage, String id) {
        prefManager = SecuredPrefManager.init(mContext);
        if(prefManager.getIsPrivateKeyPresent(SecuredPrefManager.mIsPrivateKey)) {
            try {
                Map<String, byte[]> userPrivateKey = (Map<String, byte[]>) SaveKeys.readObject(SecuredPrefManager.mIsPrivateKey);
                byte[] privateKey = userPrivateKey.get(id);
                return symProcessor.decryptWithAESKey(encodedMessage, privateKey);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        }
        return null;
    }

}
