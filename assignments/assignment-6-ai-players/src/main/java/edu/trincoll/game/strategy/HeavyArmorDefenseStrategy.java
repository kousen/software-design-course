package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Heavy armor defense - better damage reduction than standard.
 * Used by Warriors.
 *
 * TODO 1e: Implement calculateDamageReduction()
 *
 * Requirements:
 * - Calculate damage reduction: defense (full defense value)
 * - Actual damage = incoming damage - damage reduction
 * - Ensure actual damage is never negative (minimum 0)
 * - Maximum 75% damage reduction (even if defense is very high)
 *
 * Example 1: Defender has 30 defense, incoming damage is 100
 *   Damage reduction: 30
 *   Actual damage: 100 - 30 = 70
 *   Return: 70
 *
 * Example 2: Defender has 80 defense, incoming damage is 100
 *   Theoretical reduction: 80 (would leave 20 damage)
 *   But max reduction is 75%, so: 100 * 0.25 = 25
 *   Return: 25
 */
public class HeavyArmorDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDamageReduction(Character defender, int incomingDamage) {
        // Damage reduction = full defense value
        int damageReduction = defender.getStats().defense();

        // Actual damage = incoming - reduction
        int actualDamage = incomingDamage - damageReduction;

        // Cap at 75% reduction (minimum 25% damage gets through)
        int minDamage = (int) (incomingDamage * 0.25);

        // Return the greater of actualDamage or minDamage, ensuring never negative
        return Math.max(minDamage, Math.max(0, actualDamage));
    }
}
