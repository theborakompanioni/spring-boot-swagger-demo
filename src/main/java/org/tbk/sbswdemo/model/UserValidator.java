package org.tbk.sbswdemo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tbk.sbswdemo.Application;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getName().length() < 3 || user.getName().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userRepository.findByName(user.getName(), Application.standardPageRequest.get()).hasContent()) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 3) {
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
