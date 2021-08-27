package com.morebooks.morebooks.validators.users;

import com.morebooks.morebooks.entities.User;
import com.morebooks.morebooks.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class UsersInsertValidator implements Validator<User> {

    @Override
    public void validate(User request) throws Exception {
        if(request.getUsername() == null || request.getPasswd() == null ||
            request.getFirstName() == null || request.getLastName() == null ||
                request.getEmail() == null
        ) {
            throw new Exception("Invalid request");
        }
    }
}
