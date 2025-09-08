package edu.trincoll.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.trincoll.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the complete application stack.
 * Tests controller → service → repository integration.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ItemIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() throws Exception {
        // Clear all items before each test
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    Item[] items = objectMapper.readValue(content, Item[].class);
                    for (Item item : items) {
                        mockMvc.perform(delete("/api/items/" + item.getId()));
                    }
                });
    }
    
    @Test
    @DisplayName("Should create item via REST API")
    void testCreateItem() throws Exception {
        Item item = new Item("Test Item", "Test Description");
        item.setCategory("Test");
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Test Item"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.category").value("Test"));
    }
    
    @Test
    @DisplayName("Should reject invalid item creation")
    void testCreateInvalidItem() throws Exception {
        Item item = new Item("", "Description"); // Invalid: empty title
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Should get all items")
    void testGetAllItems() throws Exception {
        // Create test items
        Item item1 = new Item("Item 1", "Desc 1");
        Item item2 = new Item("Item 2", "Desc 2");
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item1)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item2)))
                .andExpect(status().isCreated());
        
        // Get all items
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("Item 1", "Item 2")));
    }
    
    @Test
    @DisplayName("Should get item by ID")
    void testGetItemById() throws Exception {
        // Create item
        Item item = new Item("Test Item", "Test Description");
        
        String response = mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Item created = objectMapper.readValue(response, Item.class);
        
        // Get by ID
        mockMvc.perform(get("/api/items/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.title").value("Test Item"));
    }
    
    @Test
    @DisplayName("Should return 404 for non-existent item")
    void testGetNonExistentItem() throws Exception {
        mockMvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Should update item")
    void testUpdateItem() throws Exception {
        // Create item
        Item item = new Item("Original Title", "Original Description");
        
        String response = mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Item created = objectMapper.readValue(response, Item.class);
        
        // Update item
        created.setTitle("Updated Title");
        created.setDescription("Updated Description");
        
        mockMvc.perform(put("/api/items/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }
    
    @Test
    @DisplayName("Should delete item")
    void testDeleteItem() throws Exception {
        // Create item
        Item item = new Item("To Delete", "Will be deleted");
        
        String response = mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Item created = objectMapper.readValue(response, Item.class);
        
        // Delete item
        mockMvc.perform(delete("/api/items/" + created.getId()))
                .andExpect(status().isNoContent());
        
        // Verify deleted
        mockMvc.perform(get("/api/items/" + created.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("Should get items by status")
    void testGetItemsByStatus() throws Exception {
        // Create items with different statuses
        Item active = new Item("Active Item", "Active");
        active.setStatus(Item.Status.ACTIVE);
        
        Item inactive = new Item("Inactive Item", "Inactive");
        inactive.setStatus(Item.Status.INACTIVE);
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(active)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inactive)))
                .andExpect(status().isCreated());
        
        // Get active items
        mockMvc.perform(get("/api/items/status/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Active Item"));
    }
    
    @Test
    @DisplayName("Should get items grouped by category")
    void testGetItemsGroupedByCategory() throws Exception {
        // Create items in different categories
        Item work1 = new Item("Work 1", "Work item");
        work1.setCategory("Work");
        
        Item work2 = new Item("Work 2", "Another work item");
        work2.setCategory("Work");
        
        Item personal = new Item("Personal", "Personal item");
        personal.setCategory("Personal");
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(work1)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(work2)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personal)))
                .andExpect(status().isCreated());
        
        // Get grouped items (will fail until implemented)
        mockMvc.perform(get("/api/items/grouped"))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Should search items")
    void testSearchItems() throws Exception {
        // Create searchable items
        Item item1 = new Item("Java Programming", "Learn Java");
        Item item2 = new Item("Python Guide", "Learn Python");
        Item item3 = new Item("JavaScript Tutorial", "Learn JS");
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item1)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item2)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item3)))
                .andExpect(status().isCreated());
        
        // Search (will return empty until implemented)
        mockMvc.perform(get("/api/items/search")
                        .param("query", "Java"))
                .andExpect(status().isOk());
    }
}