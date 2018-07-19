package com.barry.groceryposkata.controller;

import com.barry.groceryposkata.service.Inventory;
import com.barry.groceryposkata.service.ShoppingCart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private Inventory inventory;

    @Before
    public void clearInventory(){
        // clear inventory
        inventory.setItemMap(new HashMap<>());
    }

    @Before
    public void clearShoppingCart(){
        // clear inventory
        shoppingCart.setItemList(new ArrayList<>());
    }

    @Test
    public void addItem_whenAddingItemThatExists_returnsOKstatus() throws Exception{

        String itemName = "KitKat";
        inventory.addItem(itemName,10.33);

        String jsonRequest = String.format("{\"itemName\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
				.andExpect(status().isOk());

    }


    @Test
    public void addItem_whenAddingItemThatDoesNOTExists_returnsNotFoundStatus() throws Exception{

        String itemName = "AntiMatter";

        String jsonRequest = String.format("{\"name\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isNotFound());

    }

    @Test
    public void addItem_whenAddingItem_returnsItemInformation() throws Exception{

        String itemName = "KitKat";
        inventory.addItem(itemName,10.33);

        String jsonRequest = String.format("{\"itemName\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(10.33)))
                .andExpect(jsonPath("$.name", is(itemName)));

    }

    @Test
    public void addItem_whenAddingItemWithWeight_cartTotalReflectsWeightTimesPrice() throws Exception{

        // add item to inventory
        String itemName = "Truffles";
        inventory.addItem(itemName,1.00);

        double weight = 1.5;

        String jsonRequest = String.format("{\"itemName\":\"%s\", \"weight\":%s}", itemName, weight);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        // verify shopping cart total is appropriate
        assertEquals(1.50, shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void addItem_whenAddingItemWithNoWeight_cartTotalReflectsItemPrice() throws Exception{

        // add item to inventory
        String itemName = "Gobstoppers";
        inventory.addItem(itemName,2.50);

        String jsonRequest = String.format("{\"itemName\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        // verify shopping cart total is appropriate
        assertEquals(2.50, shoppingCart.getItemTotal(), 0.001);
    }


    @Test
    public void addItem_whenAdding2ofSameItem_cartTotalReflectsDoubleItemPrice() throws Exception{

        // add item to inventory
        String itemName = "Gobstoppers";
        inventory.addItem(itemName,2.50);

        String jsonRequest = String.format("{\"itemName\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());


        // verify shopping cart total is appropriate
        assertEquals(5.00, shoppingCart.getItemTotal(), 0.001);
    }

    @Test
    public void getTotal_whenNothingInShoppingCart_returnsZeroTotal() throws Exception{

        // nothing added to cart...

        mockMvc.perform(get("/shoppingcart/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(0.00)));


    }

    @Test
    public void getTotal_addingOneItemCartAndCallingGetTotal_returnsPriceOfThatItem() throws Exception{

        String itemName = "KitKat";
        inventory.addItem(itemName,10.33);
        // add our item directly to the shopping cart
        shoppingCart.addItem(itemName);

        mockMvc.perform(get("/shoppingcart/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(10.33)));


    }

    @Test
    public void getTotal_addingTwoItemsToCartAndCallingGetTotal_returnsSumOfItems() throws Exception{

        String itemName1 = "KitKat";
        double price1 = 10.33;
        inventory.addItem(itemName1,price1);

        String itemName2 = "Watchmacallit";
        double price2 = 2.55;
        inventory.addItem(itemName2,2.55);

        // add our item directly to the shopping cart
        shoppingCart.addItem(itemName1);
        shoppingCart.addItem(itemName2);

        // expectedPrice is sum of two prices.
        BigDecimal expectedPrice = new BigDecimal(price1 + price2).setScale(2, RoundingMode.HALF_UP);

        mockMvc.perform(get("/shoppingcart/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(expectedPrice.doubleValue())));


    }



    @Test
    public void removeItem_removingItemFromCart_reducesTotalByItemPrice() throws Exception{

        String itemName1 = "KitKat";
        double price1 = 10.33;
        inventory.addItem(itemName1,price1);

        String itemName2 = "Watchmacallit";
        double price2 = 2.55;
        inventory.addItem(itemName2,2.55);

        // add our item directly to the shopping cart
        shoppingCart.addItem(itemName1);
        shoppingCart.addItem(itemName2);

        // expectedPrice is sum of two prices.
        BigDecimal expectedPrice = new BigDecimal(price1 + price2).setScale(2, RoundingMode.HALF_UP);

        mockMvc.perform(delete("/shoppingcart/items/KitKat"))
                .andExpect(status().isOk());

        assertEquals(2.55, shoppingCart.getItemTotal(), 0.001);


    }

}
