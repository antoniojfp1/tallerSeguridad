package com.taller.security;

//Punto AES
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class Security {

    static {
        java.security.Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    private static final byte[] IV = new byte[GCM_IV_LENGTH];

    public String sha1Password(String password) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA-1");
    	md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    /**
     * Crea la clave de encriptacion usada internamente
     * 
     * @param clave Clave que se usara para encriptar
     * @return Clave de encriptacion
     * @throws NoSuchAlgorithmException
     */
    private SecretKeySpec crearClave(String password)
            throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    	md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        SecretKeySpec secretKey = new SecretKeySpec(digest, "AES");
        return secretKey;
    }

    private byte[] doFinalSymetric(byte[] data, String password, int opMode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {

        SecretKeySpec secretKey = this.crearClave(password);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, IV);
        cipher.init(opMode, secretKey, ivSpec);
        
        return cipher.doFinal(data);
    }

    /**
     * Aplica la encriptacion AES a la cadena de texto usando la clave indicada
     * 
     * @param datos        Cadena a encriptar
     * @param claveSecreta Clave para encriptar
     * @return Información encriptada
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] encriptar(byte[] utf8Data, String claveSecreta) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        return doFinalSymetric(utf8Data, claveSecreta, Cipher.ENCRYPT_MODE);
    }
 
    /**
     * Desencripta la cadena de texto indicada usando la clave de encriptacion
     * @param datosEncriptados Datos encriptados
     * @param claveSecreta Clave de encriptacion
     * @return Informacion desencriptada
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     * @throws InvalidAlgorithmParameterException
     */
    public byte[] desencriptar(byte[] encryptedData, String claveSecreta) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        return doFinalSymetric(encryptedData, claveSecreta, Cipher.DECRYPT_MODE);
    }

    //Encripcion Asimetrica

    //Genera el par de llaves

    public KeyPair genAsymetricKeyPair() throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.genKeyPair();
    }

    private byte[] doFinalAsymetric(byte[] data, Key key, int opMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(opMode, key);
        return cipher.doFinal(data);
    }

    //Encriptar
    public byte[] asymetricEncrypt(byte[] data, byte[] rawPublicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(rawPublicKey));
        return doFinalAsymetric(data, publicKey, Cipher.ENCRYPT_MODE);
    }

    //Desencriptar
    public byte[] asymetricDecrypt(byte[] data, byte[] rawPrivateKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(rawPrivateKey));
        return doFinalAsymetric(data, privateKey, Cipher.DECRYPT_MODE);
    }

    public String generateMD5Hash(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        
        byte[] bytes = md.digest(); 
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}