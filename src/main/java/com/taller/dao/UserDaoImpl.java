package com.taller.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taller.entity.User;
import com.taller.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserRepository repository;

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(long id) {
		return repository.findById(id).get();
	}

	@Override
	public User create(User user) {
		return repository.save(user);
	}

	@Override
	public User update(User user, long id) {
		User userUpdated = repository.findById(id).get();
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
	
	

}
