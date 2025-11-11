package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Standard defense - reduces damage by a percentage of defense stat.
 * Used by most character types.
 *
 * TODO 1d: Implement calculateDamageReduction()
 *
 * Requirements:
 * - Calculate damage reduction: defense / 2
 * - Actual damage = incoming damage - damage reduction
 * - Ensure actual damage is never negative (minimum 0)
 *
 * Example: Defender has 20 defense, incoming damage is 50
 *   Damage reduction: 20 / 2 = 10
 *   Actual damage: 50 - 10 = 40
 *   Return: 40
 */
public class StandardDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDamageReduction(Character defender, int incomingDamage) {
        // TODO 1d: Implement standard defense calculation
        throw new UnsupportedOperationException("TODO 1d: Implement standard defense calculation");
    }
}
