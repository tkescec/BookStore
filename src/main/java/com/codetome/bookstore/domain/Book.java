package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer IDBook;
    private String ISBN;
    private String Name;
    private Double Price;
    private Integer Quantity;
    private String ImagePath;
    private Category category;
    private Author author;

    public String getFormattedPrice() {
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(Price);
    }
}
