package com.barry.groceryposkata.controller;


import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.service.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCart shoppingCart;


    @RequestMapping("/items")
    public void addItem(Item item){

        shoppingCart.addItem(item.getName());

    }

}
