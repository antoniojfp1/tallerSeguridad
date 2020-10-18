package com.taller.service;

import com.taller.util.CustomException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    public MultipartFile encryptFile(MultipartFile file, String password) throws CustomException;
    public MultipartFile decryptFile(MultipartFile file, String password) throws CustomException;

}
