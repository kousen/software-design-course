package edu.trincoll.service;

import edu.trincoll.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Abstract base service providing common CRUD operations.
 * Follows the Template Method pattern for validation.
 * 
 * @param <T> The entity type
 * @param <ID> The ID type
 */
public abstract class BaseService<T, ID> {
    
    /**
     * Get the repository instance for data access
     * @return the repository
     */
    protected abstract Repository<T, ID> getRepository();
    
    /**
     * Validate an entity before saving
     * @param entity the entity to validate
     * @throws IllegalArgumentException if validation fails
     */
    public abstract void validateEntity(T entity);
    
    /**
     * Save an entity with validation
     * @param entity the entity to save
     * @return the saved entity
     */
    public T save(T entity) {
        validateEntity(entity);
        return getRepository().save(entity);
    }
    
    /**
     * Find an entity by ID
     * @param id the ID to search for
     * @return Optional containing the entity if found
     */
    public Optional<T> findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return getRepository().findById(id);
    }
    
    /**
     * Find all entities
     * @return list of all entities
     */
    public List<T> findAll() {
        return getRepository().findAll();
    }
    
    /**
     * Delete an entity by ID
     * @param id the ID of entity to delete
     * @throws IllegalArgumentException if entity doesn't exist
     */
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found");
        }
        getRepository().deleteById(id);
    }
    
    /**
     * Check if an entity exists
     * @param id the ID to check
     * @return true if exists
     */
    public boolean existsById(ID id) {
        return id != null && getRepository().existsById(id);
    }
    
    /**
     * Get total count of entities
     * @return the count
     */
    public long count() {
        return getRepository().count();
    }
    
    /**
     * Delete all entities
     */
    public void deleteAll() {
        getRepository().deleteAll();
    }
    
    /**
     * Save multiple entities with validation
     * @param entities the entities to save
     * @return list of saved entities
     */
    public List<T> saveAll(List<T> entities) {
        entities.forEach(this::validateEntity);
        return getRepository().saveAll(entities);
    }
}