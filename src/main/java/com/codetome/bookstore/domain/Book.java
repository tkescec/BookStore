package com.codetome.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer IDBook;
    private String ISBN;
    private String Name;
    private Double Price;
    private Integer Quantity;
    private String Image;
    private Category category;
    private Author author;

    public String getFormattedPrice() {
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(Price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getISBN().equals(book.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISBN());
    }
}
