package com.barry.groceryposkata;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {


    @Test
    public void getItemTotal_whenAddingSingleItem_totalPriceEqualsItemPrice(){

        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(item);

        assertEquals(item.getPrice().doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoOfSameItems_totalPriceIsDoubleItemPrice(){
        Item item = new Item();
        item.setPrice(new BigDecimal(10));

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(item);
        shoppingCart.addItem(item);

        BigDecimal twiceThePriceValue = item.getPrice().multiply(new BigDecimal(2));
        assertEquals(twiceThePriceValue.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }


}
