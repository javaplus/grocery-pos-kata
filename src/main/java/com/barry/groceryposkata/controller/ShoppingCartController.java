package com.barry.groceryposkata.controller;


import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.entities.ShoppingCartAddRequest;
import com.barry.groceryposkata.service.Inventory;
import com.barry.groceryposkata.service.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private Inventory inventory;


    @RequestMapping(value = "/items", method = RequestMethod.POST)
    @ResponseBody
    public Item addItem(@RequestBody ShoppingCartAddRequest request) throws Exception{

        double weight = (request.getWeight() > 0.00)? request.getWeight(): 1.00;

        return shoppingCart.addItem(request.getItemName(), weight);

    }

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    //@ResponseBody
    public String getTotal() throws Exception{

        double total = shoppingCart.getItemTotal();
        String response = String.format("{\"total\":%s}",total);
        return response;

    }


    @RequestMapping(value = "/items/{itemName}", method = RequestMethod.DELETE)
    public void removeItem(@PathVariable String itemName) throws Exception{

        int itemID = inventory.getItemByName(itemName).getID();

        shoppingCart.removeOrderedItem(itemID);

    }

}
