package edu.trincoll.game.template;

import edu.trincoll.game.model.Character;

/**
 * Standard battle sequence - simple attack with no special actions.
 *
 * TODO 5b: Implement performAttack()
 *
 * This concrete implementation only needs to define the attack behavior.
 * The rest of the sequence is handled by the template method.
 */
public class StandardBattleSequence extends BattleSequence {

    public StandardBattleSequence(Character attacker, Character defender) {
        super(attacker, defender);
    }

    /**
     * TODO 5b: Implement performAttack()
     *
     * Requirements:
     * 1. Calculate damage: attacker.attack(defender)
     * 2. Apply damage: defender.takeDamage(calculatedDamage)
     */
    @Override
    protected void performAttack() {
        // TODO 5b: Implement standard attack
        throw new UnsupportedOperationException("TODO 5b: Implement StandardBattleSequence.performAttack()");
    }
}
