package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Strategy pattern for defense behaviors.
 * Different defense strategies reduce incoming damage differently.
 *
 * This is a functional interface, so it can be implemented with lambda expressions.
 */
@FunctionalInterface
public interface DefenseStrategy {
    /**
     * Calculate damage reduction based on defender's stats.
     *
     * @param defender The character defending
     * @param incomingDamage The amount of damage before defense
     * @return The actual damage after defense calculation
     */
    int calculateDamageReduction(Character defender, int incomingDamage);
}
