package com.taller.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taller.dto.ByteFile;
import com.taller.security.Security;
import com.taller.util.CustomException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private Security security;

    @Override
    public ByteFile encryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new ByteFile(new String(security.encriptar(file.getBytes(), password), StandardCharsets.UTF_8), file.getOriginalFilename());
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser encriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public ByteFile decryptFile(MultipartFile file, String password) throws CustomException {
        try {
            return new ByteFile(new String(security.desencriptar(file.getBytes(), password)), file.getOriginalFilename());
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }
    
}
