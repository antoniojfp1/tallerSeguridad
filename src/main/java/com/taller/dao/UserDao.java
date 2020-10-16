package com.taller.dao;

import java.util.List;
import java.util.Optional;

import com.taller.entity.UserEntity;

public interface UserDao {
	public List<UserEntity> findAll();
	public Optional<UserEntity> findById(long id);
	public UserEntity create(UserEntity user);
	public UserEntity update(UserEntity user, long id);
	public void delete(long id);
	
}
