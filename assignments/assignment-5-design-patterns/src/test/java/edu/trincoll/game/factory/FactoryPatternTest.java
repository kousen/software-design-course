package edu.trincoll.game.factory;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.strategy.HeavyArmorDefenseStrategy;
import edu.trincoll.game.strategy.MagicAttackStrategy;
import edu.trincoll.game.strategy.MeleeAttackStrategy;
import edu.trincoll.game.strategy.RangedAttackStrategy;
import edu.trincoll.game.strategy.StandardDefenseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Factory Method Pattern Tests")
class FactoryPatternTest {

    @Nested
    @DisplayName("Specific Character Creation")
    class SpecificCharacterTests {

        @Test
        @DisplayName("TODO 2a: Factory creates Warrior with correct stats and strategies")
        void testCreateWarrior() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getName()).isEqualTo("Conan");
            assertThat(warrior.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(warrior.getStats().maxHealth()).isEqualTo(150);
            assertThat(warrior.getStats().attackPower()).isEqualTo(40);
            assertThat(warrior.getStats().defense()).isEqualTo(30);
            assertThat(warrior.getStats().maxMana()).isEqualTo(0);
            assertThat(warrior.getAttackStrategy()).isInstanceOf(MeleeAttackStrategy.class);
            assertThat(warrior.getDefenseStrategy()).isInstanceOf(HeavyArmorDefenseStrategy.class);
        }

        @Test
        @DisplayName("TODO 2b: Factory creates Mage with correct stats and strategies")
        void testCreateMage() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getName()).isEqualTo("Gandalf");
            assertThat(mage.getType()).isEqualTo(CharacterType.MAGE);
            assertThat(mage.getStats().maxHealth()).isEqualTo(80);
            assertThat(mage.getStats().attackPower()).isEqualTo(60);
            assertThat(mage.getStats().defense()).isEqualTo(10);
            assertThat(mage.getStats().maxMana()).isEqualTo(100);
            assertThat(mage.getAttackStrategy()).isInstanceOf(MagicAttackStrategy.class);
            assertThat(mage.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }

        @Test
        @DisplayName("TODO 2c: Factory creates Archer with correct stats and strategies")
        void testCreateArcher() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getName()).isEqualTo("Legolas");
            assertThat(archer.getType()).isEqualTo(CharacterType.ARCHER);
            assertThat(archer.getStats().maxHealth()).isEqualTo(100);
            assertThat(archer.getStats().attackPower()).isEqualTo(50);
            assertThat(archer.getStats().defense()).isEqualTo(15);
            assertThat(archer.getStats().maxMana()).isEqualTo(20);
            assertThat(archer.getAttackStrategy()).isInstanceOf(RangedAttackStrategy.class);
            assertThat(archer.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }

        @Test
        @DisplayName("TODO 2d: Factory creates Rogue with correct stats and strategies")
        void testCreateRogue() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getName()).isEqualTo("Assassin");
            assertThat(rogue.getType()).isEqualTo(CharacterType.ROGUE);
            assertThat(rogue.getStats().maxHealth()).isEqualTo(90);
            assertThat(rogue.getStats().attackPower()).isEqualTo(55);
            assertThat(rogue.getStats().defense()).isEqualTo(20);
            assertThat(rogue.getStats().maxMana()).isEqualTo(30);
            assertThat(rogue.getAttackStrategy()).isInstanceOf(MeleeAttackStrategy.class);
            assertThat(rogue.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }
    }

    @Nested
    @DisplayName("Generic Factory Method")
    class GenericFactoryMethodTests {

        @Test
        @DisplayName("TODO 2e: Generic factory method creates Warrior")
        void testGenericFactoryWarrior() {
            Character character = CharacterFactory.createCharacter("Hero", CharacterType.WARRIOR);

            assertThat(character).isNotNull();
            assertThat(character.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(character.getName()).isEqualTo("Hero");
        }

        @Test
        @DisplayName("TODO 2e: Generic factory method creates Mage")
        void testGenericFactoryMage() {
            Character character = CharacterFactory.createCharacter("Wizard", CharacterType.MAGE);

            assertThat(character).isNotNull();
            assertThat(character.getType()).isEqualTo(CharacterType.MAGE);
        }

        @Test
        @DisplayName("TODO 2e: Generic factory method creates Archer")
        void testGenericFactoryArcher() {
            Character character = CharacterFactory.createCharacter("Ranger", CharacterType.ARCHER);

            assertThat(character).isNotNull();
            assertThat(character.getType()).isEqualTo(CharacterType.ARCHER);
        }

        @Test
        @DisplayName("TODO 2e: Generic factory method creates Rogue")
        void testGenericFactoryRogue() {
            Character character = CharacterFactory.createCharacter("Thief", CharacterType.ROGUE);

            assertThat(character).isNotNull();
            assertThat(character.getType()).isEqualTo(CharacterType.ROGUE);
        }

        @Test
        @DisplayName("TODO 2e: Generic factory method handles null type")
        void testGenericFactoryNullType() {
            assertThatThrownBy(() ->
                CharacterFactory.createCharacter("Test", null)
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Open-Closed Principle Demonstration")
    class OpenClosedPrincipleTests {

        @Test
        @DisplayName("Adding new character type doesn't break existing code")
        void testOCPCompliance() {
            // Create all existing types
            Character warrior = CharacterFactory.createWarrior("W");
            Character mage = CharacterFactory.createMage("M");
            Character archer = CharacterFactory.createArcher("A");
            Character rogue = CharacterFactory.createRogue("R");

            // All should be valid
            assertThat(warrior).isNotNull();
            assertThat(mage).isNotNull();
            assertThat(archer).isNotNull();
            assertThat(rogue).isNotNull();

            // Generic factory should handle all types
            assertThat(CharacterFactory.createCharacter("Test", CharacterType.WARRIOR)).isNotNull();
            assertThat(CharacterFactory.createCharacter("Test", CharacterType.MAGE)).isNotNull();
            assertThat(CharacterFactory.createCharacter("Test", CharacterType.ARCHER)).isNotNull();
            assertThat(CharacterFactory.createCharacter("Test", CharacterType.ROGUE)).isNotNull();
        }
    }
}
