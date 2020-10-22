package com.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.taller.service.FileService;
import com.taller.util.CustomException;
import com.taller.util.Response;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping(value = "/encrypt")
	public ResponseEntity<Response<Object>> encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) {
		try {
			return ResponseEntity.ok(new Response<>(fileService.encryptFile(file, password), "Encriptado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}

	@PostMapping(value = "/decrypt")
	public ResponseEntity<Response<Object>> decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) {
		try {
			return ResponseEntity.ok(new Response<>(fileService.decryptFile(file, password), "Desencriptado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}

}
