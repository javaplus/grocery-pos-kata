package com.barry.groceryposkata;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private int greatestId = 0;

    List<Item> itemList = new ArrayList<Item>();

    public int addItem(String itemName, double itemPrice){

        Item newItem = new Item(generateNextId());
        newItem.setPrice(itemPrice);

        itemList.add(newItem);

        return newItem.getID();

    }


    private int generateNextId(){
        return ++greatestId;
    }



}
