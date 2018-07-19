package com.barry.groceryposkata.entities;

import lombok.Data;

@Data
public class ShoppingCartAddRequest {

    private double weight;
    private String itemName;

}
