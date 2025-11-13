package edu.trincoll.game.player;

import edu.trincoll.game.command.AttackCommand;
import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.command.HealCommand;
import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RuleBasedPlayer Tests")
class RuleBasedPlayerTest {

    private RuleBasedPlayer player;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        player = new RuleBasedPlayer();
        gameState = GameState.initial();
    }

    @Nested
    @DisplayName("Rule 1: Self-Preservation (HP < 30%)")
    class SelfPreservationRule {

        @Test
        @DisplayName("Should heal self when health is below 30%")
        void shouldHealSelfWhenHealthLow() {
            Character self = CharacterFactory.createWarrior("Self");
            self.setHealth(40); // 40/150 = 26.6% < 30%

            Character ally = CharacterFactory.createMage("Ally");
            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, ally),
                List.of(enemy),
                gameState
            );

            assertThat(command).isInstanceOf(HealCommand.class);
            HealCommand healCommand = (HealCommand) command;
            assertThat(healCommand.getDescription()).contains("Self");
        }

        @Test
        @DisplayName("Should heal self at exactly 30% health threshold")
        void shouldNotHealAtExactly30Percent() {
            Character self = CharacterFactory.createWarrior("Self");
            self.setHealth(45); // 45/150 = 30% (not < 30%)

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self),
                List.of(enemy),
                gameState
            );

            // At exactly 30%, should NOT heal (rule requires < 30%)
            assertThat(command).isInstanceOf(AttackCommand.class);
        }

        @Test
        @DisplayName("Should heal self just under 30% threshold")
        void shouldHealJustUnder30Percent() {
            Character self = CharacterFactory.createWarrior("Self");
            self.setHealth(44); // 44/150 = 29.3% < 30%

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self),
                List.of(enemy),
                gameState
            );

            assertThat(command).isInstanceOf(HealCommand.class);
        }

        @Test
        @DisplayName("Should prioritize self-heal over helping allies")
        void shouldPrioritizeSelfHealOverAllies() {
            Character self = CharacterFactory.createWarrior("Self");
            self.setHealth(40); // 26.6% < 30%

            Character dyingAlly = CharacterFactory.createMage("DyingAlly");
            dyingAlly.setHealth(10); // 10/80 = 12.5% < 20% (would trigger ally heal)

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, dyingAlly),
                List.of(enemy),
                gameState
            );

            // Should heal self, not ally
            assertThat(command).isInstanceOf(HealCommand.class);
            HealCommand healCommand = (HealCommand) command;
            assertThat(healCommand.getDescription()).contains("Self");
        }
    }

    @Nested
    @DisplayName("Rule 2: Help Weakest Ally (Ally HP < 20%)")
    class HelpWeakestAllyRule {

        @Test
        @DisplayName("Should heal ally when their health is below 20%")
        void shouldHealAllyWhenHealthCritical() {
            Character self = CharacterFactory.createWarrior("Self");
            // Self is healthy

            Character weakAlly = CharacterFactory.createMage("WeakAlly");
            weakAlly.setHealth(15); // 15/80 = 18.75% < 20%

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, weakAlly),
                List.of(enemy),
                gameState
            );

            assertThat(command).isInstanceOf(HealCommand.class);
            HealCommand healCommand = (HealCommand) command;
            assertThat(healCommand.getDescription()).contains("WeakAlly");
        }

        @Test
        @DisplayName("Should heal weakest ally when multiple allies are critical")
        void shouldHealWeakestAllyWhenMultipleCritical() {
            Character self = CharacterFactory.createWarrior("Self");

            Character ally1 = CharacterFactory.createMage("Ally1");
            ally1.setHealth(15); // 15/80 = 18.75%

            Character ally2 = CharacterFactory.createArcher("Ally2");
            ally2.setHealth(10); // 10/100 = 10% (weaker)

            Character enemy = CharacterFactory.createRogue("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, ally1, ally2),
                List.of(enemy),
                gameState
            );

            assertThat(command).isInstanceOf(HealCommand.class);
            HealCommand healCommand = (HealCommand) command;
            // Should heal Ally2 (10 HP is less than 15 HP)
            assertThat(healCommand.getDescription()).contains("Ally2");
        }

        @Test
        @DisplayName("Should not heal ally at exactly 20% health")
        void shouldNotHealAllyAtExactly20Percent() {
            Character self = CharacterFactory.createWarrior("Self");

            Character ally = CharacterFactory.createMage("Ally");
            ally.setHealth(16); // 16/80 = 20% (not < 20%)

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, ally),
                List.of(enemy),
                gameState
            );

            // Should attack instead
            assertThat(command).isInstanceOf(AttackCommand.class);
        }

        @Test
        @DisplayName("Should not heal self when checking for allies")
        void shouldNotConsiderSelfAsAlly() {
            Character self = CharacterFactory.createWarrior("Self");
            // Self is at 40% health (not critical for self-heal)
            self.setHealth(60);

            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self), // Only self in allies list
                List.of(enemy),
                gameState
            );

            // Should attack, not heal self (40% is not < 30%)
            assertThat(command).isInstanceOf(AttackCommand.class);
        }
    }

    @Nested
    @DisplayName("Rule 3: Attack Weakest Enemy")
    class AttackWeakestEnemyRule {

        @Test
        @DisplayName("Should attack when no healing is needed")
        void shouldAttackWhenNoHealingNeeded() {
            Character self = CharacterFactory.createWarrior("Self");
            Character ally = CharacterFactory.createMage("Ally");
            Character enemy = CharacterFactory.createArcher("Enemy");

            GameCommand command = player.decideAction(
                self,
                List.of(self, ally),
                List.of(enemy),
                gameState
            );

            assertThat(command).isInstanceOf(AttackCommand.class);
        }

        @Test
        @DisplayName("Should attack weakest enemy when multiple enemies exist")
        void shouldAttackWeakestEnemy() {
            Character self = CharacterFactory.createWarrior("Self");

            Character strongEnemy = CharacterFactory.createWarrior("Strong");
            strongEnemy.setHealth(150); // Full health

            Character weakEnemy = CharacterFactory.createMage("Weak");
            weakEnemy.setHealth(20); // Low health

            Character mediumEnemy = CharacterFactory.createArcher("Medium");
            mediumEnemy.setHealth(50);

            GameCommand command = player.decideAction(
                self,
                List.of(self),
                List.of(strongEnemy, weakEnemy, mediumEnemy),
                gameState
            );

            assertThat(command).isInstanceOf(AttackCommand.class);
            AttackCommand attackCommand = (AttackCommand) command;
            // Should target the weakest enemy (Weak with 20 HP)
            assertThat(attackCommand.getDescription()).contains("Weak");
        }

        @Test
        @DisplayName("Should focus fire on same weak enemy across multiple turns")
        void shouldFocusFireOnWeakEnemy() {
            Character self = CharacterFactory.createWarrior("Self");

            Character enemy1 = CharacterFactory.createWarrior("Enemy1");
            enemy1.setHealth(100);

            Character enemy2 = CharacterFactory.createMage("Enemy2");
            enemy2.setHealth(30); // Weakest

            // First decision
            GameCommand command1 = player.decideAction(
                self,
                List.of(self),
                List.of(enemy1, enemy2),
                gameState
            );

            assertThat(command1).isInstanceOf(AttackCommand.class);
            assertThat(command1.getDescription()).contains("Enemy2");

            // Execute the attack
            command1.execute();

            // Second decision (enemy2 is now even weaker)
            GameCommand command2 = player.decideAction(
                self,
                List.of(self),
                List.of(enemy1, enemy2),
                gameState
            );

            // Should still target Enemy2
            assertThat(command2).isInstanceOf(AttackCommand.class);
            assertThat(command2.getDescription()).contains("Enemy2");
        }
    }

    @Nested
    @DisplayName("Integration Tests - Full Decision Tree")
    class IntegrationTests {

        @Test
        @DisplayName("Should follow decision priority: self-heal > ally-heal > attack")
        void shouldFollowDecisionPriority() {
            Character selfLowHP = CharacterFactory.createWarrior("SelfLow");
            selfLowHP.setHealth(40); // < 30%

            Character selfOkHP = CharacterFactory.createWarrior("SelfOk");
            selfOkHP.setHealth(100); // > 30%

            Character allyLowHP = CharacterFactory.createMage("AllyLow");
            allyLowHP.setHealth(10); // < 20%

            Character enemy = CharacterFactory.createArcher("Enemy");

            // Case 1: Self HP low - should heal self
            GameCommand cmd1 = player.decideAction(selfLowHP, List.of(selfLowHP, allyLowHP),
                List.of(enemy), gameState);
            assertThat(cmd1).isInstanceOf(HealCommand.class);
            assertThat(cmd1.getDescription()).contains("SelfLow");

            // Case 2: Self HP ok, ally low - should heal ally
            GameCommand cmd2 = player.decideAction(selfOkHP, List.of(selfOkHP, allyLowHP),
                List.of(enemy), gameState);
            assertThat(cmd2).isInstanceOf(HealCommand.class);
            assertThat(cmd2.getDescription()).contains("AllyLow");

            // Case 3: Everyone healthy - should attack
            Character allyHealthy = CharacterFactory.createMage("AllyHealthy");
            GameCommand cmd3 = player.decideAction(selfOkHP, List.of(selfOkHP, allyHealthy),
                List.of(enemy), gameState);
            assertThat(cmd3).isInstanceOf(AttackCommand.class);
        }

        @Test
        @DisplayName("Should make sensible decisions in realistic battle scenario")
        void shouldMakeSensibleDecisionsInBattle() {
            Character warrior = CharacterFactory.createWarrior("Tank");
            Character mage = CharacterFactory.createMage("Healer");
            Character enemy1 = CharacterFactory.createArcher("Enemy1");
            Character enemy2 = CharacterFactory.createRogue("Enemy2");

            // Simulate battle damage
            warrior.setHealth(100); // 66% - ok
            mage.setHealth(60); // 75% - ok

            // Turn 1: Everyone healthy - should attack weakest
            GameCommand turn1 = player.decideAction(warrior,
                List.of(warrior, mage),
                List.of(enemy1, enemy2),
                gameState);
            assertThat(turn1).isInstanceOf(AttackCommand.class);

            // Simulate warrior taking heavy damage
            warrior.setHealth(30); // 20% - below self-heal threshold

            // Turn 2: Warrior hurt - should heal self
            GameCommand turn2 = player.decideAction(warrior,
                List.of(warrior, mage),
                List.of(enemy1, enemy2),
                gameState);
            assertThat(turn2).isInstanceOf(HealCommand.class);
            assertThat(turn2.getDescription()).contains("Tank");
        }
    }

    @Nested
    @DisplayName("Strategy Pattern Verification")
    class StrategyPatternVerification {

        @Test
        @DisplayName("Should implement Player interface")
        void shouldImplementPlayerInterface() {
            assertThat(player).isInstanceOf(Player.class);
        }

        @Test
        @DisplayName("Should be interchangeable with other player implementations")
        void shouldBeInterchangeable() {
            Player ruleBasedPlayer = new RuleBasedPlayer();
            Character character = CharacterFactory.createWarrior("Test");
            Character enemy = CharacterFactory.createMage("Enemy");

            // Should be able to use Player interface methods
            GameCommand command = ruleBasedPlayer.decideAction(
                character,
                List.of(character),
                List.of(enemy),
                gameState
            );

            assertThat(command).isNotNull();
            assertThat(command).isInstanceOf(GameCommand.class);
        }
    }
}
