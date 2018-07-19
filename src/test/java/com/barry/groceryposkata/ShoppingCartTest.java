package com.barry.groceryposkata;

import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.service.Inventory;
import com.barry.groceryposkata.service.ShoppingCart;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @Mock
    private Inventory mockedInventory = mock(Inventory.class);

    @Before
    public void initializeShoppingCart(){

        shoppingCart = new ShoppingCart();
        shoppingCart.setInventory(mockedInventory);
    }

     private void addItemToShoppingCartWithWeight(int id, String name,  double price, double quantity){
        Item item = new Item(id);
        item.setPrice(price);
        item.setName(name);

        when(mockedInventory.getItemByName(name)).thenReturn(item);

        shoppingCart.addItem(name, quantity);
    }

    private void addItemToShoppingCart(int id, String name,  double price){
        Item item = new Item(id);
        item.setPrice(price);
        item.setName(name);

        when(mockedInventory.getItemByName(name)).thenReturn(item);

        shoppingCart.addItem(name);
    }


    @Test
    public void getItemTotal_whenAddingSingleItem_totalPriceEqualsItemPrice(){

        String itemName = "KitKat";
        Item item = new Item(1);
        item.setPrice(10.00);
        item.setName(itemName);

        when(mockedInventory.getItemByName(itemName)).thenReturn(item);

        shoppingCart.addItem(itemName);


        assertEquals(item.getPrice().doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoOfSameItems_totalPriceIsDoubleItemPrice(){
        String itemName = "Bottle Caps";
        Item item = new Item(1);
        item.setPrice(10.00);
        item.setName(itemName);

        when(mockedInventory.getItemByName(itemName)).thenReturn(item);

        shoppingCart.addItem(itemName);
        shoppingCart.addItem(itemName);

        BigDecimal twiceThePriceValue = item.getPrice().multiply(new BigDecimal(2));
        assertEquals(twiceThePriceValue.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoDifferentItems_totalPriceIsSumOfItemPrices(){
        String item1Name = "PayDay";
        Item item1 = new Item(1);
        item1.setPrice(10.00);
        String item2Name = "Zero";
        Item item2 = new Item(2);
        item2.setPrice(5.50);

        when(mockedInventory.getItemByName(item1Name)).thenReturn(item1);
        when(mockedInventory.getItemByName(item2Name)).thenReturn(item2);

        shoppingCart.addItem(item1Name);
        shoppingCart.addItem(item2Name);

        BigDecimal sumOfPrices = item1.getPrice().add(item2.getPrice());
        assertEquals(sumOfPrices.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingItemAndWeight_totalPriceReflectsItemWithWeight(){

        addItemToShoppingCartWithWeight(1, "Skittles",  3.00, 3.0);


        assertEquals(9.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingMultipleItemsWithWeight_totalPriceReflectsItemsWithWeight(){

        addItemToShoppingCartWithWeight(1, "Chicken Chicken", 3.00, 3.0);

        addItemToShoppingCartWithWeight(2, "Courageous Chicken", 2.00, 1.5);

        assertEquals(12.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingItemsWithAndWithoutWeight_totalPriceReflectsAllItems(){

        addItemToShoppingCartWithWeight(1, "Brave Chicken",3.00, 2.5);

        addItemToShoppingCart(2, "Pirate Puffy Pants Pants",2.00);

        addItemToShoppingCart(3, "Cheesey Puffs",  1.25);

        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
    }

    @Test
    public void removeItem_removingItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice(){
        addItemToShoppingCartWithWeight(1, "Chocolaty Ovaltine", 3.00, 2.5);

        addItemToShoppingCart(2, "Orphan Annie's Decoder Ring", 2.00);


        int itemId = 3;

        addItemToShoppingCart(itemId, "LifeBuoy Soap", 1.25);


        // assert value before removal
        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
        shoppingCart.removeOrderedItem(itemId);
        // assert value after removal
        assertEquals(9.50, shoppingCart.getItemTotal(), .001);

    }


    @Test
    public void removeItem_removingWeightedItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice(){

        int itemId = 1;
        addItemToShoppingCartWithWeight(itemId, "Buckeye", 3.00, 2.5);

        addItemToShoppingCart(2, "Wolverine", 2.00);

        addItemToShoppingCart(3, "Tar Heel", 1.25);

        // assert value before removal
        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
        shoppingCart.removeOrderedItem(itemId);
        // assert value after removal
        assertEquals(3.25, shoppingCart.getItemTotal(), .001);

    }

}
