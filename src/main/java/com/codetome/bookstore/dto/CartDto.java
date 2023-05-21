package com.codetome.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> items;

    public String getTotalFormatted(){
        Double total = 0.00;
        DecimalFormat df= new DecimalFormat("0.00");

        for (CartItemDto item : items) {
            total += item.getTotal();
        }

        return df.format(total);
    }

    public Double getTotal(){
        Double total = 0.00;

        for (CartItemDto item : items) {
            total += item.getTotal();
        }

        return total;
    }
}
