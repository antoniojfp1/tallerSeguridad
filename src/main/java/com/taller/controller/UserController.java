package com.taller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taller.dto.User;
import com.taller.service.UserService;
import com.taller.util.CustomException;
import com.taller.util.Response;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Response<Object>> findAll() throws Exception {
		try {
			return ResponseEntity.ok(new Response<>(service.findAll(), "Encontrado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode()))
					.body(new Response<>(null, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<Object>> findById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(new Response<>(service.findById(id), "Encontrado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode()))
					.body(new Response<>(null, e.getMessage()));
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Response<Object>> create(@RequestBody User user) {
		try {
			return ResponseEntity.ok(new Response<>(service.create(user), "Creado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode()))
					.body(new Response<>(null, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody User user, @PathVariable long id) {
		try {
			return ResponseEntity.ok(new Response<>(service.update(user, id), "Actualizado"));
		} catch (CustomException e) {
			return ResponseEntity.status(Integer.valueOf(e.getErrorCode()))
					.body(new Response<>(null, e.getMessage()));
		}
	}
	
	
}
