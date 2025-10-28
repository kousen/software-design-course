package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Melee attack - straightforward physical damage based on attack power.
 * Used by Warriors and Rogues.
 *
 * TODO 1a: Implement calculateDamage()
 *
 * Requirements:
 * - Base damage = attacker's attack power
 * - Add 20% bonus damage (multiply by 1.2)
 * - Return the total as an integer
 *
 * Example: If attacker has 50 attack power:
 *   Base: 50
 *   With bonus: 50 * 1.2 = 60
 *   Return: 60
 */
public class MeleeAttackStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(Character attacker, Character target) {
        // TODO 1a: Implement melee attack calculation
        throw new UnsupportedOperationException("TODO 1a: Implement melee attack calculation");
    }
}
