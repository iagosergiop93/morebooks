package com.morebooks.morebooks.controllers;

import com.morebooks.morebooks.domain.dtos.Credentials;
import com.morebooks.morebooks.domain.dtos.Principal;
import com.morebooks.morebooks.entities.User;
import com.morebooks.morebooks.services.UsersService;
import com.morebooks.morebooks.validators.users.LoginValidator;
import com.morebooks.morebooks.validators.users.UsersInsertValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UsersController {

    private static Logger LOG = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersInsertValidator usersInsertValidator;

    @Autowired
    private LoginValidator loginValidator;

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/account-load")
    public ResponseEntity<Principal> accountLoad(HttpServletRequest req, HttpServletResponse resp) {

        String authHeader = req.getHeader("authorization");
        Principal principal = usersService.accountLoad(authHeader);

        if(principal == null || principal.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok()
                .body(principal);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody Credentials cred) throws Exception {
        Principal principal = null;

        loginValidator.validate(cred);

        principal = usersService.login(cred);

        String jwt = usersService.createJwt(principal);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization","Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(principal);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/user")
    public ResponseEntity<Principal> createUser(@RequestBody User user) throws Exception {

        usersInsertValidator.validate(user);
        Principal principal = usersService.createUser(user);

        String jwt = usersService.createJwt(principal);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("auth-token","Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(principal);
    }

}
