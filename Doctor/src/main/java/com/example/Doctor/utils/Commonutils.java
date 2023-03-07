package com.example.Doctor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commonutils {
    public static boolean phoneValidator(String phoneNumber){
        Pattern pattern=Pattern.compile("^\\d{10}$");
        Matcher matcher=pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
