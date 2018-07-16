package com.barry.groceryposkata;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item{

    private int ID;
    private BigDecimal price;

    public Item(int id){
        this.ID = id;
    }

    // convenience method to set price with double
    public void setPrice(double priceAsDouble){
        price = new BigDecimal(priceAsDouble);
    }


}
