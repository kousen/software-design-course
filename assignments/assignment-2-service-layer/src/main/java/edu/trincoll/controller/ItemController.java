package edu.trincoll.controller;

import edu.trincoll.model.Item;
import edu.trincoll.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO: Rename this controller to match your domain
 * 
 * REST controller - should ONLY handle HTTP concerns.
 * All business logic should be in the service layer.
 */
@RestController
@RequestMapping("/api/items")
public class ItemController {
    
    private final ItemService service;
    
    public ItemController(ItemService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<Item> getAllItems() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            Item saved = service.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        item.setId(id);
        try {
            Item updated = service.save(item);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Additional endpoints for collections operations
    
    @GetMapping("/status/{status}")
    public List<Item> getItemsByStatus(@PathVariable Item.Status status) {
        return service.findByStatus(status);
    }
    
    @GetMapping("/category/{category}")
    public List<Item> getItemsByCategory(@PathVariable String category) {
        return service.findByCategory(category);
    }
    
    @GetMapping("/grouped")
    public Map<String, List<Item>> getItemsGroupedByCategory() {
        return service.groupByCategory();
    }
    
    @GetMapping("/tags")
    public Set<String> getAllTags() {
        return service.getAllUniqueTags();
    }
    
    @GetMapping("/stats/status")
    public Map<Item.Status, Long> getStatusStatistics() {
        return service.countByStatus();
    }
    
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam String query) {
        return service.search(query);
    }
}