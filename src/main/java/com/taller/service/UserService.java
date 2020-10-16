package com.taller.service;

import java.util.List;

import com.taller.model.User;

public interface UserService {
	
	public List<User> findAll() throws Exception;
	public User findById(long id) throws Exception;
	public User create(User user) throws Exception;
	public User update(User user, long id) throws Exception;
	public void delete(long id) throws Exception;
	
}
