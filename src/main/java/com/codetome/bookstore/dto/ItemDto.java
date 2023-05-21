package com.codetome.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Integer IDItem;
    private Integer Quantity;
    private Double PriceByPiece;
    private Double TotalPrice;
    private Integer InvoiceID;
    private Integer BookID;

    public ItemDto(Integer quantity, Double priceByPiece, Double totalPrice, Integer invoiceID, Integer bookID) {
        Quantity = quantity;
        PriceByPiece = priceByPiece;
        TotalPrice = totalPrice;
        InvoiceID = invoiceID;
        BookID = bookID;
    }
}
