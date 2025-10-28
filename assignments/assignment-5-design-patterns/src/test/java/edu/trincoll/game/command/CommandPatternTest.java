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
    @DisplayName("Attack Command")
    class AttackCommandTests {

        private Character attacker;
        private Character target;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("Attacker");
            target = CharacterFactory.createWarrior("Target");
        }

        @Test
        @DisplayName("TODO 4a: Attack command executes attack")
        void testAttackCommandExecute() {
            int initialHealth = target.getStats().health();

            AttackCommand command = new AttackCommand(attacker, target);
            command.execute();

            assertThat(target.getStats().health())
                .as("Target should take damage")
                .isLessThan(initialHealth);
        }

        @Test
        @DisplayName("TODO 4a: Attack command can be undone")
        void testAttackCommandUndo() {
            int initialHealth = target.getStats().health();

            AttackCommand command = new AttackCommand(attacker, target);
            command.execute();

            int healthAfterAttack = target.getStats().health();
            assertThat(healthAfterAttack).isLessThan(initialHealth);

            command.undo();

            assertThat(target.getStats().health())
                .as("Undo should restore health")
                .isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Attack command has description")
        void testAttackCommandDescription() {
            AttackCommand command = new AttackCommand(attacker, target);

            assertThat(command.getDescription())
                .contains(attacker.getName())
                .contains(target.getName());
        }
    }

    @Nested
    @DisplayName("Heal Command")
    class HealCommandTests {

        private Character character;

        @BeforeEach
        void setUp() {
            character = CharacterFactory.createWarrior("Hero");
            character.takeDamage(50); // Damage the character first
        }

        @Test
        @DisplayName("TODO 4b: Heal command restores health")
        void testHealCommandExecute() {
            int initialHealth = character.getStats().health();

            HealCommand command = new HealCommand(character, 30);
            command.execute();

            assertThat(character.getStats().health())
                .as("Character should be healed")
                .isGreaterThan(initialHealth);
        }

        @Test
        @DisplayName("TODO 4b: Heal command can be undone")
        void testHealCommandUndo() {
            int healthBeforeHeal = character.getStats().health();

            HealCommand command = new HealCommand(character, 30);
            command.execute();

            int healthAfterHeal = character.getStats().health();
            assertThat(healthAfterHeal).isGreaterThan(healthBeforeHeal);

            command.undo();

            assertThat(character.getStats().health())
                .as("Undo should remove healing")
                .isEqualTo(healthBeforeHeal);
        }

        @Test
        @DisplayName("TODO 4b: Heal command respects max health")
        void testHealCommandMaxHealth() {
            int maxHealth = character.getStats().maxHealth();

            HealCommand command = new HealCommand(character, 1000); // Overheal
            command.execute();

            assertThat(character.getStats().health())
                .as("Healing should not exceed max health")
                .isEqualTo(maxHealth);
        }

        @Test
        @DisplayName("Heal command has description")
        void testHealCommandDescription() {
            HealCommand command = new HealCommand(character, 30);

            assertThat(command.getDescription())
                .contains(character.getName())
                .contains("30");
        }
    }

    @Nested
    @DisplayName("Command Invoker")
    class CommandInvokerTests {

        private CommandInvoker invoker;
        private Character attacker;
        private Character target;

        @BeforeEach
        void setUp() {
            invoker = new CommandInvoker();
            attacker = CharacterFactory.createWarrior("Attacker");
            target = CharacterFactory.createWarrior("Target");
        }

        @Test
        @DisplayName("TODO 4c: Invoker executes commands and stores history")
        void testInvokerExecuteCommand() {
            AttackCommand command = new AttackCommand(attacker, target);

            invoker.executeCommand(command);

            assertThat(invoker.getCommandHistory())
                .as("Command should be in history")
                .hasSize(1)
                .contains(command);
        }

        @Test
        @DisplayName("TODO 4c: Invoker can undo last command")
        void testInvokerUndoLastCommand() {
            int initialHealth = target.getStats().health();

            AttackCommand command = new AttackCommand(attacker, target);
            invoker.executeCommand(command);

            int healthAfterAttack = target.getStats().health();
            assertThat(healthAfterAttack).isLessThan(initialHealth);

            invoker.undoLastCommand();

            assertThat(target.getStats().health())
                .as("Undo should restore state")
                .isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("TODO 4c: Invoker handles multiple commands")
        void testInvokerMultipleCommands() {
            AttackCommand attack1 = new AttackCommand(attacker, target);
            AttackCommand attack2 = new AttackCommand(attacker, target);
            HealCommand heal = new HealCommand(target, 20);

            invoker.executeCommand(attack1);
            invoker.executeCommand(attack2);
            invoker.executeCommand(heal);

            assertThat(invoker.getCommandHistory()).hasSize(3);
        }

        @Test
        @DisplayName("TODO 4c: Invoker undoes commands in reverse order")
        void testInvokerUndoOrder() {
            int initialHealth = target.getStats().health();

            AttackCommand attack = new AttackCommand(attacker, target);
            HealCommand heal = new HealCommand(target, 10);

            invoker.executeCommand(attack);
            invoker.executeCommand(heal);

            // Undo heal first
            invoker.undoLastCommand();
            int healthAfterUndoHeal = target.getStats().health();

            // Then undo attack
            invoker.undoLastCommand();
            int healthAfterUndoAttack = target.getStats().health();

            assertThat(healthAfterUndoAttack)
                .as("Should be back to initial state")
                .isEqualTo(initialHealth);
        }

        @Test
        @DisplayName("Invoker can clear history")
        void testInvokerClearHistory() {
            invoker.executeCommand(new AttackCommand(attacker, target));
            invoker.executeCommand(new AttackCommand(attacker, target));

            assertThat(invoker.getCommandHistory()).hasSize(2);

            invoker.clearHistory();

            assertThat(invoker.getCommandHistory()).isEmpty();
        }

        @Test
        @DisplayName("TODO 4c: Invoker handles undo when history is empty")
        void testInvokerUndoEmptyHistory() {
            assertThatCode(() -> invoker.undoLastCommand())
                .as("Undo on empty history should not throw")
                .doesNotThrowAnyException();
        }
    }
}
