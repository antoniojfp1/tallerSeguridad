package com.taller.dto;

public class ByteHashedFile extends ByteFile {

    private final String md5Hash;

    public ByteHashedFile(byte[] content, String name, String md5Hash) {
        super(content, name);
        this.md5Hash = md5Hash;
    }

    public String getMd5Hash() {
        return md5Hash;
    }
    
}
