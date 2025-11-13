package edu.trincoll.game;

import edu.trincoll.game.controller.GameController;
import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.player.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main Spring Boot application for AI-powered RPG game.
 * <p>
 * This application demonstrates:
 * <p>
 * - Spring Boot autoconfiguration
 * - Spring AI integration
 * - Command-line game interface
 * - Design patterns working together
 * <p>
 * Run with:
 * <p>
 *   ./gradlew run
 * <p>
 * Or with API keys:
 * <p>
 *   OPENAI_API_KEY=xxx ANTHROPIC_API_KEY=yyy ./gradlew run
 */
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class, args);
    }

    /**
     * CommandLineRunner bean that executes after Spring Boot starts.
     * <p>
     * This is where the game setup and execution happens.
     * Students will implement team configuration here.
     *
     * @param openAiClient ChatClient for OpenAI/GPT-5
     * @param anthropicClient ChatClient for Anthropic/Claude Sonnet 4.5
     * @param geminiClient ChatClient for Google/Gemini 2.5 Pro
     * @return CommandLineRunner that starts the game
     */
    @Bean
    public CommandLineRunner run(
            @Qualifier("openAiChatClient") ChatClient openAiClient,
            @Qualifier("anthropicChatClient") ChatClient anthropicClient,
            @Qualifier("geminiChatClient") ChatClient geminiClient) {

        return args -> {
            System.out.println("""
                ============================================================
                AI-POWERED RPG GAME
                ============================================================

                This game demonstrates design patterns with AI players:
                - Strategy Pattern: Different AI decision-making algorithms
                - Command Pattern: Undoable game actions
                - Factory Pattern: Character creation
                - Builder Pattern: Complex object construction

                Players can be:
                - Human (you control via console)
                - LLM-based (GPT-4, Claude, or Gemini)
                - Rule-based AI (simple if-then logic)
                ============================================================
                """);

            // TODO 6: Implement team configuration (15 points)
            // <p>
            // Create two teams with a mix of player types:
            // <p>
            // - Team 1: Should include at least 1 human player
            // - Team 2: Should use all three AI models (GPT-5, Claude Sonnet 4.5, Gemini 2.5 Pro)
            // <p>
            // Example team setup:
            // <p>
            //   Team 1: Human Warrior, RuleBasedAI Mage
            //   Team 2: GPT-5 Archer, Claude Sonnet 4.5 Rogue, Gemini 2.5 Pro Warrior
            // <p>
            // Steps:
            // <p>
            // 1. Create characters using CharacterFactory
            // 2. Create Player instances (Human, LLMPlayer, RuleBasedPlayer)
            // 3. Map each character to their player
            // 4. Create GameController with teams and player map
            // 5. Run the game
            // <p>
            // Hint: Use the helper method createTeamConfiguration() below

            throw new UnsupportedOperationException("TODO 6: Configure teams and start game");
        };
    }

    /**
     * Helper method to create team configuration.
     *
     * TODO 6 (part of): Students implement this to set up teams.
     *
     * Example implementation structure:
     * ```
     * // Team 1: Human + RuleBasedAI
     * Character humanWarrior = CharacterFactory.createWarrior("Conan");
     * Character aiMage = CharacterFactory.createMage("Gandalf");
     * List<Character> team1 = List.of(humanWarrior, aiMage);
     *
     * // Team 2: Three LLM players
     * Character gptArcher = CharacterFactory.createArcher("Legolas");
     * Character claudeRogue = CharacterFactory.createRogue("Assassin");
     * Character geminiWarrior = CharacterFactory.createWarrior("Tank");
     * List<Character> team2 = List.of(gptArcher, claudeRogue, geminiWarrior);
     *
     * // Map characters to players
     * Map<Character, Player> playerMap = new HashMap<>();
     * playerMap.put(humanWarrior, new HumanPlayer());
     * playerMap.put(aiMage, new RuleBasedPlayer());
     * playerMap.put(gptArcher, new LLMPlayer(openAiClient, "GPT-5"));
     * playerMap.put(claudeRogue, new LLMPlayer(anthropicClient, "Claude-Sonnet-4.5"));
     * playerMap.put(geminiWarrior, new LLMPlayer(geminiClient, "Gemini-2.5-Pro"));
     *
     * return new GameController(team1, team2, playerMap);
     * ```
     */
    private GameController createTeamConfiguration(
            ChatClient openAiClient,
            ChatClient anthropicClient,
            ChatClient geminiClient) {
        // TODO 6: Implement team configuration
        throw new UnsupportedOperationException("TODO 6: Implement team configuration");
    }

    /**
     * Interactive team setup (optional enhancement).
     *
     * This could allow users to choose their team composition
     * via console prompts. Not required for base assignment.
     */
    private GameController interactiveSetup(
            ChatClient openAiClient,
            ChatClient anthropicClient,
            ChatClient geminiClient) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Team Setup ===");
        System.out.println("Configure your team!");

        // Let user choose characters and AI models
        // This is an optional feature for students to implement

        throw new UnsupportedOperationException("Optional: Interactive setup not implemented");
    }
}
