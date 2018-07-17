package com.barry.groceryposkata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private Inventory inventory;

    @RequestMapping(value = "items", method = POST, produces= "application/json;charset=UTF-8")
    @ResponseBody
    public Item addItem(@RequestBody Item item) throws Exception{
        item.setID(1);
        System.out.println("Item price:" + item.getPrice());
        item.setID(inventory.addItem("", item.getPrice().doubleValue()));
        return item;

    }

    @RequestMapping(value = "items", method = GET, produces= "application/json")
    @ResponseBody
    public List<Item> getItems() throws Exception{


        return inventory.getItemList();

    }



}
