package org.example.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class SuperSecretKey {

    public SecretKeySpec 
    generateSecretKey(char[] password, byte[] salt, 
                      int iterationCount, int keyLength) 
        throws NoSuchAlgorithmException, 
               InvalidKeySpecException 
    { 
        SecretKeyFactory keyFactory 
            = SecretKeyFactory.getInstance( 
                "PBKDF2WithHmacSHA512"); 
        PBEKeySpec keySpec = new PBEKeySpec( 
            password, salt, iterationCount, keyLength); 
        SecretKey tempKey 
            = keyFactory.generateSecret(keySpec); 
        return new SecretKeySpec(tempKey.getEncoded(), 
                                 "AES"); 
    } 

    private String base64Encoder(byte[] bytes) 
    { 
        return Base64.getEncoder().encodeToString(bytes); 
    } 

    public String encrypt(String dataToEncrypt, 
                          SecretKeySpec key) 
        throws GeneralSecurityException, 
               UnsupportedEncodingException 
    { 
        Cipher pbeCipher 
            = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
        pbeCipher.init(Cipher.ENCRYPT_MODE, key); 
          
                 AlgorithmParameters parameters 
            = pbeCipher.getParameters(); 
          
                 IvParameterSpec ivParameterSpec 
            = parameters.getParameterSpec( 
                IvParameterSpec.class); 
          
                 byte[] cryptoText = pbeCipher.doFinal( 
            dataToEncrypt.getBytes("UTF-8")); 
          
                 byte[] iv = ivParameterSpec.getIV(); 
        return base64Encoder(iv) + ":"
            + base64Encoder(cryptoText); 
    } 
}