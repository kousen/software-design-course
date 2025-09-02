package edu.trincoll.tracker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Base entity class for your domain object.
 * <p>
 * TODO: Rename this class to match your chosen domain
 * Examples: Bookmark, Quote, Habit, Recipe, Movie
 * <p>
 * TODO: Add at least 3 meaningful fields beyond 'id'
 * Examples for different domains:
 * - Bookmark: url, title, category, tags
 * - Quote: text, author, source, category
 * - Habit: name, frequency, streak, lastCompleted
 * - Recipe: name, ingredients, instructions, prepTime
 * - Movie: title, director, year, rating, watched
 */
public class Item {
    
    private Long id;
    
    // TODO: Replace these example fields with your domain-specific fields
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    
    private String description;
    
    private LocalDateTime createdAt;
    
    private boolean completed;
    
    // Constructor
    public Item() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }
    
    // TODO: Generate getters and setters for all your fields
    // Hint: IntelliJ can do this for you (Alt+Insert or Cmd+N)
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    // TODO: Consider overriding equals() and hashCode() based on your domain
    // This is important for testing and collections
}