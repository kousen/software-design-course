package edu.trincoll.service;

import edu.trincoll.model.Item;
import edu.trincoll.repository.InMemoryItemRepository;
import edu.trincoll.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for the service layer.
 * Tests both inherited BaseService functionality and ItemService-specific methods.
 */
class ItemServiceTest {
    
    private ItemService service;
    private ItemRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        service = new ItemService(repository);
        repository.deleteAll();
    }
    
    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        
        @Test
        @DisplayName("Should reject null item")
        void testValidateNullItem() {
            assertThatThrownBy(() -> service.validateEntity(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cannot be null");
        }
        
        @Test
        @DisplayName("Should reject item without title")
        void testValidateNoTitle() {
            Item item = new Item();
            item.setDescription("Description");
            
            assertThatThrownBy(() -> service.validateEntity(item))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Title is required");
        }
        
        @Test
        @DisplayName("Should reject item with empty title")
        void testValidateEmptyTitle() {
            Item item = new Item("   ", "Description");
            
            assertThatThrownBy(() -> service.validateEntity(item))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Title is required");
        }
        
        @Test
        @DisplayName("Should reject item with title too long")
        void testValidateTitleTooLong() {
            String longTitle = "a".repeat(101);
            Item item = new Item(longTitle, "Description");
            
            assertThatThrownBy(() -> service.validateEntity(item))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cannot exceed 100 characters");
        }
        
        @Test
        @DisplayName("Should accept valid item")
        void testValidateValidItem() {
            Item item = new Item("Valid Title", "Valid Description");
            item.setCategory("Test");
            
            assertThatNoException().isThrownBy(() -> service.validateEntity(item));
        }
    }
    
    @Nested
    @DisplayName("CRUD Operations Tests")
    class CrudTests {
        
        @Test
        @DisplayName("Should save item with validation")
        void testSave() {
            Item item = new Item("Test Item", "Description");
            
            Item saved = service.save(item);
            
            assertThat(saved.getId()).isNotNull();
            assertThat(service.count()).isEqualTo(1);
        }
        
        @Test
        @DisplayName("Should not save invalid item")
        void testSaveInvalid() {
            Item item = new Item("", "Description");
            
            assertThatThrownBy(() -> service.save(item))
                    .isInstanceOf(IllegalArgumentException.class);
            
            assertThat(service.count()).isZero();
        }
        
        @Test
        @DisplayName("Should find item by ID")
        void testFindById() {
            Item item = service.save(new Item("Test", "Desc"));
            
            assertThat(service.findById(item.getId())).isPresent();
            assertThat(service.findById(999L)).isEmpty();
        }
        
        @Test
        @DisplayName("Should delete item by ID")
        void testDeleteById() {
            Item item = service.save(new Item("To Delete", "Desc"));
            Long id = item.getId();
            
            service.deleteById(id);
            
            assertThat(service.findById(id)).isEmpty();
        }
        
        @Test
        @DisplayName("Should throw exception when deleting non-existent item")
        void testDeleteNonExistent() {
            assertThatThrownBy(() -> service.deleteById(999L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("not found");
        }
    }
    
    @Nested
    @DisplayName("Collection Operations Tests")
    class CollectionTests {
        
        @BeforeEach
        void setUpTestData() {
            Item item1 = new Item("Work Task 1", "Important work");
            item1.setCategory("Work");
            item1.setStatus(Item.Status.ACTIVE);
            item1.addTag("urgent");
            item1.addTag("project-a");
            
            Item item2 = new Item("Personal Task", "Personal stuff");
            item2.setCategory("Personal");
            item2.setStatus(Item.Status.ACTIVE);
            item2.addTag("home");
            
            Item item3 = new Item("Work Task 2", "More work");
            item3.setCategory("Work");
            item3.setStatus(Item.Status.INACTIVE);
            item3.addTag("project-b");
            
            Item item4 = new Item("Archived Task", "Old task");
            item4.setCategory("Work");
            item4.setStatus(Item.Status.ARCHIVED);
            item4.addTag("urgent");
            
            service.save(item1);
            service.save(item2);
            service.save(item3);
            service.save(item4);
        }
        
        @Test
        @DisplayName("Should group items by category")
        void testGroupByCategory() {
            Map<String, List<Item>> grouped = service.groupByCategory();
            
            // This test will fail until students implement the method
            assertThat(grouped).hasSize(2);
            assertThat(grouped.get("Work")).hasSize(3);
            assertThat(grouped.get("Personal")).hasSize(1);
        }
        
        @Test
        @DisplayName("Should get all unique tags")
        void testGetAllUniqueTags() {
            Set<String> tags = service.getAllUniqueTags();
            
            // This test will fail until students implement the method
            assertThat(tags).hasSize(4);
            assertThat(tags).containsExactlyInAnyOrder(
                    "urgent", "project-a", "home", "project-b"
            );
        }
        
        @Test
        @DisplayName("Should count items by status")
        void testCountByStatus() {
            Map<Item.Status, Long> counts = service.countByStatus();
            
            // This test will fail until students implement the method
            assertThat(counts).hasSize(3);
            assertThat(counts.get(Item.Status.ACTIVE)).isEqualTo(2);
            assertThat(counts.get(Item.Status.INACTIVE)).isEqualTo(1);
            assertThat(counts.get(Item.Status.ARCHIVED)).isEqualTo(1);
        }
        
        @Test
        @DisplayName("Should find items with all specified tags")
        void testFindByAllTags() {
            Item item5 = new Item("Multi-tag Task", "Has multiple tags");
            item5.addTag("urgent");
            item5.addTag("project-a");
            service.save(item5);
            
            List<Item> results = service.findByAllTags(Set.of("urgent", "project-a"));
            
            // This test will fail until students implement the method
            assertThat(results).hasSize(2);
            assertThat(results).extracting(Item::getTitle)
                    .containsExactlyInAnyOrder("Work Task 1", "Multi-tag Task");
        }
        
        @Test
        @DisplayName("Should find items with any of specified tags")
        void testFindByAnyTag() {
            List<Item> results = service.findByAnyTag(Set.of("home", "project-b"));
            
            // This test will fail until students implement the method
            assertThat(results).hasSize(2);
            assertThat(results).extracting(Item::getTitle)
                    .containsExactlyInAnyOrder("Personal Task", "Work Task 2");
        }
        
        @Test
        @DisplayName("Should get most popular tags")
        void testGetMostPopularTags() {
            List<String> popular = service.getMostPopularTags(2);
            
            // This test will fail until students implement the method
            assertThat(popular).hasSize(2);
            assertThat(popular.get(0)).isEqualTo("urgent"); // appears twice
        }
        
        @Test
        @DisplayName("Should search items by query")
        void testSearch() {
            List<Item> results = service.search("work");
            
            // This test will fail until students implement the method
            assertThat(results).hasSize(3);
            assertThat(results).extracting(Item::getTitle)
                    .contains("Work Task 1", "Work Task 2");
        }
        
        @Test
        @DisplayName("Should archive inactive items")
        void testArchiveInactiveItems() {
            int archived = service.archiveInactiveItems();
            
            // This test will fail until students implement the method
            assertThat(archived).isEqualTo(1);
            
            List<Item> inactiveItems = service.findByStatus(Item.Status.INACTIVE);
            assertThat(inactiveItems).isEmpty();
            
            List<Item> archivedItems = service.findByStatus(Item.Status.ARCHIVED);
            assertThat(archivedItems).hasSize(2);
        }
    }
}