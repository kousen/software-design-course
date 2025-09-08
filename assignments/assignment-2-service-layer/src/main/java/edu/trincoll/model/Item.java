package edu.trincoll.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Rename this class to match your domain from Assignment 1
 * Examples: Bookmark, Quote, Habit, Recipe, Movie
 * 
 * Add at least 3 meaningful fields beyond the basics shown here.
 */
public class Item {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Status status;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum Status {
        ACTIVE, INACTIVE, ARCHIVED
    }
    
    public Item() {
        this.tags = new HashSet<>();
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Item(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }
    
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            tags.add(tag.toLowerCase().trim());
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    public void removeTag(String tag) {
        tags.remove(tag.toLowerCase().trim());
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean hasTag(String tag) {
        return tags.contains(tag.toLowerCase().trim());
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }
    
    public void setTags(Set<String> tags) {
        this.tags = new HashSet<>(tags);
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public String toString() {
        return String.format("Item[id=%d, title='%s', category='%s', status=%s]", 
                id, title, category, status);
    }
}