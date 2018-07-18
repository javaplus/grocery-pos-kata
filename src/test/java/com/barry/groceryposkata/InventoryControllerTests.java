package com.barry.groceryposkata;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class InventoryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Inventory inventory;

	@Test
	public void addItem_whenAddingNewItem_increasesInventoryByOne() throws Exception {

		int initialInventorySize = inventory.getCount();

		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"price\":\"2.50\"}"))
				.andExpect(status().isOk());

		assertEquals(initialInventorySize +1, inventory.getCount());

	}

	@Test
	public void addItem_whenAddingNewItem_thatItemCanBeFoundInInventory() throws Exception {

		String responseBody = mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"price\":\"2.50\"}"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		int id = JsonPath.read(responseBody, "$.id");

		Item item = inventory.getItemList().stream().filter(p->p.getID()==id).findFirst().get();
		assertEquals(2.50, item.getPrice().doubleValue(), 0.001);

	}


	@Test
	public void getItems_whenCallingGetItems_returnsNewlyAddedItem() throws Exception {

		int id = inventory.addItem("TestTwinkies", 3.55);

		mockMvc.perform(get("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[*].id", hasItem(id)));

	}

}
