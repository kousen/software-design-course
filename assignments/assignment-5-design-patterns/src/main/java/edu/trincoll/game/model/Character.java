package edu.trincoll.game.model;

import edu.trincoll.game.strategy.AttackStrategy;
import edu.trincoll.game.strategy.DefenseStrategy;

import java.util.Objects;

/**
 * Represents a game character with stats and behavior strategies.
 * This class will be constructed using the Builder pattern.
 */
public class Character {
    private final String name;
    private final CharacterType type;
    private CharacterStats stats;
    private AttackStrategy attackStrategy;
    private DefenseStrategy defenseStrategy;

    // Public constructor for testing - prefer Builder for production use
    public Character(String name, CharacterType type, CharacterStats stats,
                     AttackStrategy attackStrategy, DefenseStrategy defenseStrategy) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.type = Objects.requireNonNull(type, "Type cannot be null");
        this.stats = Objects.requireNonNull(stats, "Stats cannot be null");
        this.attackStrategy = Objects.requireNonNull(attackStrategy, "Attack strategy cannot be null");
        this.defenseStrategy = Objects.requireNonNull(defenseStrategy, "Defense strategy cannot be null");
    }

    // Getters
    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public CharacterStats getStats() {
        return stats;
    }

    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    public DefenseStrategy getDefenseStrategy() {
        return defenseStrategy;
    }

    // Strategy setters (allow runtime strategy changes - Strategy pattern)
    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = Objects.requireNonNull(attackStrategy, "Attack strategy cannot be null");
    }

    public void setDefenseStrategy(DefenseStrategy defenseStrategy) {
        this.defenseStrategy = Objects.requireNonNull(defenseStrategy, "Defense strategy cannot be null");
    }

    // Combat methods that delegate to strategies
    public int attack(Character target) {
        return attackStrategy.calculateDamage(this, target);
    }

    public int defend(int incomingDamage) {
        return defenseStrategy.calculateDamageReduction(this, incomingDamage);
    }

    // Health management
    public void takeDamage(int damage) {
        int actualDamage = defend(damage);
        int netDamage = Math.max(0, actualDamage);
        stats = stats.withHealth(stats.health() - netDamage);
    }

    public void heal(int amount) {
        stats = stats.withHealth(stats.health() + amount);
    }

    /**
     * Set health directly (used for command undo operations and testing).
     * Use with caution - bypasses defense calculations.
     */
    public void setHealth(int health) {
        stats = stats.withHealth(health);
    }

    // Mana management
    public void useMana(int amount) {
        if (stats.mana() < amount) {
            throw new IllegalStateException("Not enough mana");
        }
        stats = stats.withMana(stats.mana() - amount);
    }

    public void restoreMana(int amount) {
        stats = stats.withMana(stats.mana() + amount);
    }

    // Status checks
    public boolean isAlive() {
        return stats.isAlive();
    }

    public boolean isDead() {
        return stats.isDead();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - HP: %d/%d, ATK: %d, DEF: %d",
            name, type, stats.health(), stats.maxHealth(),
            stats.attackPower(), stats.defense());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name) &&
               type == character.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    /**
     * Builder for creating Character instances.
     * TODO: Students will implement this as part of the Builder pattern section.
     */
    public static class Builder {
        private String name;
        private CharacterType type;
        private CharacterStats stats;
        private AttackStrategy attackStrategy;
        private DefenseStrategy defenseStrategy;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(CharacterType type) {
            this.type = type;
            return this;
        }

        public Builder stats(CharacterStats stats) {
            this.stats = stats;
            return this;
        }

        public Builder attackStrategy(AttackStrategy attackStrategy) {
            this.attackStrategy = attackStrategy;
            return this;
        }

        public Builder defenseStrategy(DefenseStrategy defenseStrategy) {
            this.defenseStrategy = defenseStrategy;
            return this;
        }

        /**
         * TODO 3: Implement the build() method
         *
         * Requirements:
         * 1. Validate that all required fields are set (name, type, stats, both strategies)
         * 2. Throw IllegalStateException with clear message if any field is null
         * 3. Return a new Character instance
         *
         * This demonstrates the Builder pattern's ability to construct complex objects
         * while ensuring all required fields are provided before construction.
         */
        public Character build() {
            // TODO: Implement validation and construction
            throw new UnsupportedOperationException("TODO 3: Implement build() method");
        }
    }

    /**
     * Static factory method to create a Builder.
     * This is the entry point for using the Builder pattern.
     */
    public static Builder builder() {
        return new Builder();
    }
}
