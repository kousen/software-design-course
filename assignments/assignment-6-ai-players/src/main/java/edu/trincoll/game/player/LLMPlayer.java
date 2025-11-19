package edu.trincoll.game.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.trincoll.game.command.AttackCommand;
import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.command.HealCommand;
import edu.trincoll.game.model.Character;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;

/**
 * LLM-based AI player using Spring AI.
 * <p>
 * This class demonstrates how to integrate Large Language Models
 * into game AI using the Strategy pattern. The LLM acts as the
 * decision-making algorithm, making this player fundamentally
 * different from rule-based AI.
 * <p>
 * Design Patterns:
 * <p>
 * - STRATEGY: Implements Player interface with LLM-based decisions
 * - ADAPTER: Adapts LLM output format to game commands
 * - FACADE: Simplifies complex LLM interaction
 * <p>
 * Students will implement the prompt engineering and response parsing.
 */
public class LLMPlayer implements Player {
    private final ChatClient chatClient;
    private final String modelName;

    public LLMPlayer(ChatClient chatClient, String modelName) {
        this.chatClient = chatClient;
        this.modelName = modelName;
    }

    @Override
    public GameCommand decideAction(Character self,
                                   List<Character> allies,
                                   List<Character> enemies,
                                   GameState gameState) {
        // TODO 1: Build the prompt (10 points)
        // Create a detailed prompt that gives the LLM:
        // - Character information (name, type, HP, mana, stats)
        // - Allies status
        // - Enemies status
        // - Available actions
        // - Strategic context
        //
        // Hint: Use String templates or StringBuilder
        // Good prompts should be clear, structured, and include examples
        String prompt = buildPrompt(self, allies, enemies, gameState);

        // TODO 2: Call the LLM and parse response (15 points)
        // Use the ChatClient to get a Decision object from the LLM
        // Spring AI will automatically deserialize the JSON response into the Decision record
        //
        // Example:
        //   Decision decision = chatClient.prompt()
        //       .user(prompt)
        //       .call()
        //       .entity(Decision.class);
        //
        // Handle errors gracefully (fallback to default action if parsing fails)
        //
        // Expected JSON format from LLM:
        // {
        //   "action": "attack" | "heal",
        //   "target": "character_name",
        //   "reasoning": "why this decision was made"
        // }
        throw new UnsupportedOperationException("TODO 2: Implement LLM call and parse response");

        // TODO 3: Convert Decision to GameCommand (10 points)
        // Based on the decision.action(), create the appropriate GameCommand:
        // - "attack" -> new AttackCommand(self, target)
        // - "heal" -> new HealCommand(self, target)
        //
        // Use findCharacterByName() to locate the target character
        // Hint: Use a switch expression or if-else to handle different actions
    }

    /**
     * TODO 1: Implement this method to build an effective prompt.
     *
     * A good prompt should include:
     * 1. Role definition: "You are a [character type] in a tactical RPG..."
     * 2. Current situation: HP, mana, position in battle
     * 3. Allies: Who's on your team and their status
     * 4. Enemies: Who you're fighting and their status
     * 5. Available actions: attack (with damage estimate) or heal
     * 6. Strategic guidance: "Consider focus fire, protect wounded allies..."
     * 7. Output format: JSON structure expected
     *
     * Example structure:
     * """
     * You are {character_name}, a {type} warrior in a turn-based RPG battle.
     *
     * YOUR STATUS:
     * - HP: {current}/{max} ({percent}%)
     * - Mana: {current}/{max}
     * - Attack Power: {attack}
     * - Defense: {defense}
     *
     * YOUR TEAM:
     * {list allies with HP and status}
     *
     * ENEMIES:
     * {list enemies with HP and status}
     *
     * AVAILABLE ACTIONS:
     * 1. attack <target_name> - Deal ~{estimate} damage
     * 2. heal <target_name> - Restore 30 HP
     *
     * STRATEGY TIPS:
     * - Focus fire on weak enemies to reduce enemy actions
     * - Heal allies below 30% HP to prevent deaths
     * - Consider your character type's strengths
     *
     * Respond with JSON:
     * {
     *   "action": "attack" or "heal",
     *   "target": "character name",
     *   "reasoning": "brief explanation"
     * }
     * """
     *
     * @param self your character
     * @param allies your team
     * @param enemies opponent team
     * @param gameState current game state
     * @return prompt string for the LLM
     */
    private String buildPrompt(Character self,
                              List<Character> allies,
                              List<Character> enemies,
                              GameState gameState) {
        // TODO 1: Implement prompt building
        // See method documentation above for structure
        throw new UnsupportedOperationException("TODO 1: Build prompt for LLM");
    }

    /**
     * Formats a list of characters for display in the prompt.
     *
     * Helper method provided to students.
     */
    private String formatCharacterList(List<Character> characters) {
        StringBuilder sb = new StringBuilder();
        for (Character c : characters) {
            double healthPercent = (double) c.getStats().health() / c.getStats().maxHealth() * 100;
            sb.append(String.format("  - %s (%s): %d/%d HP (%.0f%%), %d ATK, %d DEF%n",
                c.getName(),
                c.getType(),
                c.getStats().health(),
                c.getStats().maxHealth(),
                healthPercent,
                c.getStats().attackPower(),
                c.getStats().defense()));
        }
        return sb.toString();
    }

    /**
     * Estimates damage this character would deal to a target.
     *
     * Helper method provided to students.
     */
    private int estimateDamage(Character attacker, Character target) {
        // Rough estimate using attack strategy
        int baseDamage = attacker.attack(target);
        return target.getDefenseStrategy()
            .calculateDamageReduction(target, baseDamage);
    }

    /**
     * Finds a character by name in a list.
     *
     * Helper method provided to students.
     */
    private Character findCharacterByName(String name, List<Character> characters) {
        return characters.stream()
            .filter(c -> c.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(characters.getFirst()); // Fallback to first if not found
    }

    /**
     * Record for parsing LLM JSON response.
     *
     * Uses Jackson annotations for JSON deserialization.
     * This is provided to students as a reference for JSON structure.
     */
    public record Decision(
        @JsonProperty(required = true) String action,
        @JsonProperty(required = true) String target,
        @JsonProperty String reasoning
    ) {}
}
