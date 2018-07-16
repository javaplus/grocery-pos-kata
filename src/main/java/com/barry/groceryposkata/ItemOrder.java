package com.barry.groceryposkata;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemOrder {

    public ItemOrder(Item item){
        this.item = item;
        quantity = 1;
    }

    public ItemOrder(Item item, double qty){
        this.item = item;
        this.quantity = qty;
    }

    public BigDecimal getPrice(){
        return item.getPrice().multiply(new BigDecimal(quantity));
    }
    private double quantity;

    private Item item;
}
