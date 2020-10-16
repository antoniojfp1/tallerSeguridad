package com.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taller.model.User;
import com.taller.security.JWTToken;

@RestController
public class LoginController {
	
	@Autowired
	private JWTToken jwtToken;
	
	@PostMapping("/login")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		String token = jwtToken.generate(username);
		User user = new User();
		user.setUsername(username);
		user.setToken(token);		
		return user;
		
	}
	
}
