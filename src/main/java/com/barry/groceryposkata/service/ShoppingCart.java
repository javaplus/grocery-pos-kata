package com.barry.groceryposkata.service;


import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.entities.ItemOrder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCart {

    @Autowired
    @Setter
    private Inventory inventory;

    List<ItemOrder> itemList = new ArrayList<ItemOrder>();

    public void addItem(String name) {

        this.addItem(name, 1.00);
    }

    public void addItem(String name, double weight) {

        ItemOrder itemOrder = new ItemOrder(inventory.getItemByName(name), weight);

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
