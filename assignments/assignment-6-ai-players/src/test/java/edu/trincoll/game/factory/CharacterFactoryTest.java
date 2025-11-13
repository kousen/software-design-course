package edu.trincoll.game.factory;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CharacterFactory Tests")
class CharacterFactoryTest {

    @Nested
    @DisplayName("createWarrior() Tests")
    class CreateWarriorTests {

        @Test
        @DisplayName("Should create warrior with correct type")
        void shouldCreateWarriorWithCorrectType() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getType()).isEqualTo(CharacterType.WARRIOR);
        }

        @Test
        @DisplayName("Should create warrior with correct name")
        void shouldCreateWarriorWithCorrectName() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getName()).isEqualTo("Conan");
        }

        @Test
        @DisplayName("Should create warrior with correct stats")
        void shouldCreateWarriorWithCorrectStats() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getStats().maxHealth()).isEqualTo(150);
            assertThat(warrior.getStats().health()).isEqualTo(150);
            assertThat(warrior.getStats().attackPower()).isEqualTo(40);
            assertThat(warrior.getStats().defense()).isEqualTo(30);
            assertThat(warrior.getStats().maxMana()).isEqualTo(0);
            assertThat(warrior.getStats().mana()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should create warrior with melee attack strategy")
        void shouldCreateWarriorWithMeleeAttack() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getAttackStrategy()).isInstanceOf(MeleeAttackStrategy.class);
        }

        @Test
        @DisplayName("Should create warrior with heavy armor defense strategy")
        void shouldCreateWarriorWithHeavyArmorDefense() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            assertThat(warrior.getDefenseStrategy()).isInstanceOf(HeavyArmorDefenseStrategy.class);
        }
    }

    @Nested
    @DisplayName("createMage() Tests")
    class CreateMageTests {

        @Test
        @DisplayName("Should create mage with correct type")
        void shouldCreateMageWithCorrectType() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getType()).isEqualTo(CharacterType.MAGE);
        }

        @Test
        @DisplayName("Should create mage with correct name")
        void shouldCreateMageWithCorrectName() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getName()).isEqualTo("Gandalf");
        }

        @Test
        @DisplayName("Should create mage with correct stats")
        void shouldCreateMageWithCorrectStats() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getStats().maxHealth()).isEqualTo(80);
            assertThat(mage.getStats().health()).isEqualTo(80);
            assertThat(mage.getStats().attackPower()).isEqualTo(60);
            assertThat(mage.getStats().defense()).isEqualTo(10);
            assertThat(mage.getStats().maxMana()).isEqualTo(100);
            assertThat(mage.getStats().mana()).isEqualTo(100);
        }

        @Test
        @DisplayName("Should create mage with magic attack strategy")
        void shouldCreateMageWithMagicAttack() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getAttackStrategy()).isInstanceOf(MagicAttackStrategy.class);
        }

        @Test
        @DisplayName("Should create mage with standard defense strategy")
        void shouldCreateMageWithStandardDefense() {
            Character mage = CharacterFactory.createMage("Gandalf");

            assertThat(mage.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }
    }

    @Nested
    @DisplayName("createArcher() Tests")
    class CreateArcherTests {

        @Test
        @DisplayName("Should create archer with correct type")
        void shouldCreateArcherWithCorrectType() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getType()).isEqualTo(CharacterType.ARCHER);
        }

        @Test
        @DisplayName("Should create archer with correct name")
        void shouldCreateArcherWithCorrectName() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getName()).isEqualTo("Legolas");
        }

        @Test
        @DisplayName("Should create archer with correct stats")
        void shouldCreateArcherWithCorrectStats() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getStats().maxHealth()).isEqualTo(100);
            assertThat(archer.getStats().health()).isEqualTo(100);
            assertThat(archer.getStats().attackPower()).isEqualTo(50);
            assertThat(archer.getStats().defense()).isEqualTo(15);
            assertThat(archer.getStats().maxMana()).isEqualTo(20);
            assertThat(archer.getStats().mana()).isEqualTo(20);
        }

        @Test
        @DisplayName("Should create archer with ranged attack strategy")
        void shouldCreateArcherWithRangedAttack() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getAttackStrategy()).isInstanceOf(RangedAttackStrategy.class);
        }

        @Test
        @DisplayName("Should create archer with standard defense strategy")
        void shouldCreateArcherWithStandardDefense() {
            Character archer = CharacterFactory.createArcher("Legolas");

            assertThat(archer.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }
    }

    @Nested
    @DisplayName("createRogue() Tests")
    class CreateRogueTests {

        @Test
        @DisplayName("Should create rogue with correct type")
        void shouldCreateRogueWithCorrectType() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getType()).isEqualTo(CharacterType.ROGUE);
        }

        @Test
        @DisplayName("Should create rogue with correct name")
        void shouldCreateRogueWithCorrectName() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getName()).isEqualTo("Assassin");
        }

        @Test
        @DisplayName("Should create rogue with correct stats")
        void shouldCreateRogueWithCorrectStats() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getStats().maxHealth()).isEqualTo(90);
            assertThat(rogue.getStats().health()).isEqualTo(90);
            assertThat(rogue.getStats().attackPower()).isEqualTo(55);
            assertThat(rogue.getStats().defense()).isEqualTo(20);
            assertThat(rogue.getStats().maxMana()).isEqualTo(30);
            assertThat(rogue.getStats().mana()).isEqualTo(30);
        }

        @Test
        @DisplayName("Should create rogue with melee attack strategy")
        void shouldCreateRogueWithMeleeAttack() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getAttackStrategy()).isInstanceOf(MeleeAttackStrategy.class);
        }

        @Test
        @DisplayName("Should create rogue with standard defense strategy")
        void shouldCreateRogueWithStandardDefense() {
            Character rogue = CharacterFactory.createRogue("Assassin");

            assertThat(rogue.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }
    }

    @Nested
    @DisplayName("createCharacter() Factory Method Tests")
    class CreateCharacterTests {

        @Test
        @DisplayName("Should create warrior when type is WARRIOR")
        void shouldCreateWarriorWhenTypeIsWarrior() {
            Character character = CharacterFactory.createCharacter("Hero", CharacterType.WARRIOR);

            assertThat(character.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(character.getName()).isEqualTo("Hero");
            assertThat(character.getStats().maxHealth()).isEqualTo(150);
        }

        @Test
        @DisplayName("Should create mage when type is MAGE")
        void shouldCreateMageWhenTypeIsMage() {
            Character character = CharacterFactory.createCharacter("Wizard", CharacterType.MAGE);

            assertThat(character.getType()).isEqualTo(CharacterType.MAGE);
            assertThat(character.getName()).isEqualTo("Wizard");
            assertThat(character.getStats().maxHealth()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should create archer when type is ARCHER")
        void shouldCreateArcherWhenTypeIsArcher() {
            Character character = CharacterFactory.createCharacter("Bowman", CharacterType.ARCHER);

            assertThat(character.getType()).isEqualTo(CharacterType.ARCHER);
            assertThat(character.getName()).isEqualTo("Bowman");
            assertThat(character.getStats().maxHealth()).isEqualTo(100);
        }

        @Test
        @DisplayName("Should create rogue when type is ROGUE")
        void shouldCreateRogueWhenTypeIsRogue() {
            Character character = CharacterFactory.createCharacter("Thief", CharacterType.ROGUE);

            assertThat(character.getType()).isEqualTo(CharacterType.ROGUE);
            assertThat(character.getName()).isEqualTo("Thief");
            assertThat(character.getStats().maxHealth()).isEqualTo(90);
        }

        @Test
        @DisplayName("Should throw exception when type is null")
        void shouldThrowExceptionWhenTypeIsNull() {
            assertThatThrownBy(() -> CharacterFactory.createCharacter("Hero", null))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Integration Tests - Character Behavior")
    class IntegrationTests {

        @Test
        @DisplayName("Created warrior should be able to attack")
        void createdWarriorShouldAttack() {
            Character warrior = CharacterFactory.createWarrior("Conan");
            Character target = CharacterFactory.createMage("Enemy");

            int damage = warrior.attack(target);

            // Warrior: 40 attack * 1.2 = 48
            assertThat(damage).isEqualTo(48);
        }

        @Test
        @DisplayName("Created mage should be able to cast magic")
        void createdMageShouldCastMagic() {
            Character mage = CharacterFactory.createMage("Gandalf");
            Character target = CharacterFactory.createWarrior("Enemy");

            int damage = mage.attack(target);

            // Mage: 60 attack + (100 mana / 10) = 60 + 10 = 70
            assertThat(damage).isEqualTo(70);
            assertThat(mage.getStats().mana()).isEqualTo(90); // Used 10 mana
        }

        @Test
        @DisplayName("Created archer should deal ranged damage")
        void createdArcherShouldDealRangedDamage() {
            Character archer = CharacterFactory.createArcher("Legolas");
            Character target = CharacterFactory.createWarrior("Enemy");

            int damage = archer.attack(target);

            // Archer: 50 attack * 0.8 = 40 (no critical on full health)
            assertThat(damage).isEqualTo(40);
        }

        @Test
        @DisplayName("Created characters should be able to defend")
        void createdCharactersShouldDefend() {
            Character warrior = CharacterFactory.createWarrior("Conan");

            int actualDamage = warrior.defend(100);

            // Heavy armor: 100 - 30 = 70
            assertThat(actualDamage).isEqualTo(70);
        }
    }
}
