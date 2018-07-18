package com.barry.groceryposkata.service;


import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.entities.ItemOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {

    List<ItemOrder> itemList = new ArrayList<ItemOrder>();

    public void addItem(Item item) {

        this.addItem(item, 1.00);
    }

    public void addItem(Item item, double weight) {

        ItemOrder itemOrder = new ItemOrder(item, weight);

        itemList.add(itemOrder);
    }


    public double getItemTotal(){

        // add up all the item prices.
        BigDecimal total = itemList.stream()
                .map(ItemOrder::getPrice) // get price of items
                .reduce(BigDecimal.ZERO, BigDecimal::add); // add price of all items
        return total.doubleValue();
    }

    /**
     * Removes the entire ordered item. Including all quantities
     */
    public void removeOrderedItem(int idOfItemToRemove){
        this.itemList = itemList.stream().filter(p-> p.getItem().getID() != idOfItemToRemove).collect(Collectors.toList());
    }



}
