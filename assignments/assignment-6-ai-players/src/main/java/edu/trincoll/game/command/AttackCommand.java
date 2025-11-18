package edu.trincoll.game.command;

import edu.trincoll.game.model.Character;

/**
 * Command to execute an attack from one character to another.
 *
 * TODO 4a: Implement execute() and undo()
 *
 * Requirements for execute():
 * 1. Calculate damage: attacker.attack(target)
 * 2. Apply damage: target.takeDamage(calculatedDamage)
 * 3. Store the damage dealt for potential undo
 *
 * Requirements for undo():
 * 1. Heal the target for the amount of damage that was dealt
 * 2. Use target.heal(damageDealt)
 *
 * Note: This is a simplified undo - in a real game, you'd need to
 * restore mana usage, status effects, etc.
 */
public class AttackCommand implements GameCommand {
    private final Character attacker;
    private final Character target;
    private int actualHealthLost;

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute() {
        // Store health before attack
        int healthBefore = target.getStats().health();

        // Calculate damage
        int damageDealt = attacker.attack(target);

        // Apply damage to target
        target.takeDamage(damageDealt);

        // Store actual health lost for undo
        actualHealthLost = healthBefore - target.getStats().health();
    }

    @Override
    public void undo() {
        // Heal the target for the actual health lost
        target.heal(actualHealthLost);
    }

    @Override
    public String getDescription() {
        return String.format("%s attacks %s", attacker.getName(), target.getName());
    }
}
