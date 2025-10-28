package edu.trincoll.game.model;

/**
 * Immutable character statistics.
 * Using a record for clean, immutable data.
 */
public record CharacterStats(
    int health,
    int maxHealth,
    int attackPower,
    int defense,
    int mana,
    int maxMana
) {
    public CharacterStats {
        if (health < 0 || maxHealth <= 0) {
            throw new IllegalArgumentException("Health values must be positive");
        }
        if (health > maxHealth) {
            throw new IllegalArgumentException("Health cannot exceed max health");
        }
        if (attackPower < 0 || defense < 0) {
            throw new IllegalArgumentException("Stats cannot be negative");
        }
        if (mana < 0 || maxMana < 0) {
            throw new IllegalArgumentException("Mana values cannot be negative");
        }
        if (mana > maxMana) {
            throw new IllegalArgumentException("Mana cannot exceed max mana");
        }
    }

    /**
     * Create stats with full health and mana.
     */
    public static CharacterStats create(int maxHealth, int attackPower, int defense, int maxMana) {
        return new CharacterStats(maxHealth, maxHealth, attackPower, defense, maxMana, maxMana);
    }

    /**
     * Returns a copy with modified health.
     */
    public CharacterStats withHealth(int newHealth) {
        return new CharacterStats(
            Math.max(0, Math.min(newHealth, maxHealth)),
            maxHealth,
            attackPower,
            defense,
            mana,
            maxMana
        );
    }

    /**
     * Returns a copy with modified mana.
     */
    public CharacterStats withMana(int newMana) {
        return new CharacterStats(
            health,
            maxHealth,
            attackPower,
            defense,
            Math.max(0, Math.min(newMana, maxMana)),
            maxMana
        );
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
