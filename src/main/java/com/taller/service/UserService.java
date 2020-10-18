package com.taller.service;

import java.util.List;

import com.taller.dto.Login;
import com.taller.dto.User;
import com.taller.util.CustomException;

public interface UserService {
	
	public List<User> findAll() throws CustomException;
	public User findById(long id) throws CustomException;
	public User create(User user) throws CustomException;
	public User update(User user, long id) throws CustomException;
	public void delete(long id) throws CustomException;
	public Login autenticate(String username, String password) throws CustomException;
	
}
