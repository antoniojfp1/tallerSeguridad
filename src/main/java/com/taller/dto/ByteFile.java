package com.taller.dto;

public class ByteFile {
    
    private final byte[] content;
    private final String name;

    public ByteFile(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
    
}
