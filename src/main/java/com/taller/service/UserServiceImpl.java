package com.taller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.converter.UserConverter;
import com.taller.dao.UserDao;
import com.taller.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserConverter converter;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> findAll() {
		 return userDao
				 .findAll()
				 .stream()
				 .map(entity -> converter.toModel(entity))
				 .collect(Collectors.toList());
	}

	@Override
	public User findById(long id) throws Exception{
		return userDao.findById(id)
				.map(entity -> converter.toModel(entity))
				.orElseThrow(Exception::new);
	}

	@Override
	public User create(User user) {
		return converter.toModel(userDao.create(converter.toEntity(user)));
	}

	@Override
	public User update(User user, long id) {
		return converter.toModel(userDao.update(converter.toEntity(user), id));
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}
	
}
