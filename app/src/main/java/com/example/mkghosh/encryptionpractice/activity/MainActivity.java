package com.example.mkghosh.encryptionpractice.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.mkghosh.encryptionpractice.security.symmetric.SymmetricManager;
import com.example.w3e02.encryptionpractice.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String KPA_KEY = "kpA";
    private static String KPB_KEY = "kpB";

    private static String encText;

    public static final String TAG = "Mithun : ";

    private static final String CURVE_NAME = "secp160k1";

    private TextView encryptedText;
    private TextView decryptedText;

    private EditText textToEncrypt;
    private EditText idOfUser;

    private Button encryptButton;
    private Button decryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgressBarIndeterminateVisibility(false);

        encryptedText = (TextView) findViewById(R.id.textView_first);
        decryptedText = (TextView) findViewById(R.id.textView_decrypt);

        textToEncrypt = (EditText) findViewById(R.id.editText_getText);
        idOfUser = (EditText) findViewById(R.id.editText_id);

        encryptButton = (Button) findViewById(R.id.encryptButton);
        decryptButton = (Button) findViewById(R.id.decryptButton);

        encryptButton.setOnClickListener(this);
        decryptButton.setOnClickListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }


    private String getText() {
        return textToEncrypt.getText().toString();
    }

    private String getId() {
        return idOfUser.getText().toString();
    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SymmetricManager symmetricManager = new SymmetricManager(getApplicationContext());
        if (v.getId() == R.id.encryptButton) {

            String plainText = getText();
            Log.d(TAG, plainText);
            if (plainText.isEmpty()) {
                encryptedText.setError("No text to encrypt");
            } else {
                if(getId().isEmpty())
                    idOfUser.setError("please provide a user id.");
                else {
                    encText = symmetricManager.getEncryptedMessage(plainText, getId());
                    Log.d(TAG, "The value of enc text is : " + encText);
                    encryptedText.setText(encText);
                }
            }

        } else if (v.getId() == R.id.decryptButton) {
            if (encText == null || encText.isEmpty()) {
                decryptedText.setError("No encrypted text found first encrypt some text.");
                Log.d(TAG, "enctext is null or empty");
            } else {
                if(getId().isEmpty())
                    idOfUser.setError("Please provide an id of user");
                else
                    decryptedText.setText(symmetricManager.getDecryptedMessage(encText, getId()));
            }
        }
    }
}
