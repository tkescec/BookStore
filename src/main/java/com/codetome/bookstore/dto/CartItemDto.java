package com.codetome.bookstore.dto;

import com.codetome.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer Quantity;
    private Book book;

    public String getTotalFormatted(){
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(book.getPrice() * Quantity);
    }

    public Double getTotal(){
        return book.getPrice() * Quantity;
    }
}
