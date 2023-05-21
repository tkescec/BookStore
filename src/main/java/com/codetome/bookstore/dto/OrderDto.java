package com.codetome.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
