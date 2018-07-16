package com.barry.groceryposkata;

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

    @Test
    public void getItemTotal_whenAddingSingleItem_totalPriceEqualsItemPrice(){

        Item item = new Item();
        item.setPrice(10.00);

        shoppingCart.addItem(item);

        assertEquals(item.getPrice().doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoOfSameItems_totalPriceIsDoubleItemPrice(){
        Item item = new Item();
        item.setPrice(10.00);

        shoppingCart.addItem(item);
        shoppingCart.addItem(item);

        BigDecimal twiceThePriceValue = item.getPrice().multiply(new BigDecimal(2));
        assertEquals(twiceThePriceValue.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingTwoDifferentItems_totalPriceIsSumOfItemPrices(){
        Item item1 = new Item();
        item1.setPrice(10.00);
        Item item2 = new Item();
        item2.setPrice(5.50);

        shoppingCart.addItem(item1);
        shoppingCart.addItem(item2);

        BigDecimal sumOfPrices = item1.getPrice().add(item2.getPrice());
        assertEquals(sumOfPrices.doubleValue(), shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getItemTotal_addingItemAndWeight_totalPriceReflectsItemWithWeight(){

        Item item = new Item();
        item.setPrice(3.00);
        double weight = 3;
        shoppingCart.addItem(item, weight);

        assertEquals(9.0, shoppingCart.getItemTotal(), .001);

    }

}
