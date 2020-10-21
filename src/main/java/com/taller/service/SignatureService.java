package com.taller.service;

import com.taller.dto.ByteFile;
import com.taller.dto.ByteHashedFile;
import com.taller.dto.Keys;
import com.taller.util.CustomException;

import org.springframework.web.multipart.MultipartFile;

public interface SignatureService {

    public Keys generateKeys() throws CustomException;
    public ByteHashedFile encrypt(MultipartFile file, MultipartFile publicKey) throws CustomException;
    public ByteFile decrypt(MultipartFile file, MultipartFile publicKey, String md5Hash) throws CustomException;
    
}
