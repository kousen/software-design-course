package edu.trincoll.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining basic CRUD operations.
 * This follows the Repository pattern for data access abstraction.
 * 
 * @param <T> The entity type
 * @param <ID> The ID type (usually Long)
 */
public interface Repository<T, ID> {
    
    /**
     * Save an entity (create or update)
     * @param entity the entity to save
     * @return the saved entity with ID assigned
     */
    T save(T entity);
    
    /**
     * Find an entity by its ID
     * @param id the ID to search for
     * @return Optional containing the entity if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Find all entities
     * @return list of all entities
     */
    List<T> findAll();
    
    /**
     * Delete an entity by ID
     * @param id the ID of entity to delete
     */
    void deleteById(ID id);
    
    /**
     * Check if an entity exists by ID
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean existsById(ID id);
    
    /**
     * Count total number of entities
     * @return the count
     */
    long count();
    
    /**
     * Delete all entities
     */
    void deleteAll();
    
    /**
     * Save multiple entities
     * @param entities the entities to save
     * @return list of saved entities
     */
    List<T> saveAll(List<T> entities);
}