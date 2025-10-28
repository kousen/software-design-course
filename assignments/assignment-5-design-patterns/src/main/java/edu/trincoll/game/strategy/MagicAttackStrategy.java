package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Magic attack - uses mana and attack power to calculate damage.
 * Used by Mages.
 *
 * TODO 1b: Implement calculateDamage()
 *
 * Requirements:
 * - Base damage = attacker's attack power
 * - Mana bonus = current mana / 10 (integer division)
 * - Total damage = base + mana bonus
 * - Reduce attacker's mana by 10 (use attacker.useMana(10))
 *   NOTE: If not enough mana, useMana() will throw an exception
 *
 * Example: If attacker has 60 attack power and 50 mana:
 *   Base: 60
 *   Mana bonus: 50 / 10 = 5
 *   Total: 65
 *   After attack: mana reduced by 10
 */
public class MagicAttackStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(Character attacker, Character target) {
        // TODO 1b: Implement magic attack calculation
        throw new UnsupportedOperationException("TODO 1b: Implement magic attack calculation");
    }
}
