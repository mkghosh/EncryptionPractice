package com.example.mkghosh.encryptionpractice;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

/**
 * <h1>The intention of this class is to help with ECC encryption.</h1>
 * Created by Mithun on 4/19/16.
 */
public class CryptoHelper {

    public static KeyPair getKeyPair() {
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp160k1");
        KeyPairGenerator g = null;
        try {
            g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecGenSpec, new SecureRandom());
            return g.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
