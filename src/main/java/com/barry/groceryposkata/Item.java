package com.barry.groceryposkata;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Item{


    private int ID;
    private String name;
    private BigDecimal price;


    public Item(){
        this.ID = 1;
    }

    public Item(int id){
        this.ID = id;
    }

    // convenience method to set price with double
    public void setPrice(double priceAsDouble){
        price = new BigDecimal(priceAsDouble).setScale(2, RoundingMode.HALF_UP);
    }


}
