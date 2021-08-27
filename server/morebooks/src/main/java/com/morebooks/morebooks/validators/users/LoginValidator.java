package com.morebooks.morebooks.validators.users;

import com.morebooks.morebooks.domain.dtos.Credentials;
import com.morebooks.morebooks.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class LoginValidator implements Validator<Credentials> {

    @Override
    public void validate(Credentials request) throws Exception {
        if(request == null || request.getUsername() == null || request.getPasswd() == null) {
            throw new Exception("Invalid request");
        }
    }
}
