package com.barry.groceryposkata;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @RequestMapping(value = "items", method = POST, produces= "application/json;charset=UTF-8")
    @ResponseBody
    public String addItem(@RequestBody Item item) throws Exception{

        System.out.println("Item price:" + item.getPrice());
        return "{\"id\":\"1\"}";

    }

    @RequestMapping(value = "items", method = GET, produces= "application/json")
    @ResponseBody
    public List<Item> addItem() throws Exception{

        Item item = new Item();
        item.setPrice(3.00);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        return itemList;

    }



}
