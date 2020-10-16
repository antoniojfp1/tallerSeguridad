package com.taller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taller.model.User;
import com.taller.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> findAll() throws Exception {
		return service.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User findById(@PathVariable long id) throws Exception {
		return service.findById(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public User create(@RequestBody User user) throws Exception {
		return service.create(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public User update(@RequestBody User user, @PathVariable long id) throws Exception {
		return service.update(user, id);
	}
	
	
}
