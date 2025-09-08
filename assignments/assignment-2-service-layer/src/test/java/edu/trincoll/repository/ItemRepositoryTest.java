package edu.trincoll.repository;

import edu.trincoll.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for the repository layer.
 * These tests should pass when the repository is properly implemented.
 */
class ItemRepositoryTest {
    
    private ItemRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
        repository.deleteAll();
    }
    
    @Test
    @DisplayName("Should save and retrieve item by ID")
    void testSaveAndFindById() {
        Item item = new Item("Test Item", "Description");
        item.setCategory("Test Category");
        
        Item saved = repository.save(item);
        
        assertThat(saved.getId()).isNotNull();
        
        Optional<Item> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Item");
    }
    
    @Test
    @DisplayName("Should return empty Optional for non-existent ID")
    void testFindByIdNotFound() {
        Optional<Item> found = repository.findById(999L);
        assertThat(found).isEmpty();
    }
    
    @Test
    @DisplayName("Should find all items")
    void testFindAll() {
        repository.save(new Item("Item 1", "Desc 1"));
        repository.save(new Item("Item 2", "Desc 2"));
        repository.save(new Item("Item 3", "Desc 3"));
        
        List<Item> all = repository.findAll();
        
        assertThat(all).hasSize(3);
        assertThat(all).extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Item 1", "Item 2", "Item 3");
    }
    
    @Test
    @DisplayName("Should delete item by ID")
    void testDeleteById() {
        Item item = repository.save(new Item("To Delete", "Will be deleted"));
        Long id = item.getId();
        
        assertThat(repository.existsById(id)).isTrue();
        
        repository.deleteById(id);
        
        assertThat(repository.existsById(id)).isFalse();
        assertThat(repository.findById(id)).isEmpty();
    }
    
    @Test
    @DisplayName("Should check if item exists")
    void testExistsById() {
        Item item = repository.save(new Item("Exists", "Test"));
        
        assertThat(repository.existsById(item.getId())).isTrue();
        assertThat(repository.existsById(999L)).isFalse();
    }
    
    @Test
    @DisplayName("Should count items correctly")
    void testCount() {
        assertThat(repository.count()).isZero();
        
        repository.save(new Item("Item 1", "Desc"));
        repository.save(new Item("Item 2", "Desc"));
        
        assertThat(repository.count()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should delete all items")
    void testDeleteAll() {
        repository.save(new Item("Item 1", "Desc"));
        repository.save(new Item("Item 2", "Desc"));
        
        assertThat(repository.count()).isEqualTo(2);
        
        repository.deleteAll();
        
        assertThat(repository.count()).isZero();
        assertThat(repository.findAll()).isEmpty();
    }
    
    @Test
    @DisplayName("Should save multiple items")
    void testSaveAll() {
        List<Item> items = List.of(
                new Item("Item 1", "Desc 1"),
                new Item("Item 2", "Desc 2"),
                new Item("Item 3", "Desc 3")
        );
        
        List<Item> saved = repository.saveAll(items);
        
        assertThat(saved).hasSize(3);
        assertThat(saved).allMatch(item -> item.getId() != null);
        assertThat(repository.count()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should find items by status")
    void testFindByStatus() {
        Item active = new Item("Active", "Active item");
        active.setStatus(Item.Status.ACTIVE);
        
        Item inactive = new Item("Inactive", "Inactive item");
        inactive.setStatus(Item.Status.INACTIVE);
        
        Item archived = new Item("Archived", "Archived item");
        archived.setStatus(Item.Status.ARCHIVED);
        
        repository.save(active);
        repository.save(inactive);
        repository.save(archived);
        
        List<Item> activeItems = repository.findByStatus(Item.Status.ACTIVE);
        assertThat(activeItems).hasSize(1);
        assertThat(activeItems.get(0).getTitle()).isEqualTo("Active");
        
        List<Item> archivedItems = repository.findByStatus(Item.Status.ARCHIVED);
        assertThat(archivedItems).hasSize(1);
    }
    
    @Test
    @DisplayName("Should find items by category")
    void testFindByCategory() {
        Item item1 = new Item("Item 1", "Desc");
        item1.setCategory("Work");
        
        Item item2 = new Item("Item 2", "Desc");
        item2.setCategory("Personal");
        
        Item item3 = new Item("Item 3", "Desc");
        item3.setCategory("Work");
        
        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
        
        List<Item> workItems = repository.findByCategory("Work");
        
        // This test will fail until students implement the method
        assertThat(workItems).hasSize(2);
        assertThat(workItems).extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Item 1", "Item 3");
    }
    
    @Test
    @DisplayName("Should find items by tag")
    void testFindByTag() {
        Item item1 = new Item("Item 1", "Desc");
        item1.addTag("urgent");
        item1.addTag("bug");
        
        Item item2 = new Item("Item 2", "Desc");
        item2.addTag("feature");
        
        Item item3 = new Item("Item 3", "Desc");
        item3.addTag("urgent");
        
        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
        
        List<Item> urgentItems = repository.findByTag("urgent");
        
        // This test will fail until students implement the method
        assertThat(urgentItems).hasSize(2);
        assertThat(urgentItems).extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Item 1", "Item 3");
    }
    
    @Test
    @DisplayName("Should find items by title containing search term")
    void testFindByTitleContaining() {
        repository.save(new Item("Java Programming", "Book"));
        repository.save(new Item("Python Programming", "Book"));
        repository.save(new Item("JavaScript Guide", "Book"));
        repository.save(new Item("Data Structures", "Course"));
        
        List<Item> programmingItems = repository.findByTitleContaining("Programming");
        
        // This test will fail until students implement the method
        assertThat(programmingItems).hasSize(2);
        assertThat(programmingItems).extracting(Item::getTitle)
                .containsExactlyInAnyOrder("Java Programming", "Python Programming");
    }
}