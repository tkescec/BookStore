package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer IDUser;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private Role role;
    private City city;

    public User(Integer IDUser, String firstName, String lastName) {
        this.IDUser = IDUser;
        FirstName = firstName;
        LastName = lastName;
    }
}
