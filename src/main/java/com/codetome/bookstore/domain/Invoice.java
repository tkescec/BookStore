package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private Integer IDInvoice;
    private Payment payment;
    private User user;
    private Timestamp IssuedAt;

    public Invoice(Integer IDInvoice, Timestamp issuedAt) {
        this.IDInvoice = IDInvoice;
        IssuedAt = issuedAt;
    }
}
