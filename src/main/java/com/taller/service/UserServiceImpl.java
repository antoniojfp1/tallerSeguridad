package com.taller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller.dao.UserDao;
import com.taller.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public User create(User user) {
		return userDao.create(user);
	}

	@Override
	public User update(User user, long id) {
		return userDao.update(user, id);
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}
	
}
