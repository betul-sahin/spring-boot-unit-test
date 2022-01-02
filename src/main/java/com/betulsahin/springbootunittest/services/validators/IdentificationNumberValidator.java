package com.betulsahin.springbootunittest.services.validators;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IdentificationNumberValidator implements Predicate<String> {

    @Override
    public boolean test(String identificationNumber) {
        String regex = "^[1-9]{1}[0-9]{9}[02468]{1}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identificationNumber);

        return matcher.find();
    }
}
