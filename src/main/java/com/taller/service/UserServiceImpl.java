package com.taller.service;

import java.security.NoSuchAlgorithmException;
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
import com.taller.security.Security;
import com.taller.util.CustomException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserConverter converter;
	
	@Autowired
	private JWTToken jwtToken;
	
	@Autowired
	private Security security;

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
	public User create(User user) throws CustomException {
		try {
			user.setPassword(security.sha1Password(user.getPassword()));
			return converter.toModel(userDao.create(converter.toEntity(user)));
		} catch (NoSuchAlgorithmException e) {
			throw new CustomException("Error al crear el usuario",
					String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
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
		try {
			return userDao.findByUsernameAndPassword(username, security.sha1Password(password)).map(entity -> {
					String token = jwtToken.generate(username);
					Login login = new Login();
					login.setUsername(entity.getUsername());
					login.setToken(token);
					return login;
				}).orElseThrow(CustomException::new);
		} catch (NoSuchAlgorithmException e) {
			throw new CustomException("Error al intentar autenticar el usuario",
					String.valueOf(HttpStatus.PRECONDITION_FAILED.value())); 
		} catch (CustomException e) {
			throw new CustomException("Usuario no autenticado",
					String.valueOf(HttpStatus.PRECONDITION_FAILED.value()));
		}
	}
	
}
