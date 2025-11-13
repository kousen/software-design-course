package edu.trincoll.game.player;

import edu.trincoll.game.command.AttackCommand;
import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.command.HealCommand;
import edu.trincoll.game.model.Character;

import java.util.Comparator;
import java.util.List;

/**
 * Simple rule-based AI player.
 * <p>
 * This demonstrates a basic AI strategy using if-then rules.
 * Students will compare this against LLM-based decision-making.
 * <p>
 * Design Pattern: STRATEGY (concrete strategy)
 * <p>
 * - Implements Player interface
 * - Uses deterministic rules
 * - Provides baseline for AI comparison
 * <p>
 * Rules:
 * <p>
 * 1. If self HP < 30%, heal self
 * 2. If ally HP < 20%, heal weakest ally
 * 3. Otherwise, attack weakest enemy
 */
public class RuleBasedPlayer implements Player {
    private static final double SELF_HEAL_THRESHOLD = 0.30;
    private static final double ALLY_HEAL_THRESHOLD = 0.20;

    @Override
    public GameCommand decideAction(Character self,
                                   List<Character> allies,
                                   List<Character> enemies,
                                   GameState gameState) {
        // Rule 1: Self-preservation
        double healthPercent = (double) self.getStats().health() / self.getStats().maxHealth();
        if (healthPercent < SELF_HEAL_THRESHOLD) {
            return new HealCommand(self, 30);
        }

        // Rule 2: Help weakest ally if critical
        Character weakestAlly = allies.stream()
            .filter(c -> c != self)
            .filter(c -> {
                double hp = (double) c.getStats().health() / c.getStats().maxHealth();
                return hp < ALLY_HEAL_THRESHOLD;
            })
            .min(Comparator.comparingInt(c -> c.getStats().health()))
            .orElse(null);

        if (weakestAlly != null) {
            return new HealCommand(weakestAlly, 30);
        }

        // Rule 3: Attack weakest enemy (focus fire)
        Character weakestEnemy = enemies.stream()
            .min(Comparator.comparingInt(c -> c.getStats().health()))
            .orElse(enemies.get(0));

        return new AttackCommand(self, weakestEnemy);
    }
}
