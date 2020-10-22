package com.taller.service;

import org.springframework.web.multipart.MultipartFile;

import com.taller.dto.File;
import com.taller.util.CustomException;

public interface FileService {
    
    public File encryptFile(MultipartFile file, String password) throws CustomException;
    public File decryptFile(MultipartFile file, String password) throws CustomException;

}
