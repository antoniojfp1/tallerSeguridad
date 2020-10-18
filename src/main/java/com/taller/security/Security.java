package com.taller.security;

//Punto Encripcion Asimetrica
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
//Punto AES
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

@Component
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

    //Encripcion Asimetrica
    //Se crean las variables donde se guardan als claves publica y privada

    public PrivateKey PrivateKey = null;
    public PublicKey PublicKey = null;

    //Los metodos para crear las llaves

    public void setPrivateKeyString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] encodedPrivateKey = stringToBytes(key);
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        this.PrivateKey = privateKey;
    }

    public void setPublicKeyString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        
        byte[] encodedPublicKey = stringToBytes(key);
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        this.PublicKey = publicKey;
    }

    //Netodos para obtener las llaves
    public String getPrivateKeyString(){
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(this.PrivateKey.getEncoded());
        return bytesToString(pkcs8EncodedKeySpec.getEncoded());
    }

    public String getPublicKeyString(){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.PublicKey.getEncoded());
        return bytesToString(x509EncodedKeySpec.getEncoded());
    }

    //Genera el par de llaves

    public void genKeyPair(int size) throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(size);
        KeyPair kp = kpg.genKeyPair();
        
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        
        this.PrivateKey = privateKey;
        this.PublicKey = publicKey;
    }

    //Encriptar
    public String Encrypt(String plain) throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException,IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchProviderException {

        byte[] encryptedBytes; 
  
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.PublicKey);
        encryptedBytes = cipher.doFinal(plain.getBytes());

        return bytesToString(encryptedBytes);

    }

    //Desencriptar
    public String Decrypt(String result) throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        byte[] decryptedBytes;

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, this.PrivateKey);
        decryptedBytes = cipher.doFinal(stringToBytes(result));
        return new String(decryptedBytes);
    }

    //Funciones para eliminar posibles desbordamientos en los arreglos de bytes
    //Son utilizadsas en los metodos anteiroes

    public String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

    //Metodos orientadso a guardar las claves en un archivo y a leerlos

    public void saveToDiskPrivateKey(String path) throws IOException {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            out.write(this.getPrivateKeyString());
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void saveToDiskPublicKey(String path) {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            out.write(this.getPublicKeyString());
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void openFromDiskPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = this.readFileAsString(path);
        this.setPublicKeyString(content);
    }
    
    public void openFromDiskPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String content = this.readFileAsString(path);
        this.setPrivateKeyString(content);
    }
    
    
    private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

}