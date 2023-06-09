package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer IDLog;
    private String Ip;
    private Timestamp LastLogin;
    private User user;

    public LocalDateTime getLocaleDateTime(){
        return LastLogin.toLocalDateTime();
    }
}
