package edu.trincoll.tracker;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;

/**
 * AI Collaboration Report:
 * - AI Tool Used: [Your AI tool here - ChatGPT/Claude/Copilot/Gemini]
 * - Most Helpful Prompt: [Paste the prompt that worked best]
 * - AI Mistake We Fixed: [Describe what the AI got wrong and how you fixed it]
 * - Time Saved: [Estimate hours saved using AI]
 * - Team Members: [List your team members' names]
 */
@RestController
@RequestMapping("/api/items") // TODO: Rename to match your domain (e.g., /api/bookmarks, /api/recipes)
public class ItemController {
    
    // TODO: You'll need some way to store items - consider using a List or Map for now
    // (We'll learn about databases in Week 5)
    
    /**
     * GET /api/items
     * Returns all items in the system
     * <p>
     * TODO: Implement this endpoint
     * Hint: Return a List of your domain objects
     */
    
    /**
     * GET /api/items/{id}
     * Returns a specific item by ID
     * <p>
     * TODO: Implement this endpoint
     * Hint: Return 404 if item doesn't exist
     */
    
    /**
     * POST /api/items
     * Creates a new item
     * <p>
     * TODO: Implement this endpoint
     * Hint: Validate required fields, return 400 for bad requests
     */
    
    /**
     * PUT /api/items/{id}
     * Updates an existing item
     * <p>
     * TODO: Implement this endpoint
     * Hint: Return 404 if item doesn't exist
     */
    
    /**
     * DELETE /api/items/{id}
     * Deletes an item
     * <p>
     * TODO: Implement this endpoint
     * Hint: Return 204 No Content on successful delete
     */
    
    /**
     * GET /api/items/search?field=value
     * Searches items by a specific field
     * <p>
     * TODO: Implement this endpoint (BONUS)
     * Hint: Use @RequestParam to get query parameters
     */
}