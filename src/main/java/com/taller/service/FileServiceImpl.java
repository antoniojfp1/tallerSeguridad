package com.taller.service;

import com.taller.security.Security;
import com.taller.util.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public byte[] encryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new Security().encriptar(file.getBytes(), password);
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser encriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public byte[] decryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new Security().desencriptar(file.getBytes(), password);
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }
    
}
