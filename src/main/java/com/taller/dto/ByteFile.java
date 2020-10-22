package com.taller.dto;

public class ByteFile {
    
    private final String content;
    private final String name;

    public ByteFile(String content, String name) {
        this.content = content;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
    
}
