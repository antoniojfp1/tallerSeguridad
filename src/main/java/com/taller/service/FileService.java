package com.taller.service;

import com.taller.dto.ByteFile;
import com.taller.util.CustomException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    public ByteFile encryptFile(MultipartFile file, String password) throws CustomException;
    public ByteFile decryptFile(MultipartFile file, String password) throws CustomException;

}
