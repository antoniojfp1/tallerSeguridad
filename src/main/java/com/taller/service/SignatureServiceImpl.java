package com.taller.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.taller.dto.ByteFile;
import com.taller.dto.ByteHashedFile;
import com.taller.dto.File;
import com.taller.dto.Keys;
import com.taller.security.Security;
import com.taller.util.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SignatureServiceImpl implements SignatureService {

    @Autowired
    private Security security;

    @Override
    public Keys generateKeys() throws CustomException {
        try {
            KeyPair keyPair = security.genAsymetricKeyPair();
            ByteFile publicKey = new ByteFile(keyPair.getPublic().getEncoded(), "key.pub");
            ByteFile privateKey = new ByteFile(keyPair.getPrivate().getEncoded(), "key.pri");
            
            File publiK = new File();
            publiK.setContent(Base64.getEncoder().encodeToString(publicKey.getContent()));
            
            File privateK = new File();
            privateK.setContent(Base64.getEncoder().encodeToString(privateKey.getContent()));
            
            return new Keys(publiK, privateK);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("Llaves no pudieron ser creadas",
                    String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public ByteHashedFile encrypt(MultipartFile file, MultipartFile publicKey) throws CustomException {
        try {
            byte[] data = file.getBytes();
            byte[] encryptedBytes = security.asymetricEncrypt(file.getBytes(), publicKey.getBytes());
            return new ByteHashedFile(encryptedBytes, file.getOriginalFilename(), security.generateMD5Hash(data));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
        | BadPaddingException | InvalidKeySpecException | IOException e) {
            throw new CustomException("Archivo no pudo ser encriptado",
                String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
}
    }

    @Override
    public ByteFile decrypt(MultipartFile file, MultipartFile privateKey, String md5Hash) throws CustomException {

        try {
            byte[] decryptedBytes = security.asymetricDecrypt(file.getBytes(), privateKey.getBytes());
            String newHash = security.generateMD5Hash(decryptedBytes);
            
            if (!newHash.equals(md5Hash)) {
                throw new CustomException("No se pudo validar la integridad del archivo",
                    String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
            }

            return new ByteFile(decryptedBytes, file.getOriginalFilename());

        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeySpecException | IOException e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
                String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    
    }
    
}
