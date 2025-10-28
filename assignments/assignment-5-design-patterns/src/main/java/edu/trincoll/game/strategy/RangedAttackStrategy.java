package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Ranged attack - physical damage with accuracy modifier.
 * Used by Archers.
 *
 * TODO 1c: Implement calculateDamage()
 *
 * Requirements:
 * - Base damage = attacker's attack power
 * - Apply 80% accuracy (multiply by 0.8)
 * - Add critical hit bonus: if target's health < 30% of max, add 50% bonus
 * - Return total as integer
 *
 * Example 1 (no critical): Attacker has 50 attack, target has 80/100 HP
 *   Base: 50
 *   With accuracy: 50 * 0.8 = 40
 *   Target health % = 80/100 = 80% (no critical)
 *   Return: 40
 *
 * Example 2 (critical): Attacker has 50 attack, target has 20/100 HP
 *   Base: 50
 *   With accuracy: 50 * 0.8 = 40
 *   Target health % = 20/100 = 20% (< 30%, critical!)
 *   Critical bonus: 40 * 1.5 = 60
 *   Return: 60
 */
public class RangedAttackStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(Character attacker, Character target) {
        // TODO 1c: Implement ranged attack with critical hit logic
        throw new UnsupportedOperationException("TODO 1c: Implement ranged attack calculation");
    }
}
