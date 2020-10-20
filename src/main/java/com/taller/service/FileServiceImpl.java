package com.taller.service;

import com.taller.dto.ByteFile;
import com.taller.security.Security;
import com.taller.util.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private Security security;

    @Override
    public ByteFile encryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new ByteFile(security.encriptar(file.getBytes(), password), file.getOriginalFilename());
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser encriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public ByteFile decryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new ByteFile(security.desencriptar(file.getBytes(), password), file.getOriginalFilename());
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }
    
}
