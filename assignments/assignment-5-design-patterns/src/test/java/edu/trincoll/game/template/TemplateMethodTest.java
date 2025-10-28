package edu.trincoll.game.template;

import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Template Method Pattern Tests")
class TemplateMethodTest {

    @Nested
    @DisplayName("Standard Battle Sequence")
    class StandardBattleSequenceTests {

        private Character attacker;
        private Character defender;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("Attacker");
            defender = CharacterFactory.createWarrior("Defender");
        }

        @Test
        @DisplayName("TODO 5b: Standard battle sequence executes attack")
        void testStandardBattleSequence() {
            int initialHealth = defender.getStats().health();

            StandardBattleSequence sequence = new StandardBattleSequence(attacker, defender);
            sequence.executeTurn();

            assertThat(defender.getStats().health())
                .as("Defender should take damage")
                .isLessThan(initialHealth);
        }
    }

    @Nested
    @DisplayName("Power Attack Sequence")
    class PowerAttackSequenceTests {

        private Character attacker;
        private Character defender;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("PowerAttacker");
            defender = CharacterFactory.createWarrior("Defender");
        }

        @Test
        @DisplayName("TODO 5c: Power attack deals more damage than standard")
        void testPowerAttackDamage() {
            int defenderInitialHealth = defender.getStats().health();

            // Execute standard attack on one defender
            Character defender1 = CharacterFactory.createWarrior("Defender1");
            StandardBattleSequence standardSequence = new StandardBattleSequence(attacker, defender1);
            standardSequence.executeTurn();
            int standardDamage = defenderInitialHealth - defender1.getStats().health();

            // Execute power attack on another defender
            Character defender2 = CharacterFactory.createWarrior("Defender2");
            PowerAttackSequence powerSequence = new PowerAttackSequence(attacker, defender2);
            powerSequence.executeTurn();
            int powerDamage = defenderInitialHealth - defender2.getStats().health();

            assertThat(powerDamage)
                .as("Power attack should deal more damage")
                .isGreaterThan(standardDamage);
        }

        @Test
        @DisplayName("TODO 5c: Power attack causes recoil damage to attacker")
        void testPowerAttackRecoil() {
            int attackerInitialHealth = attacker.getStats().health();

            PowerAttackSequence sequence = new PowerAttackSequence(attacker, defender);
            sequence.executeTurn();

            assertThat(attacker.getStats().health())
                .as("Attacker should take recoil damage")
                .isLessThan(attackerInitialHealth);

            int recoilDamage = attackerInitialHealth - attacker.getStats().health();
            int expectedRecoil = (int) (attacker.getStats().maxHealth() * 0.1);

            assertThat(recoilDamage)
                .as("Recoil should be 10% of max health")
                .isEqualTo(expectedRecoil);
        }
    }

    @Nested
    @DisplayName("Template Method Structure")
    class TemplateMethodStructureTests {

        @Test
        @DisplayName("TODO 5a: Template method calls all steps in order")
        void testTemplateMethodSequence() {
            Character attacker = CharacterFactory.createWarrior("Attacker");
            Character defender = CharacterFactory.createWarrior("Defender");

            // Create a spy sequence to verify method calls
            TestableSequence sequence = new TestableSequence(attacker, defender);
            sequence.executeTurn();

            assertThat(sequence.methodCallOrder)
                .as("Methods should be called in correct order")
                .containsExactly(
                    "beginTurn",
                    "preAttackAction",
                    "performAttack",
                    "postAttackAction",
                    "endTurn"
                );
        }

        /**
         * Test sequence that tracks method calls.
         */
        private static class TestableSequence extends BattleSequence {
            java.util.List<String> methodCallOrder = new java.util.ArrayList<>();

            public TestableSequence(Character attacker, Character defender) {
                super(attacker, defender);
            }

            @Override
            protected void beginTurn() {
                methodCallOrder.add("beginTurn");
            }

            @Override
            protected void preAttackAction() {
                methodCallOrder.add("preAttackAction");
            }

            @Override
            protected void performAttack() {
                methodCallOrder.add("performAttack");
                defender.takeDamage(attacker.attack(defender));
            }

            @Override
            protected void postAttackAction() {
                methodCallOrder.add("postAttackAction");
            }

            @Override
            protected void endTurn() {
                methodCallOrder.add("endTurn");
            }
        }
    }
}
