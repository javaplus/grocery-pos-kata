package com.barry.groceryposkata.service;

import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.exception.DuplicateItemException;
import com.barry.groceryposkata.exception.ItemNotFoundException;
import com.barry.groceryposkata.service.Inventory;
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
    public void addItem_addingNewItem_returnsGeneratedIdAsPositiveInt() throws Exception{

        int itemId = inventory.addItem("Twinkies", 3.00);
        assertTrue("adding item to inventory returns positive id",  itemId > 0);
    }


    @Test
    public void addItem_addingTwoItems_generatesUniqueIdForEach() throws Exception{

        int itemId1 = inventory.addItem("Twinkies", 3.00);
        int itemId2 = inventory.addItem("Swiss Cake Rolls", 2.75);
        assertNotEquals(itemId1, itemId2);
    }


    @Test
    public void addItem_addingItem_increasesInventoryCount() throws Exception{
        int initialSize = inventory.getCount();
        inventory.addItem("Twizzlers", 1.99);
        assertEquals(initialSize+1, inventory.getCount());
    }


    @Test
    public void getItemByName_whenCalledWithItemInInventory_returnsThatItem() throws Exception{
        String name = "Twizzlers";
        int id = inventory.addItem(name, 1.99);
        assertEquals(id, inventory.getItemByName(name).getID());
    }

    @Test(expected = DuplicateItemException.class)
    public void addItem_addingItemWithNameThatAlreadyExistsInInventory_causesDuplicateItemExceptionToBeThrown() throws Exception{

        String name = "Bacon!!!";  // can't have too much bacon...ok maybe in this test case you can :(
        inventory.addItem(name, 1.00);
        inventory.addItem(name, 2.50);
    }

    @Test(expected = ItemNotFoundException.class)
    public void updateItem_attemptToUpdateItemThatDoesNotExist_throwsItemNotFoundExcpetion() throws Exception{

        Item nonExistingItem = new Item(1);
        nonExistingItem.setPrice(579.99);
        nonExistingItem.setName("Flux Capacitor");

        inventory.updateItem(nonExistingItem);
    }

    @Test
    public void updateItem_updatingItemPrice_lastPriceIsKept() throws Exception{

        String name = "Bacon!!!";
        inventory.addItem(name, 1.00);
        Item item = inventory.getItemByName(name);

        // update item price
        item.setPrice(2.99);

        inventory.updateItem(item);

        assertEquals(2.99, inventory.getItemByName(name).getPrice().doubleValue(), 0.001);

    }



}
