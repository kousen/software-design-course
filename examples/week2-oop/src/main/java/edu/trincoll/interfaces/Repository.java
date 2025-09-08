package edu.trincoll.interfaces;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    
    T save(T entity);
    
    Optional<T> findById(ID id);
    
    List<T> findAll();
    
    boolean existsById(ID id);
    
    void deleteById(ID id);
    
    void delete(T entity);
    
    void deleteAll();
    
    long count();
    
    List<T> saveAll(List<T> entities);
}