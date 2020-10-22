package com.taller.util;

import java.util.regex.*;

import org.springframework.stereotype.Component;

@Component
public class ValidatePattern {
    
    public boolean isValid(String pass){
        /// Caracteres, una mayoscula, una minuscula, un numero y un caracter especial
        Pattern pattern = Pattern.compile("[0-9][a-zA-Z][@#$%^&+=].{8,}");
        if(!pattern.matcher(pass).matches()){
            return true;
        }
        else{
            return false;
        } 
    }

}
