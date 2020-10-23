package com.taller.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taller.entity.UserEntity;
import com.taller.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserRepository repository;

	@Override
	public List<UserEntity> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<UserEntity> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public UserEntity create(UserEntity user) {
		return repository.save(user);
	}

	@Override
	public UserEntity update(UserEntity user, long id) {
		UserEntity userUpdated = repository.findById(id).get();
		userUpdated.setLastname(user.getLastname());
		userUpdated.setName(user.getName());
		userUpdated.setPassword(user.getPassword());
		userUpdated.setUsername(user.getUsername());
		return repository.save(userUpdated);
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<UserEntity> findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	

}
