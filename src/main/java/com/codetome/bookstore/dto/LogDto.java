package com.codetome.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    private Integer IDLog;
    private String Ip;
    private Timestamp LastLogin;
    private Integer UserID;

    public LogDto(String ip, Timestamp lastLogin, Integer userID) {
        Ip = ip;
        LastLogin = lastLogin;
        UserID = userID;
    }
}
