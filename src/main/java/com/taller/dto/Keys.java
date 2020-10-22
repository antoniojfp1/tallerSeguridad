package com.taller.dto;

public class Keys {

    private final File publicKey;
    private final File privateKey;

    public Keys(File publicKey, File privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public File getPublicKey() {
        return publicKey;
    }

    public File getPrivateKey() {
        return privateKey;
    }
    
}
