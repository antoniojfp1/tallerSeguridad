package com.taller.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taller.converter.UserConverter;
import com.taller.dao.UserDao;
import com.taller.dto.Login;
import com.taller.dto.User;
import com.taller.security.JWTToken;
import com.taller.util.CustomException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserConverter converter;
	
	@Autowired
	private JWTToken jwtToken;

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
	public User findById(long id) throws CustomException{
		return userDao.findById(id)
				.map(entity -> converter.toModel(entity))
				.orElseThrow(() -> new CustomException("Usuario no encontrado",
						String.valueOf(HttpStatus.PRECONDITION_FAILED.value())));
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

	@Override
	public Login autenticate(String username, String password) throws CustomException {
		return userDao.findByUsernameAndPassword(username, password).map(entity -> {
				String token = jwtToken.generate(username);
				Login login = new Login();
				login.setUsername(entity.getUsername());
				login.setToken(token);
				return login;
			}).orElseThrow(() -> new CustomException("Usuario no autenticado",
					String.valueOf(HttpStatus.PRECONDITION_FAILED.value())));
		
	}
	
}
