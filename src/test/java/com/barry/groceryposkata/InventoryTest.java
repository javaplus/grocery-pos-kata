package com.barry.groceryposkata;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class InventoryTest {

    private Inventory inventory;

    @Before
    public void initializeInventory(){
        this.inventory = new Inventory();
    }

    @Test
    public void addItem_addingNewItem_returnsGeneratedIdAsPositiveInt(){

        int itemId = inventory.addItem("Twinkies", 3.00);
        assertTrue("adding item to inventory returns positive id",  itemId > 0);
    }


    @Test
    public void addItem_addingTwoItems_generatesUniqueIdForEach(){

        int itemId1 = inventory.addItem("Twinkies", 3.00);
        int itemId2 = inventory.addItem("Swiss Cake Rolls", 2.75);
        assertNotEquals(itemId1, itemId2);
    }


    @Test
    public void addItem_addingItem_increasesInventoryCount(){
        int initialSize = inventory.getCount();
        inventory.addItem("Twizzlers", 1.99);
        assertEquals(initialSize+1, inventory.getCount());
    }


    @Test
    public void getItemByName_whenCalledWithItemInInventory_returnsThatItem(){
        String name = "Twizzlers";
        int id = inventory.addItem(name, 1.99);
        assertEquals(id, inventory.getItemByName(name).getID());
    }

}
