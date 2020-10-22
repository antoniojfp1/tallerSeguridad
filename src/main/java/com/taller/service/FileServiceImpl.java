package com.taller.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taller.dto.ByteFile;
import com.taller.dto.File;
import com.taller.security.Security;
import com.taller.util.CustomException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private Security security;

    @Override
    public File encryptFile(MultipartFile file, String password) throws CustomException {
        try {
            ByteFile byteFile = new ByteFile(security.encriptar(file.getBytes(), password), file.getOriginalFilename());
            File f = new File();
            f.setContent(Base64.getEncoder().encodeToString(byteFile.getContent()));
			return f;
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser encriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }

    @Override
    public File decryptFile(MultipartFile file, String password) throws CustomException {
        try {
        	ByteFile byteFile = new ByteFile(security.desencriptar(file.getBytes(), password), file.getOriginalFilename());
        	File f = new File();
            f.setContent(Base64.getEncoder().encodeToString(byteFile.getContent()));
            return f;
        } catch (Exception e) {
            throw new CustomException("Archivo no pudo ser desencriptado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
        }
    }
    
}
