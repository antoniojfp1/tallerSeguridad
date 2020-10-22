package com.taller.Util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taller.util.ValidatePattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatePatternTest {
    
    private static final String PASSWORD = "Jfantonio1.";

    @Autowired
    private ValidatePattern validation;

    @Test
    public void vapasswordValid() throws Exception{
        boolean isValid = validation.isValid(PASSWORD);
        assertEquals(isValid, true);
    }
}
