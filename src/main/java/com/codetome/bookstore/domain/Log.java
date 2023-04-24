package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer IDLog;
    private String Ip;
    private Timestamp LastLogin;
    private User user;
}
