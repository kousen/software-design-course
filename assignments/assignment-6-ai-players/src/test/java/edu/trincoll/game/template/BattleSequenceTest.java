package edu.trincoll.game.template;

import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Template Method Pattern - BattleSequence Tests")
class BattleSequenceTest {

    @Nested
    @DisplayName("StandardBattleSequence Tests")
    class StandardBattleSequenceTests {

        private Character attacker;
        private Character defender;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("Attacker");
            defender = CharacterFactory.createMage("Defender");
        }

        @Test
        @DisplayName("Should execute standard battle sequence")
        void shouldExecuteStandardBattleSequence() {
            StandardBattleSequence sequence = new StandardBattleSequence(attacker, defender);
            int initialHealth = defender.getStats().health();

            sequence.executeTurn();

            assertThat(defender.getStats().health()).isLessThan(initialHealth);
        }

        @Test
        @DisplayName("Should deal correct damage in standard sequence")
        void shouldDealCorrectDamage() {
            StandardBattleSequence sequence = new StandardBattleSequence(attacker, defender);

            sequence.executeTurn();

            // Warrior: 40 * 1.2 = 48 damage
            // Mage defense: 48 - (10/2) = 48 - 5 = 43 actual damage
            // Expected: 80 - 43 = 37
            assertThat(defender.getStats().health()).isEqualTo(37);
        }

        @Test
        @DisplayName("Should work with different character combinations")
        void shouldWorkWithDifferentCharacterCombinations() {
            Character archer = CharacterFactory.createArcher("Legolas");
            Character warrior = CharacterFactory.createWarrior("Conan");

            StandardBattleSequence sequence = new StandardBattleSequence(archer, warrior);
            int initialHealth = warrior.getStats().health();

            sequence.executeTurn();

            assertThat(warrior.getStats().health()).isLessThan(initialHealth);
        }

        @Test
        @DisplayName("Should not affect attacker health")
        void shouldNotAffectAttackerHealth() {
            StandardBattleSequence sequence = new StandardBattleSequence(attacker, defender);
            int attackerInitialHealth = attacker.getStats().health();

            sequence.executeTurn();

            assertThat(attacker.getStats().health()).isEqualTo(attackerInitialHealth);
        }
    }

    @Nested
    @DisplayName("PowerAttackSequence Tests")
    class PowerAttackSequenceTests {

        private Character attacker;
        private Character defender;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("PowerAttacker");
            defender = CharacterFactory.createMage("Defender");
        }

        @Test
        @DisplayName("Should execute power attack sequence")
        void shouldExecutePowerAttackSequence() {
            PowerAttackSequence sequence = new PowerAttackSequence(attacker, defender);
            int initialHealth = defender.getStats().health();

            sequence.executeTurn();

            assertThat(defender.getStats().health()).isLessThan(initialHealth);
        }

        @Test
        @DisplayName("Should deal bonus damage compared to standard attack")
        void shouldDealBonusDamage() {
            // Execute standard attack
            Character standardAttacker = CharacterFactory.createWarrior("Standard");
            Character standardDefender = CharacterFactory.createMage("Target1");
            StandardBattleSequence standardSeq = new StandardBattleSequence(standardAttacker, standardDefender);
            standardSeq.executeTurn();
            int standardDamageTaken = 80 - standardDefender.getStats().health();

            // Execute power attack
            Character powerAttacker = CharacterFactory.createWarrior("Power");
            Character powerDefender = CharacterFactory.createMage("Target2");
            PowerAttackSequence powerSeq = new PowerAttackSequence(powerAttacker, powerDefender);
            powerSeq.executeTurn();
            int powerDamageTaken = 80 - powerDefender.getStats().health();

            // Power attack should deal more damage
            assertThat(powerDamageTaken).isGreaterThan(standardDamageTaken);
        }

        @Test
        @DisplayName("Should calculate correct bonus damage")
        void shouldCalculateCorrectBonusDamage() {
            PowerAttackSequence sequence = new PowerAttackSequence(attacker, defender);

            sequence.executeTurn();

            // Base damage: 40 * 1.2 = 48
            // Bonus: 40 / 4 = 10
            // Total: 58
            // After defense: 58 - (10/2) = 58 - 5 = 53
            // Expected health: 80 - 53 = 27
            assertThat(defender.getStats().health()).isEqualTo(27);
        }

        @Test
        @DisplayName("Should apply recoil damage to attacker")
        void shouldApplyRecoilDamage() {
            PowerAttackSequence sequence = new PowerAttackSequence(attacker, defender);
            int attackerInitialHealth = attacker.getStats().health();

            sequence.executeTurn();

            // Recoil: 10% of 150 max health = 15
            // Expected: 150 - 15 = 135
            assertThat(attacker.getStats().health()).isEqualTo(135);
            assertThat(attacker.getStats().health()).isLessThan(attackerInitialHealth);
        }

        @Test
        @DisplayName("Should work with high attack power characters")
        void shouldWorkWithHighAttackPower() {
            // Rogue has 55 attack power
            Character rogue = CharacterFactory.createRogue("Assassin");
            Character target = CharacterFactory.createMage("Target");

            PowerAttackSequence sequence = new PowerAttackSequence(rogue, target);

            sequence.executeTurn();

            // Base: 55 * 1.2 = 66
            // Bonus: 55 / 4 = 13 (integer division)
            // Total: 79
            // After defense: 79 - 5 = 74
            // Expected: 80 - 74 = 6
            assertThat(target.getStats().health()).isEqualTo(6);
        }

        @Test
        @DisplayName("Should calculate recoil based on max health, not current")
        void shouldCalculateRecoilBasedOnMaxHealth() {
            // Reduce attacker health first
            attacker.setHealth(75);

            PowerAttackSequence sequence = new PowerAttackSequence(attacker, defender);

            sequence.executeTurn();

            // Recoil should be 10% of 150 (max), not 10% of 75 (current)
            // Expected: 75 - 15 = 60
            assertThat(attacker.getStats().health()).isEqualTo(60);
        }
    }

    @Nested
    @DisplayName("Template Method Pattern Verification")
    class TemplateMethodPatternVerification {

        @Test
        @DisplayName("Template method should be final (not overridable)")
        void templateMethodShouldBeFinal() throws NoSuchMethodException {
            java.lang.reflect.Method method = BattleSequence.class.getDeclaredMethod("executeTurn");

            assertThat(java.lang.reflect.Modifier.isFinal(method.getModifiers())).isTrue();
        }

        @Test
        @DisplayName("Hook methods should be overridable (not final)")
        void hookMethodsShouldBeOverridable() throws NoSuchMethodException {
            java.lang.reflect.Method beginTurn = BattleSequence.class.getDeclaredMethod("beginTurn");
            java.lang.reflect.Method preAttackAction = BattleSequence.class.getDeclaredMethod("preAttackAction");
            java.lang.reflect.Method postAttackAction = BattleSequence.class.getDeclaredMethod("postAttackAction");
            java.lang.reflect.Method endTurn = BattleSequence.class.getDeclaredMethod("endTurn");

            assertThat(java.lang.reflect.Modifier.isFinal(beginTurn.getModifiers())).isFalse();
            assertThat(java.lang.reflect.Modifier.isFinal(preAttackAction.getModifiers())).isFalse();
            assertThat(java.lang.reflect.Modifier.isFinal(postAttackAction.getModifiers())).isFalse();
            assertThat(java.lang.reflect.Modifier.isFinal(endTurn.getModifiers())).isFalse();
        }

        @Test
        @DisplayName("performAttack should be abstract (must be overridden)")
        void performAttackShouldBeAbstract() throws NoSuchMethodException {
            java.lang.reflect.Method performAttack = BattleSequence.class.getDeclaredMethod("performAttack");

            assertThat(java.lang.reflect.Modifier.isAbstract(performAttack.getModifiers())).isTrue();
        }

        @Test
        @DisplayName("Different implementations should produce different results")
        void differentImplementationsShouldProduceDifferentResults() {
            Character attacker1 = CharacterFactory.createWarrior("Standard");
            Character defender1 = CharacterFactory.createMage("Target1");

            Character attacker2 = CharacterFactory.createWarrior("Power");
            Character defender2 = CharacterFactory.createMage("Target2");

            StandardBattleSequence standardSeq = new StandardBattleSequence(attacker1, defender1);
            PowerAttackSequence powerSeq = new PowerAttackSequence(attacker2, defender2);

            standardSeq.executeTurn();
            powerSeq.executeTurn();

            // Results should differ
            assertThat(defender1.getStats().health()).isNotEqualTo(defender2.getStats().health());
            assertThat(attacker1.getStats().health()).isNotEqualTo(attacker2.getStats().health());
        }
    }
}
