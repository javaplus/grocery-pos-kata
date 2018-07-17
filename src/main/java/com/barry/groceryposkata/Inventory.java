package com.barry.groceryposkata;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    List<Item> itemList = new ArrayList<Item>();

    public int addItem(String itemName, double itemPrice){

        Item newItem = new Item(1);
        newItem.setPrice(itemPrice);

        itemList.add(newItem);

        return newItem.getID();

    }



}
