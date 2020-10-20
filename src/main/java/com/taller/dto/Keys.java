package com.taller.dto;

public class Keys {

    private final ByteFile publicKey;
    private final ByteFile privateKey;

    public Keys(ByteFile publicKey, ByteFile privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ByteFile getPublicKey() {
        return publicKey;
    }

    public ByteFile getPrivateKey() {
        return privateKey;
    }
    
}
