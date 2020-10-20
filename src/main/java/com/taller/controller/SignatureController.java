package com.taller.controller;

import com.taller.service.SignatureService;
import com.taller.util.CustomException;
import com.taller.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/signature")
public class SignatureController {

    @Autowired
    private SignatureService signatureService;

    @GetMapping(value = "/generateKeys")
	public ResponseEntity<Response<Object>> generateKeys() {
		try {
			return ResponseEntity.ok(new Response<>(signatureService.generateKeys(), "Llaves generadas"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}

    @PostMapping(value = "/encryptWithKey")
	public ResponseEntity<Response<Object>> encryptWithKey(@RequestParam("file") MultipartFile file, @RequestParam("publicKey") MultipartFile publicKey) {
        try {
			return ResponseEntity.ok(new Response<>(signatureService.encrypt(file, publicKey), "Encriptado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}

	@PostMapping(value = "/decryptWithKey")
	public ResponseEntity<Response<Object>> decryptWithKey(@RequestParam("file") MultipartFile file, @RequestParam("privateKey") MultipartFile privateKey) throws CustomException {
        try {
			return ResponseEntity.ok(new Response<>(signatureService.decrypt(file, privateKey), "Desencriptado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}
    
}
