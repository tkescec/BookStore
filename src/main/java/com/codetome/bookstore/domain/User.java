package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer IDUser;
    private String username;
    private String password;
    private String FirstName;
    private String LastName;
    private Integer enabled;


    public User(Integer IDUser, String username) {
        this.IDUser = IDUser;
        this.username = username;
    }

    public User(Integer IDUser, String username, String firstName, String lastName) {
        this.IDUser = IDUser;
        this.username = username;
        FirstName = firstName;
        LastName = lastName;
    }
}
