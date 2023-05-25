package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private Integer IDInvoice;
    private Payment payment;
    private User user;
    private Timestamp IssuedAt;
    private Double Total;

    public Invoice(Integer IDInvoice, Timestamp issuedAt) {
        this.IDInvoice = IDInvoice;
        IssuedAt = issuedAt;
    }

    public LocalDateTime getLocaleDateTime(){
        return IssuedAt.toLocalDateTime();
    }

    public String getTotalFormatted(){
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(Total);
    }
}
