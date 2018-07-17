package com.barry.groceryposkata;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;


public class InventoryTest {

    @Test
    public void addInventoryItem_addingNewItem_returnsGeneratedIdAsPositiveInt(){

        Inventory inventory = new Inventory();

        int itemId = inventory.addItem("Twinkies", 3.00);
        assertTrue("adding item to inventory returns positive id",  itemId > 0);
    }

}
