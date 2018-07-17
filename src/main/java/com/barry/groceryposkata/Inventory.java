package com.barry.groceryposkata;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// should only be one inventory for all
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Inventory {

    private int greatestId = 0;

    @Getter
    private List<Item> itemList = new ArrayList<Item>();


    public int addItem(String itemName, double itemPrice){

        Item newItem = new Item(generateNextId());
        newItem.setPrice(itemPrice);

        itemList.add(newItem);

        return newItem.getID();

    }

    public int getCount(){
        return itemList.size();
    }


    private int generateNextId(){
        return ++greatestId;
    }



}
