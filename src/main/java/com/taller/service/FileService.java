package com.taller.service;

import com.taller.util.CustomException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    public byte[] encryptFile(MultipartFile file, String password) throws CustomException;
    public byte[] decryptFile(MultipartFile file, String password) throws CustomException;

}
