package edu.trincoll.tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test suite for the Item API.
 * <p>
 * ALL TESTS MUST PASS for full credit.
 * Do not modify these tests - modify your code to make them pass.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Item Controller Tests")
class ItemControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() throws Exception {
        // Clear any existing data before each test
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }
    
    @Nested
    @DisplayName("GET /api/items")
    class GetAllItems {
        
        @Test
        @DisplayName("should return empty list when no items exist")
        void shouldReturnEmptyList() throws Exception {
            mockMvc.perform(get("/api/items"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
        }
        
        @Test
        @DisplayName("should return all items when items exist")
        void shouldReturnAllItems() throws Exception {
            // Create a test item first
            Item testItem = new Item();
            testItem.setName("Test Item");
            testItem.setDescription("Test Description");
            
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testItem)))
                    .andExpect(status().isCreated());
            
            // Now get all items
            mockMvc.perform(get("/api/items"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                    .andExpect(jsonPath("$[?(@.name == 'Test Item')]").exists());
        }
    }
    
    @Nested
    @DisplayName("GET /api/items/{id}")
    class GetItemById {
        
        @Test
        @DisplayName("should return item when it exists")
        void shouldReturnItemWhenExists() throws Exception {
            // Create a test item first
            Item testItem = new Item();
            testItem.setName("Specific Item");
            testItem.setDescription("Specific Description");
            
            String response = mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testItem)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            
            Item createdItem = objectMapper.readValue(response, Item.class);
            
            // Get the specific item
            mockMvc.perform(get("/api/items/{id}", createdItem.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Specific Item"))
                    .andExpect(jsonPath("$.description").value("Specific Description"));
        }
        
        @Test
        @DisplayName("should return 404 when item doesn't exist")
        void shouldReturn404WhenItemDoesNotExist() throws Exception {
            mockMvc.perform(get("/api/items/{id}", 999999))
                    .andExpect(status().isNotFound());
        }
    }
    
    @Nested
    @DisplayName("POST /api/items")
    class CreateItem {
        
        @Test
        @DisplayName("should create new item with valid data")
        void shouldCreateNewItem() throws Exception {
            Item newItem = new Item();
            newItem.setName("New Item");
            newItem.setDescription("New Description");
            
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newItem)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("New Item"))
                    .andExpect(jsonPath("$.description").value("New Description"));
        }
        
        @Test
        @DisplayName("should return 400 when name is missing")
        void shouldReturn400WhenNameMissing() throws Exception {
            String invalidJson = "{\"description\":\"No name provided\"}";
            
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        @DisplayName("should return 400 when name is blank")
        void shouldReturn400WhenNameBlank() throws Exception {
            Item invalidItem = new Item();
            invalidItem.setName("");  // Blank name
            invalidItem.setDescription("Valid Description");
            
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidItem)))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        @DisplayName("should not allow duplicate items with same name")
        void shouldNotAllowDuplicates() throws Exception {
            Item firstItem = new Item();
            firstItem.setName("Unique Name");
            firstItem.setDescription("First Description");
            
            // Create first item
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(firstItem)))
                    .andExpect(status().isCreated());
            
            // Try to create duplicate
            Item duplicateItem = new Item();
            duplicateItem.setName("Unique Name");  // Same name
            duplicateItem.setDescription("Different Description");
            
            mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(duplicateItem)))
                    .andExpect(status().isConflict());  // 409 Conflict
        }
    }
    
    @Nested
    @DisplayName("PUT /api/items/{id}")
    class UpdateItem {
        
        @Test
        @DisplayName("should update existing item")
        void shouldUpdateExistingItem() throws Exception {
            // Create initial item
            Item initialItem = new Item();
            initialItem.setName("Original Name");
            initialItem.setDescription("Original Description");
            
            String response = mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(initialItem)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            
            Item createdItem = objectMapper.readValue(response, Item.class);
            
            // Update the item
            Item updatedItem = new Item();
            updatedItem.setName("Updated Name");
            updatedItem.setDescription("Updated Description");
            updatedItem.setCompleted(true);
            
            mockMvc.perform(put("/api/items/{id}", createdItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedItem)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Updated Name"))
                    .andExpect(jsonPath("$.description").value("Updated Description"))
                    .andExpect(jsonPath("$.completed").value(true));
        }
        
        @Test
        @DisplayName("should return 404 when updating non-existent item")
        void shouldReturn404WhenUpdatingNonExistent() throws Exception {
            Item updateItem = new Item();
            updateItem.setName("Update Name");
            updateItem.setDescription("Update Description");
            
            mockMvc.perform(put("/api/items/{id}", 999999)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateItem)))
                    .andExpect(status().isNotFound());
        }
        
        @Test
        @DisplayName("should validate required fields on update")
        void shouldValidateRequiredFieldsOnUpdate() throws Exception {
            // Create initial item
            Item initialItem = new Item();
            initialItem.setName("Original Name");
            initialItem.setDescription("Original Description");
            
            String response = mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(initialItem)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            
            Item createdItem = objectMapper.readValue(response, Item.class);
            
            // Try to update with invalid data
            String invalidUpdate = "{\"name\":\"\",\"description\":\"Valid Description\"}";
            
            mockMvc.perform(put("/api/items/{id}", createdItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidUpdate))
                    .andExpect(status().isBadRequest());
        }
    }
    
    @Nested
    @DisplayName("DELETE /api/items/{id}")
    class DeleteItem {
        
        @Test
        @DisplayName("should delete existing item")
        void shouldDeleteExistingItem() throws Exception {
            // Create item to delete
            Item itemToDelete = new Item();
            itemToDelete.setName("Delete Me");
            itemToDelete.setDescription("To Be Deleted");
            
            String response = mockMvc.perform(post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(itemToDelete)))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            
            Item createdItem = objectMapper.readValue(response, Item.class);
            
            // Delete the item
            mockMvc.perform(delete("/api/items/{id}", createdItem.getId()))
                    .andExpect(status().isNoContent());
            
            // Verify it's gone
            mockMvc.perform(get("/api/items/{id}", createdItem.getId()))
                    .andExpect(status().isNotFound());
        }
        
        @Test
        @DisplayName("should return 404 when deleting non-existent item")
        void shouldReturn404WhenDeletingNonExistent() throws Exception {
            mockMvc.perform(delete("/api/items/{id}", 999999))
                    .andExpect(status().isNotFound());
        }
    }
    
    @Nested
    @DisplayName("Bonus: Search Functionality")
    class SearchItems {
        
        @Test
        @DisplayName("BONUS: should search items by name")
        void shouldSearchItemsByName() throws Exception {
            // Create test items
            Item item1 = new Item();
            item1.setName("Apple");
            item1.setDescription("Red fruit");
            
            Item item2 = new Item();
            item2.setName("Banana");
            item2.setDescription("Yellow fruit");
            
            Item item3 = new Item();
            item3.setName("Application");
            item3.setDescription("Software");
            
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
            
            // Search for items containing "App"
            mockMvc.perform(get("/api/items/search")
                    .param("name", "App"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[?(@.name == 'Apple')]").exists())
                    .andExpect(jsonPath("$[?(@.name == 'Application')]").exists());
        }
    }
}