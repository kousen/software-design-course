package edu.trincoll.game.factory;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterStats;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.strategy.*;

/**
 * Factory for creating pre-configured characters.
 * Demonstrates the Factory Method pattern for object creation.
 *
 * This class provides static factory methods that create characters
 * with appropriate stats and strategies for their type.
 */
public class CharacterFactory {

    /**
     * TODO 2a: Implement createWarrior()
     *
     * Create a Warrior character with:
     * - Type: WARRIOR
     * - Stats: 150 max health, 40 attack power, 30 defense, 0 max mana
     * - Attack: MeleeAttackStrategy
     * - Defense: HeavyArmorDefenseStrategy
     *
     * Use Character.builder() to construct the character.
     *
     * @param name The warrior's name
     * @return A fully configured Warrior character
     */
    public static Character createWarrior(String name) {
        return Character.builder()
            .name(name)
            .type(CharacterType.WARRIOR)
            .stats(CharacterStats.create(150, 40, 30, 0))
            .attackStrategy(new MeleeAttackStrategy())
            .defenseStrategy(new HeavyArmorDefenseStrategy())
            .build();
    }

    /**
     * TODO 2b: Implement createMage()
     *
     * Create a Mage character with:
     * - Type: MAGE
     * - Stats: 80 max health, 60 attack power, 10 defense, 100 max mana
     * - Attack: MagicAttackStrategy
     * - Defense: StandardDefenseStrategy
     *
     * @param name The mage's name
     * @return A fully configured Mage character
     */
    public static Character createMage(String name) {
        return Character.builder()
            .name(name)
            .type(CharacterType.MAGE)
            .stats(CharacterStats.create(80, 60, 10, 100))
            .attackStrategy(new MagicAttackStrategy())
            .defenseStrategy(new StandardDefenseStrategy())
            .build();
    }

    /**
     * TODO 2c: Implement createArcher()
     *
     * Create an Archer character with:
     * - Type: ARCHER
     * - Stats: 100 max health, 50 attack power, 15 defense, 20 max mana
     * - Attack: RangedAttackStrategy
     * - Defense: StandardDefenseStrategy
     *
     * @param name The archer's name
     * @return A fully configured Archer character
     */
    public static Character createArcher(String name) {
        return Character.builder()
            .name(name)
            .type(CharacterType.ARCHER)
            .stats(CharacterStats.create(100, 50, 15, 20))
            .attackStrategy(new RangedAttackStrategy())
            .defenseStrategy(new StandardDefenseStrategy())
            .build();
    }

    /**
     * TODO 2d: Implement createRogue()
     *
     * Create a Rogue character with:
     * - Type: ROGUE
     * - Stats: 90 max health, 55 attack power, 20 defense, 30 max mana
     * - Attack: MeleeAttackStrategy
     * - Defense: StandardDefenseStrategy
     *
     * @param name The rogue's name
     * @return A fully configured Rogue character
     */
    public static Character createRogue(String name) {
        return Character.builder()
            .name(name)
            .type(CharacterType.ROGUE)
            .stats(CharacterStats.create(90, 55, 20, 30))
            .attackStrategy(new MeleeAttackStrategy())
            .defenseStrategy(new StandardDefenseStrategy())
            .build();
    }

    /**
     * TODO 2e: Implement createCharacter()
     *
     * Factory method that creates a character of the specified type.
     * This demonstrates the Factory Method pattern - one method that
     * decides which concrete creation method to call.
     *
     * Use a switch expression (Java 21 feature!) to delegate to the
     * appropriate creation method based on the type.
     *
     * @param name The character's name
     * @param type The type of character to create
     * @return A character of the specified type
     * @throws IllegalArgumentException if type is null
     */
    public static Character createCharacter(String name, CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("Character type cannot be null");
        }

        return switch(type) {
            case WARRIOR -> createWarrior(name);
            case MAGE -> createMage(name);
            case ARCHER -> createArcher(name);
            case ROGUE -> createRogue(name);
        };
    }
}
