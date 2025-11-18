package edu.trincoll.game.command;

import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Command Pattern Tests")
class CommandPatternTest {

    @Nested
    @DisplayName("AttackCommand Tests")
    class AttackCommandTests {

        private Character attacker;
        private Character target;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("Attacker");
            target = CharacterFactory.createMage("Target");
        }

        @Test
        @DisplayName("Should execute attack and reduce target health")
        void shouldExecuteAttack() {
            AttackCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            command.execute();

            assertThat(target.getStats().health()).isLessThan(initialHealth);
        }

        @Test
        @DisplayName("Should calculate correct damage based on attacker and target")
        void shouldCalculateCorrectDamage() {
            AttackCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            command.execute();

            // Warrior: 40 * 1.2 = 48 damage
            // Mage defense: 48 - (10/2) = 48 - 5 = 43 actual damage
            // Expected health: 80 - 43 = 37
            assertThat(target.getStats().health()).isEqualTo(37);
        }

        @Test
        @DisplayName("Should undo attack by restoring target health")
        void shouldUndoAttack() {
            AttackCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            command.execute();
            command.undo();

            assertThat(target.getStats().health()).isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Should handle multiple execute and undo cycles")
        void shouldHandleMultipleExecuteUndoCycles() {
            AttackCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            // Execute, undo, execute again
            command.execute();
            int afterFirstAttack = target.getStats().health();
            command.undo();
            assertThat(target.getStats().health()).isEqualTo(initialHealth);

            command.execute();
            assertThat(target.getStats().health()).isEqualTo(afterFirstAttack);
        }

        @Test
        @DisplayName("Should have meaningful description")
        void shouldHaveMeaningfulDescription() {
            AttackCommand command = new AttackCommand(attacker, target);

            String description = command.getDescription();

            assertThat(description)
                .contains("Attacker")
                .contains("attacks")
                .contains("Target");
        }

        @Test
        @DisplayName("Should work with different character types")
        void shouldWorkWithDifferentCharacterTypes() {
            Character archer = CharacterFactory.createArcher("Legolas");
            Character warrior = CharacterFactory.createWarrior("Conan");

            AttackCommand command = new AttackCommand(archer, warrior);
            int initialHealth = warrior.getStats().health();

            command.execute();

            assertThat(warrior.getStats().health()).isLessThan(initialHealth);
        }
    }

    @Nested
    @DisplayName("HealCommand Tests")
    class HealCommandTests {

        private Character healer;
        private Character wounded;

        @BeforeEach
        void setUp() {
            healer = CharacterFactory.createMage("Healer");
            wounded = CharacterFactory.createWarrior("Wounded");
            // Reduce health to test healing
            wounded.setHealth(50);
        }

        @Test
        @DisplayName("Should execute heal and increase target health")
        void shouldExecuteHeal() {
            HealCommand command = new HealCommand(wounded, 30);
            int initialHealth = wounded.getStats().health();

            command.execute();

            assertThat(wounded.getStats().health()).isGreaterThan(initialHealth);
        }

        @Test
        @DisplayName("Should heal by specified amount")
        void shouldHealBySpecifiedAmount() {
            HealCommand command = new HealCommand(wounded, 30);

            command.execute();

            // 50 + 30 = 80
            assertThat(wounded.getStats().health()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should not heal beyond max health")
        void shouldNotHealBeyondMaxHealth() {
            HealCommand command = new HealCommand(wounded, 200);

            command.execute();

            assertThat(wounded.getStats().health()).isEqualTo(wounded.getStats().maxHealth());
        }

        @Test
        @DisplayName("Should undo heal by restoring previous health")
        void shouldUndoHeal() {
            HealCommand command = new HealCommand(wounded, 30);
            int initialHealth = wounded.getStats().health();

            command.execute();
            command.undo();

            assertThat(wounded.getStats().health()).isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Should correctly undo when heal was capped at max health")
        void shouldUndoWhenHealWasCapped() {
            // Wounded at 50/150 HP
            HealCommand command = new HealCommand(wounded, 200);
            int initialHealth = wounded.getStats().health(); // 50

            command.execute();
            // Should be at max health (150)
            assertThat(wounded.getStats().health()).isEqualTo(150);

            command.undo();
            // Should be back at 50
            assertThat(wounded.getStats().health()).isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Should handle multiple execute and undo cycles")
        void shouldHandleMultipleExecuteUndoCycles() {
            HealCommand command = new HealCommand(wounded, 30);
            int initialHealth = wounded.getStats().health();

            command.execute();
            command.undo();
            assertThat(wounded.getStats().health()).isEqualTo(initialHealth);

            command.execute();
            assertThat(wounded.getStats().health()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should have meaningful description")
        void shouldHaveMeaningfulDescription() {
            HealCommand command = new HealCommand(wounded, 30);

            String description = command.getDescription();

            assertThat(description)
                .contains("Heal")
                .contains("Wounded")
                .contains("30");
        }

        @Test
        @DisplayName("Should work with self-heal")
        void shouldWorkWithSelfHeal() {
            HealCommand command = new HealCommand(healer, 20);
            healer.setHealth(50);

            command.execute();

            assertThat(healer.getStats().health()).isEqualTo(70);
        }
    }

    @Nested
    @DisplayName("CommandInvoker Tests")
    class CommandInvokerTests {

        private CommandInvoker invoker;
        private Character attacker;
        private Character target;

        @BeforeEach
        void setUp() {
            invoker = new CommandInvoker();
            attacker = CharacterFactory.createWarrior("Attacker");
            target = CharacterFactory.createMage("Target");
        }

        @Test
        @DisplayName("Should execute command and add to history")
        void shouldExecuteCommandAndAddToHistory() {
            GameCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            invoker.executeCommand(command);

            assertThat(target.getStats().health()).isLessThan(initialHealth);
        }

        @Test
        @DisplayName("Should undo last command")
        void shouldUndoLastCommand() {
            GameCommand command = new AttackCommand(attacker, target);
            int initialHealth = target.getStats().health();

            invoker.executeCommand(command);
            invoker.undoLastCommand();

            assertThat(target.getStats().health()).isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Should undo commands in reverse order (LIFO)")
        void shouldUndoCommandsInReverseOrder() {
            Character healer = CharacterFactory.createMage("Healer");
            target.setHealth(50);

            // Execute three commands: heal, attack, heal
            GameCommand heal1 = new HealCommand(target, 20);
            GameCommand attack = new AttackCommand(attacker, target);
            GameCommand heal2 = new HealCommand(target, 10);

            invoker.executeCommand(heal1); // 50 -> 70
            invoker.executeCommand(attack); // 70 -> less
            int afterAttack = target.getStats().health();
            invoker.executeCommand(heal2); // +10

            // Undo should reverse in order: heal2, attack, heal1
            invoker.undoLastCommand(); // Undo heal2
            assertThat(target.getStats().health()).isEqualTo(afterAttack);

            invoker.undoLastCommand(); // Undo attack
            assertThat(target.getStats().health()).isEqualTo(70);

            invoker.undoLastCommand(); // Undo heal1
            assertThat(target.getStats().health()).isEqualTo(50);
        }

        @Test
        @DisplayName("Should handle undo when history is empty")
        void shouldHandleUndoWhenHistoryIsEmpty() {
            // Should not throw exception
            assertThatCode(() -> invoker.undoLastCommand())
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Should handle multiple undos")
        void shouldHandleMultipleUndos() {
            target.setHealth(80);

            GameCommand attack1 = new AttackCommand(attacker, target);
            GameCommand attack2 = new AttackCommand(attacker, target);

            invoker.executeCommand(attack1);
            invoker.executeCommand(attack2);

            invoker.undoLastCommand();
            invoker.undoLastCommand();

            assertThat(target.getStats().health()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should maintain command history across multiple operations")
        void shouldMaintainCommandHistory() {
            target.setHealth(60);  // Mage max health is 80

            GameCommand heal = new HealCommand(target, 20);
            GameCommand attack = new AttackCommand(attacker, target);

            invoker.executeCommand(heal);  // 60 + 20 = 80
            invoker.executeCommand(attack);  // 80 - 43 = 37
            invoker.undoLastCommand();  // 37 + 43 = 80

            // After undoing attack, heal should still be in effect
            assertThat(target.getStats().health()).isEqualTo(80);
        }
    }

    @Nested
    @DisplayName("Integration Tests - Command Pattern Benefits")
    class IntegrationTests {

        @Test
        @DisplayName("Should enable complex battle scenarios with undo")
        void shouldEnableComplexBattleWithUndo() {
            CommandInvoker invoker = new CommandInvoker();
            Character warrior = CharacterFactory.createWarrior("Hero");
            Character mage = CharacterFactory.createMage("Enemy");

            // Record initial state
            int warriorInitialHealth = warrior.getStats().health();
            int mageInitialHealth = mage.getStats().health();

            // Execute a series of commands
            invoker.executeCommand(new AttackCommand(warrior, mage));
            invoker.executeCommand(new AttackCommand(mage, warrior));
            invoker.executeCommand(new HealCommand(warrior, 30));

            // Both characters should have changed health
            assertThat(warrior.getStats().health()).isNotEqualTo(warriorInitialHealth);
            assertThat(mage.getStats().health()).isNotEqualTo(mageInitialHealth);

            // Undo all commands
            invoker.undoLastCommand();
            invoker.undoLastCommand();
            invoker.undoLastCommand();

            // Should be back to initial state
            assertThat(warrior.getStats().health()).isEqualTo(warriorInitialHealth);
            assertThat(mage.getStats().health()).isEqualTo(mageInitialHealth);
        }

        @Test
        @DisplayName("Should support command descriptions for logging")
        void shouldSupportCommandDescriptionsForLogging() {
            Character warrior = CharacterFactory.createWarrior("Hero");
            Character mage = CharacterFactory.createMage("Enemy");

            GameCommand attack = new AttackCommand(warrior, mage);
            GameCommand heal = new HealCommand(warrior, 20);

            assertThat(attack.getDescription()).isNotEmpty();
            assertThat(heal.getDescription()).isNotEmpty();
        }
    }
}
