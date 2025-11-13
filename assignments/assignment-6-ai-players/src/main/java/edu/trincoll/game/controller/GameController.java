package edu.trincoll.game.controller;

import edu.trincoll.game.command.CommandInvoker;
import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.model.Character;
import edu.trincoll.game.player.GameState;
import edu.trincoll.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main game controller that orchestrates turn-based combat.
 * <p>
 * Design Patterns Demonstrated:
 * <p>
 * - FACADE: Simplifies complex game loop interactions
 * - MEDIATOR: Coordinates between players, characters, and commands
 * - ITERATOR: Manages turn order
 * <p>
 * This class shows how multiple patterns work together:
 * <p>
 * - Players (Strategy) make decisions
 * - Commands (Command) encapsulate actions
 * - Controller (Mediator/Facade) orchestrates everything
 */
public class GameController {
    private final List<Character> team1;
    private final List<Character> team2;
    private final Map<Character, Player> playerMap;
    private final CommandInvoker invoker;
    private GameState gameState;

    public GameController(List<Character> team1,
                         List<Character> team2,
                         Map<Character, Player> playerMap) {
        this.team1 = new ArrayList<>(team1);
        this.team2 = new ArrayList<>(team2);
        this.playerMap = new HashMap<>(playerMap);
        this.invoker = new CommandInvoker();
        this.gameState = GameState.initial();
    }

    /**
     * Runs the main game loop until one team is defeated.
     * <p>
     * TODO 4: Implement game loop (15 points)
     * <p>
     * The game loop should:
     * <p>
     * 1. Check win condition (isGameOver())
     * 2. Process each character's turn in team1
     * 3. Check win condition again
     * 4. Process each character's turn in team2
     * 5. Update game state for next round
     * 6. Display round summary
     * <p>
     * For each turn:
     * <p>
     * - Get the Player for the character
     * - Call player.decideAction(character, allies, enemies, gameState)
     * - Execute the command using invoker
     * - Update game state
     * - Display action result
     * <p>
     * Hint: Use processTurn() helper method for each character
     */
    public void playGame() {
        throw new UnsupportedOperationException("TODO 4: Implement game loop");
    }

    /**
     * Processes a single character's turn.
     * <p>
     * TODO 5: Implement turn processing (10 points)
     * <p>
     * Steps:
     * <p>
     * 1. Get the player controlling this character
     * 2. Determine allies and enemies lists
     * 3. Call player.decideAction() to get a command
     * 4. Execute the command via invoker
     * 5. Display what happened
     * 6. Update game state
     * <p>
     * @param character the character taking their turn
     * @param allies the character's team
     * @param enemies the opposing team
     */
    private void processTurn(Character character,
                            List<Character> allies,
                            List<Character> enemies) {
        // Skip if character is defeated
        if (character.getStats().health() <= 0) {
            return;
        }

        // TODO 5: Get player and execute their decision
        throw new UnsupportedOperationException("TODO 5: Process character turn");
    }

    /**
     * Checks if the game is over.
     * <p>
     * Game ends when all characters on one team are defeated (HP <= 0).
     *
     * @return true if game is over, false otherwise
     */
    private boolean isGameOver() {
        boolean team1Alive = team1.stream()
            .anyMatch(c -> c.getStats().health() > 0);
        boolean team2Alive = team2.stream()
            .anyMatch(c -> c.getStats().health() > 0);

        return !team1Alive || !team2Alive;
    }

    /**
     * Displays the game result.
     */
    public void displayResult() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("GAME OVER");
        System.out.println("=".repeat(60));

        boolean team1Wins = team1.stream().anyMatch(c -> c.getStats().health() > 0);

        if (team1Wins) {
            System.out.println("🏆 Team 1 wins!");
        } else {
            System.out.println("🏆 Team 2 wins!");
        }

        System.out.println("\nFinal Status:");
        System.out.println("\nTeam 1:");
        for (Character c : team1) {
            displayCharacterStatus(c);
        }

        System.out.println("\nTeam 2:");
        for (Character c : team2) {
            displayCharacterStatus(c);
        }

        System.out.println("\nTotal turns played: " + gameState.turnNumber());
        System.out.println("Total commands executed: " + gameState.commandHistorySize());
    }

    private void displayCharacterStatus(Character c) {
        String status = c.getStats().health() > 0 ? "Alive" : "Defeated";
        System.out.printf("  %s (%s): %d HP - %s%n",
            c.getName(),
            c.getType(),
            Math.max(0, c.getStats().health()),
            status);
    }
}
