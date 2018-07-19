package com.barry.groceryposkata.controller;


import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.service.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCart shoppingCart;


    @RequestMapping(value = "/items", method = RequestMethod.POST)
    @ResponseBody
    public Item addItem(@RequestBody Item item) throws Exception{

        return shoppingCart.addItem(item.getName());

    }

}
