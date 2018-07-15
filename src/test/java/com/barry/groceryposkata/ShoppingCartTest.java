package com.barry.groceryposkata;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {


    @Test
    public void addingScannedItemIncreasesPrice(){

        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(item);

        assertEquals(10.00, shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void addingTwoOfAnItemDoublesItemTotal(){
        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(item);
        shoppingCart.addItem(item);

        assertEquals(20.00, shoppingCart.getItemTotal(), 0.001);
    }


}
