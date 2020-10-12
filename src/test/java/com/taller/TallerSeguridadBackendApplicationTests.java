package com.taller;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taller.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TallerSeguridadBackendApplicationTests {
	
	@Autowired
	UserService userService;
	
	@Test
	public void testDependecyInjection() {
		assertNotNull(userService);
	}

}
