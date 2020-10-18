package com.taller.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ByteArrayMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String contentType;
    private final String filename;
    private final String name;

    public ByteArrayMultipartFile(byte[] content, MultipartFile file) {
        this.content = content;
        this.contentType = file.getContentType();
        this.filename = file.getOriginalFilename();
        this.name = file.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream stream = new FileOutputStream(dest)) {
            stream.write(content);
        }
    }
    
}
