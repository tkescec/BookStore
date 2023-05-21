package com.codetome.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Integer IDInvoice;
    private Integer PaymentID;
    private Integer UserID;
    private Timestamp IssuedAt;

    public InvoiceDto(Integer paymentID, Integer userID) {
        PaymentID = paymentID;
        UserID = userID;
    }
}
