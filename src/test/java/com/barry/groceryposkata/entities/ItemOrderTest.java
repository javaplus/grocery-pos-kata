package com.barry.groceryposkata.entities;

import org.junit.Test;
import static org.junit.Assert.*;
public class ItemOrderTest {

    @Test
    public void getPrice_whenItemOrderCreatedWithNoWeight_returnsItemPrice(){

        Item item = new Item(22);
        item.setPrice(89.33);
        item.setName("FooBars");

        ItemOrder itemOrder = new ItemOrder(item);

        assertEquals(89.33, itemOrder.getPrice().doubleValue(), 0.001);

    }


    @Test
    public void getPrice_whenItemOrderCreatedWithWeight_returnsItemPriceTimesWeight(){

        Item item = new Item(22);
        item.setPrice(10.50);
        item.setName("FooBars");

        ItemOrder itemOrder = new ItemOrder(item, 2.00);

        assertEquals(21.00, itemOrder.getPrice().doubleValue(), 0.001);

    }

}
