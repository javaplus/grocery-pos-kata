package com.barry.groceryposkata;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    List<Item> itemList = new ArrayList<Item>();

    public void addItem(Item item) {
        itemList.add(item);
    }


    public double getItemTotal(){

        // add up all the item prices.
        BigDecimal total = itemList.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.doubleValue();
    }

}
