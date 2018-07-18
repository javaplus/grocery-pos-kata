package com.barry.groceryposkata.service;

import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.exception.DuplicateItemException;
import com.barry.groceryposkata.exception.ItemNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
// should only be one inventory for all
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Inventory {

    private int greatestId = 0;

    @Getter
    @Setter
    private Map<String, Item> itemMap = new HashMap<>();


    public int addItem(String itemName, double itemPrice) throws DuplicateItemException {

        if(itemMap.containsKey(itemName)){
            String message = String.format("Item with name:%s already exists in inventory. Perhaps you want to update the item.", itemName);
            throw new DuplicateItemException(message);
        }

        Item newItem = new Item(generateNextId());
        newItem.setPrice(itemPrice);
        newItem.setName(itemName);

        itemMap.put(itemName, newItem);

        return newItem.getID();

    }


    public int getCount(){
        return itemMap.size();
    }

    public Item getItemByName(String name){
        Item foundItem = itemMap.get(name);
        return foundItem;
    }

    public void updateItem(Item item) throws ItemNotFoundException{

        if(itemMap.containsKey(item.getName()) == false){
            String message = String.format("Item with name:%s not found in inventory.", item.getName());
            throw new ItemNotFoundException(message);
        }
        // if item does exist update it replace or put does the same thing if it exists.
        itemMap.put(item.getName(), item);

    }


    private int generateNextId(){
        return ++greatestId;
    }



}
