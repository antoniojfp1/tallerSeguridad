package com.taller.security;

//Punto AES
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Security{

    public String sha1Password(String password) throws NoSuchAlgorithmException{
    	MessageDigest md = MessageDigest.getInstance("SHA-1");
    	md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    /**
     * Crea la clave de encriptacion usada internamente
     * @param clave Clave que se usara para encriptar
     * @return Clave de encriptacion
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException 
     */
    private SecretKeySpec crearClave(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA-1");
    	md.update(password.getBytes());
        byte[] digest = md.digest();
        SecretKeySpec secretKey = new SecretKeySpec(digest, "AES");
        return secretKey;
    }

    //TODO: cambiar el tipo de datos del objeto a encriptar/desencriptar en caso de que no sea una cadena

    /**
     * Aplica la encriptacion AES a la cadena de texto usando la clave indicada
     * @param datos Cadena a encriptar
     * @param claveSecreta Clave para encriptar
     * @return Información encriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
 
        return encriptado;
    }
 
    /**
     * Desencripta la cadena de texto indicada usando la clave de encriptacion
     * @param datosEncriptados Datos encriptados
     * @param claveSecreta Clave de encriptacion
     * @return Informacion desencriptada
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
 
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
         
        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
         
        return datos;
    }
}