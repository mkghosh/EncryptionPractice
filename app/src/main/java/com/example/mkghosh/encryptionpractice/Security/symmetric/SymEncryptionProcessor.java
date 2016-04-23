package com.example.mkghosh.encryptionpractice.Security.symmetric;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <h1>This class helps the {@link SymmetricManager} to generate the Symmetric key and other process</h1>
 * Created by Mithun on 4/20/16.
 */
public class SymEncryptionProcessor {

    private static final String algorithmName = "AES";

    /**
     * <h1>Default constructor for getting the instance without any parameter.</h1>
     */
    public SymEncryptionProcessor() {

    }

    /**
     * <h1>Generates Symmetric key and encodes it into byte and then gives it back to its caller.</h1>
     *
     * @return {@link Byte} encoded symmetric key.
     * @throws NoSuchAlgorithmException
     */
    public byte[] generateSymmetricKey() throws NoSuchAlgorithmException {

        KeyGenerator generator = KeyGenerator.getInstance(algorithmName);
        generator.init(256);
        SecretKey key = generator.generateKey();

        return key.getEncoded();

    }

    /**
     * <h1>Encrypts the given data with the provided key.</h1>
     *
     * @param data {@link String} to be encrypted.
     * @param key  {@link Byte} private key for the specific user.
     * @return {@link String} encrypted data.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public String encryptWithAESKey(String data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        SecretKey secKey = new SecretKeySpec(key, algorithmName);

        Cipher cipher = Cipher.getInstance(algorithmName);

        cipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] newData = cipher.doFinal(data.getBytes());

        return Base64.encodeToString(newData, Base64.DEFAULT);
    }

    /**
     * <h1>Decrypts the given encrypted data with the provided key.</h1>
     *
     * @param inputData {@link String} to be decrypted.
     * @param key       {@link Byte} for the specific user.
     * @return {@link String} decrypted data.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String decryptWithAESKey(String inputData, byte[] key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(algorithmName);
        SecretKey secKey = new SecretKeySpec(key, algorithmName);

        cipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] newData = cipher.doFinal(Base64.decode(inputData.getBytes(), Base64.DEFAULT));

        return new String(newData);

    }

}
