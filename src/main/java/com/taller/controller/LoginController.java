package com.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taller.service.UserService;
import com.taller.util.CustomException;
import com.taller.util.Response;

@RestController
public class LoginController {

	@Autowired
	private UserService service;

	@PostMapping("/login")
	public ResponseEntity<Response<Object>> login(@RequestParam("user") String username,
			@RequestParam("password") String password) {
		try {
			return ResponseEntity.ok(new Response<>(service.autenticate(username, password), "Autenticado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode())).body(new Response<>(null, e.getMessage()));
		}
	}

}
