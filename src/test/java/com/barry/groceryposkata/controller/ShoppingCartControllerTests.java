package com.barry.groceryposkata.controller;

import com.barry.groceryposkata.service.Inventory;
import com.barry.groceryposkata.service.ShoppingCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Test
    public void addItem_whenAddingItemThatExists_returnsOKstatus() throws Exception{

        String itemName = "KitKat";
        inventory.addItem(itemName,10.33);

        String jsonRequest = String.format("{\"name\":\"%s\"}", itemName);

        mockMvc.perform(post("/shoppingcart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
				.andExpect(status().isOk());

    }
}
