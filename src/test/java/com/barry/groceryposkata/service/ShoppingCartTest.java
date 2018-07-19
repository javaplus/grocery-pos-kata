package com.barry.groceryposkata.service;

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

     private void addItemToShoppingCartWithWeight(int id, String name,  double price, double quantity) throws Exception{
        Item item = new Item(id);
        item.setPrice(price);
        item.setName(name);
        // questionable if I should do stubbing in helper method, but it makes
        // the tests more concise and easier to read
        when(mockedInventory.getItemByName(name)).thenReturn(item);

        shoppingCart.addItem(name, quantity);
    }

    private void addItemToShoppingCart(int id, String name,  double price) throws Exception{
        Item item = new Item(id);
        item.setPrice(price);
        item.setName(name);

        // questionable if I should do stubbing in helper method, but it makes
        // the tests more concise and easier to read
        when(mockedInventory.getItemByName(name)).thenReturn(item);

        shoppingCart.addItem(name);
    }


    @Test
    public void getItemTotal_whenAddingSingleItem_totalPriceEqualsItemPrice() throws Exception{

        double price = 10.00;

        this.addItemToShoppingCart(1, "KitKat", price);


        assertEquals(price, shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoOfSameItems_totalPriceIsDoubleItemPrice() throws Exception{
        double price = 10.00;

        this.addItemToShoppingCart(1, "Bottle Caps", price);
        this.addItemToShoppingCart(1, "Bottle Caps", price);


        BigDecimal twiceThePriceValue = new BigDecimal(price).multiply(new BigDecimal(2));
        assertEquals(twiceThePriceValue.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoDifferentItems_totalPriceIsSumOfItemPrices() throws Exception{

        double price1 = 10.00;
        this.addItemToShoppingCart(1, "PayDay", price1);

        double price2 = 10.00;
        this.addItemToShoppingCart(1, "Zero", price2);


        double sumOfPrices = price1 + price2;
        assertEquals(sumOfPrices, shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingItemAndWeight_totalPriceReflectsItemWithWeight() throws Exception{

        addItemToShoppingCartWithWeight(1, "Skittles",  3.00, 3.0);


        assertEquals(9.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingMultipleItemsWithWeight_totalPriceReflectsItemsWithWeight() throws Exception{

        addItemToShoppingCartWithWeight(1, "Chicken Chicken", 3.00, 3.0);

        addItemToShoppingCartWithWeight(2, "Courageous Chicken", 2.00, 1.5);

        assertEquals(12.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingItemsWithAndWithoutWeight_totalPriceReflectsAllItems() throws Exception{

        addItemToShoppingCartWithWeight(1, "Brave Chicken",3.00, 2.5);

        addItemToShoppingCart(2, "Pirate Puffy Pants Pants",2.00);

        addItemToShoppingCart(3, "Cheesey Puffs",  1.25);

        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
    }

    @Test
    public void removeItem_removingItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice() throws Exception{
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
    public void removeItem_removingWeightedItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice() throws Exception{

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
