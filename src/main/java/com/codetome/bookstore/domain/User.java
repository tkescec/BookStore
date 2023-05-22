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
    private Integer enabled;


    public User(Integer IDUser, String username) {
        this.IDUser = IDUser;
        this.username = username;
    }
}
