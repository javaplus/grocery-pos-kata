package com.barry.groceryposkata;

import com.barry.groceryposkata.entities.Item;
import com.barry.groceryposkata.service.Inventory;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Inventory inventory;

	@Before
	public void clearInventory(){
		// clear inventory
		inventory.setItemMap(new HashMap<>());
	}

	@Test
	public void addItem_whenAddingNewItem_increasesInventoryByOne() throws Exception {


		int initialInventorySize = inventory.getCount();

		String itemJSON = this.buildItemJSON("Doritos", 4.59);
		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON))
				.andExpect(status().isOk());

		assertEquals(initialInventorySize +1, inventory.getCount());

	}

	@Test
	public void addItem_whenAddingTwoNewItem_increasesInventoryByTwo() throws Exception {

		int initialInventorySize = inventory.getCount();

		String itemJSON1 = this.buildItemJSON("Doritos", 4.59);
		String itemJSON2 = this.buildItemJSON("Tostitos", 3.59);

		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON1))
				.andExpect(status().isOk());
		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON2))
				.andExpect(status().isOk());

		assertEquals(initialInventorySize +2, inventory.getCount());

	}

	@Test
	public void addItem_afterCallingAddItem_thatItemCanBeFoundInInventory() throws Exception {

		String itemJson = buildItemJSON("twinkies", 2.50);

		String responseBody = mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		int id = JsonPath.read(responseBody, "$.id");

		Item item = inventory.getItemMap().values().stream().filter(p->p.getID()==id).findFirst().get();
		assertEquals(2.50, item.getPrice().doubleValue(), 0.001);
		assertEquals("Item name matches what was passed to controller","twinkies", item.getName());

	}


	@Test
	public void getItems_whenCallingGetItems_returnsNewlyAddedItem() throws Exception {

		String itemName = "TestTwinkies";
		int id = inventory.addItem(itemName, 3.55);

		mockMvc.perform(get("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[*].id", hasItem(id)))
				.andExpect(jsonPath("$[*].name", hasItem(itemName)));

	}

	@Test
	public void updateItem_whenUpdatingItemPrice_updatedItemPriceReflectedInInventory() throws Exception {

		// add item to inventory to be updated via controller
		String itemName = "TestTwinkies";
		int id = inventory.addItem(itemName, 3.55);

		double newPrice = 2.45;
		String itemJSON = String.format("{\"id\":%s, \"name\":\"%s\",\"price\":%s}", id, itemName, newPrice);

		mockMvc.perform(put("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON))
				.andExpect(status().isOk());

		//get item from inventory
		Item item = inventory.getItemByName(itemName);

		assertEquals(newPrice, item.getPrice().doubleValue(), 0.001);


	}

	@Test
	public void updateItem_whenUpdatingItemThatDoesntExist_returns404() throws Exception {

		// add item to inventory to be updated via controller
		String itemName = "You Dont Got None Of This to Update";
		double price = 2.45;
		int madeUpId = 3434;
		String itemJSON = String.format("{\"id\":%s, \"name\":\"%s\",\"price\":%s}", madeUpId, itemName, price);

		mockMvc.perform(put("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void addItem_whenAddingItemThatAlreadyExist_returns409() throws Exception {

		// add item to inventory to make sure it exists
		String itemName = "TestTwinkies";

		inventory.addItem(itemName, 2.45);

		String itemJSON = this.buildItemJSON(itemName, 3.12);

		// try to add this item that via the controller
		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content(itemJSON))
				.andExpect(status().isConflict());

	}



	private String buildItemJSON(String name, double price){
		return String.format("{\"name\":\"%s\",\"price\":%s}", name, price);
	}

}
