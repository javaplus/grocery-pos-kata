package com.barry.groceryposkata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
// @AutoConfigureMockMvc
public class InventoryControllerTests {

	@InjectMocks
	private InventoryController inventoryController;


	private MockMvc mockMvc;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
	}

	public void addItem_whenAddingNewItem_returnsItemId() throws Exception {
		mockMvc.perform(post("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content("{\"price\":\"2.50\"}"))
				.andExpect(status().isOk());

		//		.andExpect(content()
		//				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		//		.andExpect(jsonPath("id", is("1")));
	}


	@Test
	public void getItems_whenCallingGetItems_returnsAllInventoryItemsAdded() throws Exception {
		mockMvc.perform(get("/inventory/items")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		//		.andExpect(content()
		//				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		//		.andExpect(jsonPath("id", is("1")));
	}

}
