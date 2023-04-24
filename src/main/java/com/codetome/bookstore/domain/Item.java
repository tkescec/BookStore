package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Integer IDItem;
    private Integer Quantity;
    private Double PriceByPiece;
    private Double TotalPrice;
    private Invoice invoice;
    private Book book;
}
