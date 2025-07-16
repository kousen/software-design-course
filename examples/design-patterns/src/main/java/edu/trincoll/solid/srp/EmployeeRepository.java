package edu.trincoll.solid.srp;

import java.util.*;

public class EmployeeRepository {
    private final Map<String, Employee> database = new HashMap<>();
    
    public void save(Employee employee) {
        database.put(employee.getEmail(), employee);
    }
    
    public Optional<Employee> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }
    
    public List<Employee> findAll() {
        return new ArrayList<>(database.values());
    }
    
    public void delete(String email) {
        database.remove(email);
    }
}