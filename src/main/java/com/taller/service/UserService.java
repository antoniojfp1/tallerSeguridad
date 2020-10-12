package com.taller.service;

import java.util.List;

import com.taller.entity.User;

public interface UserService {
	
	public List<User> findAll();
	public User findById(long id);
	public User create(User user);
	public User update(User user, long id);
	public void delete(long id);
	
}
