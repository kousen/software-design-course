package edu.trincoll.game.player;

import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.model.Character;

import java.util.List;

/**
 * Strategy pattern for player decision-making.
 * <p>
 * This interface demonstrates the Strategy pattern at a higher level:
 * different players (Human, AI, Rule-based) use different algorithms
 * to decide which action to take in the game.
 * <p>
 * Design Pattern: STRATEGY
 * <p>
 * - Encapsulates decision-making algorithms
 * - Makes players interchangeable
 * - Open-Closed Principle: add new player types without modifying existing code
 */
public interface Player {
    /**
     * Decides what action this player should take.
     *
     * @param self the character this player is controlling
     * @param allies list of allied characters (including self)
     * @param enemies list of enemy characters
     * @param gameState current game state information
     * @return the command to execute
     */
    GameCommand decideAction(Character self,
                            List<Character> allies,
                            List<Character> enemies,
                            GameState gameState);
}
