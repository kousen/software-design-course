package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Strategy pattern for attack behaviors.
 * Different attack strategies calculate damage differently.
 *
 * This is a functional interface, so it can be implemented with lambda expressions
 * for custom attack behaviors (modern Java approach from Week 7).
 */
@FunctionalInterface
public interface AttackStrategy {
    /**
     * Calculate damage dealt to the target.
     *
     * @param attacker The character performing the attack
     * @param target The character being attacked
     * @return The amount of damage to apply (before defense calculation)
     */
    int calculateDamage(Character attacker, Character target);
}
