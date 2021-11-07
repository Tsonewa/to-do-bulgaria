package com.example.todobulgaria.utils;

import com.example.todobulgaria.annotations.PasswordMatches;
import com.example.todobulgaria.models.bindings.UserRegisterBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchValidator  implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegisterBindingModel user = (UserRegisterBindingModel) obj;
        return user.getPassword().equals(user.getConfirmPassword());

    }
}
