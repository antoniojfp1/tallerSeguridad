package com.taller.service;

import com.taller.dto.ByteHashedFile;
import com.taller.dto.Keys;
import com.taller.util.CustomException;

import org.springframework.web.multipart.MultipartFile;

public interface SignatureService {

    public Keys generateKeys() throws CustomException;
    public ByteHashedFile encrypt(MultipartFile file, MultipartFile publicKey) throws CustomException;
    public ByteHashedFile decrypt(MultipartFile file, MultipartFile publicKey) throws CustomException;
    
}
