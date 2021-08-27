package com.morebooks.morebooks.entities;

import lombok.Data;

@Data
public class User {

    private String id;
    private String username;
    private String passwd;
    private String email;
    private String firstName;
    private String lastName;

}
