package com.taller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taller.entity.UserEntity;
import com.taller.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUnitTest {
	
	@Autowired
	private UserRepository repository;
	
	@Test
	public void postUser() {
		UserEntity user = new UserEntity();
		user.setId(1L);
		user.setUsername("jcpenap");
		user.setPassword("123456");
		repository.save(user);
		UserEntity userSaved = repository.findById(1L).get();
		assertNotNull(userSaved);
	}
}
