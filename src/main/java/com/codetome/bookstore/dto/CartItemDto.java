package com.codetome.bookstore.dto;

import com.codetome.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer Quantity;
    private Book book;
}
