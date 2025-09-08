package edu.trincoll.repository;

import edu.trincoll.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * TODO: Rename this class to match your domain
 * 
 * In-memory implementation of the repository using Java collections.
 * Uses ConcurrentHashMap for thread-safety.
 */
@Repository
public class InMemoryItemRepository implements ItemRepository {
    
    private final Map<Long, Item> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Item save(Item entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        storage.put(entity.getId(), entity);
        return entity;
    }
    
    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
    
    @Override
    public List<Item> findAll() {
        // TODO: Return defensive copy
        return new ArrayList<>(storage.values());
    }
    
    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
    
    @Override
    public long count() {
        return storage.size();
    }
    
    @Override
    public void deleteAll() {
        storage.clear();
        idGenerator.set(1);
    }
    
    @Override
    public List<Item> saveAll(List<Item> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Item> findByStatus(Item.Status status) {
        // TODO: Implement using streams
        return storage.values().stream()
                .filter(item -> item.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Item> findByCategory(String category) {
        // TODO: Implement
        return Collections.emptyList();
    }
    
    @Override
    public List<Item> findByTag(String tag) {
        // TODO: Implement
        return Collections.emptyList();
    }
    
    @Override
    public List<Item> findByTitleContaining(String searchTerm) {
        // TODO: Implement case-insensitive search
        return Collections.emptyList();
    }
}