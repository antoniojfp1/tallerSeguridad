package com.taller.converter;

import org.springframework.stereotype.Component;

import com.taller.entity.UserEntity;
import com.taller.model.User;

@Component
public class UserConverter {
	
	public User toModel(UserEntity entity) {
		User user = new User();
		user.setId(entity.getId());
		user.setLastname(entity.getLastname());
		user.setName(entity.getName());
		user.setPassword(entity.getPassword());
		user.setUsername(entity.getUsername());
		return user;
	}
	
	public UserEntity toEntity(User user) {
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setLastname(user.getLastname());
		entity.setName(user.getName());
		entity.setPassword(user.getPassword());
		entity.setUsername(user.getUsername());
		return entity;
	}
	
}
