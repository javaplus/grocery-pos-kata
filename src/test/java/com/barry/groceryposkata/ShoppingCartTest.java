package com.barry.groceryposkata;

import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.service.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @Before
    public void initializeShoppingCart(){
        shoppingCart = new ShoppingCart();
    }

    private void addItemToShoppingCartWithWeight(int id, double price, double quantity){
        Item item = new Item(id);
        item.setPrice(price);
        shoppingCart.addItem(item, quantity);
    }

    private void addItemToShoppingCart(int id, double price){
        Item item = new Item(id);
        item.setPrice(price);
        shoppingCart.addItem(item);
    }


    @Test
    public void getItemTotal_whenAddingSingleItem_totalPriceEqualsItemPrice(){

        Item item = new Item(1);
        item.setPrice(10.00);

        shoppingCart.addItem(item);


        assertEquals(item.getPrice().doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoOfSameItems_totalPriceIsDoubleItemPrice(){
        Item item = new Item(1);
        item.setPrice(10.00);

        shoppingCart.addItem(item);
        shoppingCart.addItem(item);

        BigDecimal twiceThePriceValue = item.getPrice().multiply(new BigDecimal(2));
        assertEquals(twiceThePriceValue.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoDifferentItems_totalPriceIsSumOfItemPrices(){
        Item item1 = new Item(1);
        item1.setPrice(10.00);
        Item item2 = new Item(2);
        item2.setPrice(5.50);

        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);

        BigDecimal sumOfPrices = item1.getPrice().add(item2.getPrice());
        assertEquals(sumOfPrices.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingItemAndWeight_totalPriceReflectsItemWithWeight(){

        addItemToShoppingCartWithWeight(1, 3.00, 3.0);


        assertEquals(9.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingMultipleItemsWithWeight_totalPriceReflectsItemsWithWeight(){

        addItemToShoppingCartWithWeight(1, 3.00, 3.0);

        addItemToShoppingCartWithWeight(2, 2.00, 1.5);

        assertEquals(12.0, shoppingCart.getItemTotal(), .001);

    }

    @Test
    public void getItemTotal_addingItemsWithAndWithoutWeight_totalPriceReflectsAllItems(){

        addItemToShoppingCartWithWeight(1, 3.00, 2.5);

        addItemToShoppingCart(2, 2.00);

        addItemToShoppingCart(3, 1.25);

        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
    }

    @Test
    public void removeItem_removingItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice(){
        addItemToShoppingCartWithWeight(1, 3.00, 2.5);

        addItemToShoppingCart(2, 2.00);


        int itemId = 3;

        addItemToShoppingCart(itemId, 1.25);


        // assert value before removal
        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
        shoppingCart.removeOrderedItem(itemId);
        // assert value after removal
        assertEquals(9.50, shoppingCart.getItemTotal(), .001);

    }


    @Test
    public void removeItem_removingWeightedItemFromShoppingCart_reducesTotalPriceByRemovedOrderedItemPrice(){

        int itemId = 1;
        addItemToShoppingCartWithWeight(itemId, 3.00, 2.5);

        addItemToShoppingCart(2, 2.00);

        addItemToShoppingCart(3, 1.25);

        // assert value before removal
        assertEquals(10.75, shoppingCart.getItemTotal(), .001);
        shoppingCart.removeOrderedItem(itemId);
        // assert value after removal
        assertEquals(3.25, shoppingCart.getItemTotal(), .001);

    }

}
