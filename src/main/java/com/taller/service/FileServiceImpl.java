package com.taller.service;

import com.taller.security.Security;
import com.taller.util.ByteArrayMultipartFile;
import com.taller.util.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public MultipartFile encryptFile(MultipartFile file, String password) throws CustomException {
        try {
            byte[] encryptedBytes = new Security().encriptar(file.getBytes(), password);
            return new ByteArrayMultipartFile(encryptedBytes, file);
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser encriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public MultipartFile decryptFile(MultipartFile file, String password) throws CustomException {
        try {
            byte[] decryptedBytes = new Security().desencriptar(file.getBytes(), password);
            return new ByteArrayMultipartFile(decryptedBytes, file);
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }
    
}
