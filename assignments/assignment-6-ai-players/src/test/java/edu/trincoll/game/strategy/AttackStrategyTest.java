package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterStats;
import edu.trincoll.game.model.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Attack Strategy Tests")
class AttackStrategyTest {

    private DefenseStrategy standardDefense;

    @BeforeEach
    void setUp() {
        standardDefense = new StandardDefenseStrategy();
    }

    @Nested
    @DisplayName("MeleeAttackStrategy Tests")
    class MeleeAttackStrategyTests {

        @Test
        @DisplayName("Should calculate melee damage with 20% bonus")
        void shouldCalculateMeleeDamage() {
            // Given: Attacker with 50 attack power
            CharacterStats attackerStats = CharacterStats.create(100, 50, 20, 0);
            Character attacker = new Character("Warrior", CharacterType.WARRIOR,
                attackerStats, new MeleeAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 50 * 1.2 = 60
            int damage = attacker.attack(target);

            // Then: Damage should be 60 (50 base + 20% bonus)
            assertThat(damage).isEqualTo(60);
        }

        @Test
        @DisplayName("Should calculate melee damage for low attack power")
        void shouldCalculateMeleeDamageForLowAttack() {
            // Given: Attacker with 10 attack power
            CharacterStats attackerStats = CharacterStats.create(100, 10, 20, 0);
            Character attacker = new Character("Weak", CharacterType.ROGUE,
                attackerStats, new MeleeAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 10 * 1.2 = 12
            int damage = attacker.attack(target);

            // Then: Damage should be 12
            assertThat(damage).isEqualTo(12);
        }

        @Test
        @DisplayName("Should calculate melee damage for high attack power")
        void shouldCalculateMeleeDamageForHighAttack() {
            // Given: Attacker with 100 attack power
            CharacterStats attackerStats = CharacterStats.create(200, 100, 30, 0);
            Character attacker = new Character("Strong", CharacterType.WARRIOR,
                attackerStats, new MeleeAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 100 * 1.2 = 120
            int damage = attacker.attack(target);

            // Then: Damage should be 120
            assertThat(damage).isEqualTo(120);
        }
    }

    @Nested
    @DisplayName("MagicAttackStrategy Tests")
    class MagicAttackStrategyTests {

        @Test
        @DisplayName("Should calculate magic damage with mana bonus")
        void shouldCalculateMagicDamage() {
            // Given: Mage with 60 attack and 50 mana
            CharacterStats mageStats = CharacterStats.create(80, 60, 10, 100);
            mageStats = mageStats.withMana(50);
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            int initialMana = mage.getStats().mana();

            // When: Calculate damage
            // Expected: 60 + (50/10) = 60 + 5 = 65
            int damage = mage.attack(target);

            // Then: Damage should be 65 and mana reduced by 10
            assertThat(damage).isEqualTo(65);
            assertThat(mage.getStats().mana()).isEqualTo(initialMana - 10);
        }

        @Test
        @DisplayName("Should calculate magic damage with high mana")
        void shouldCalculateMagicDamageWithHighMana() {
            // Given: Mage with 60 attack and 100 mana
            CharacterStats mageStats = CharacterStats.create(80, 60, 10, 100);
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 60 + (100/10) = 60 + 10 = 70
            int damage = mage.attack(target);

            // Then: Damage should be 70
            assertThat(damage).isEqualTo(70);
            assertThat(mage.getStats().mana()).isEqualTo(90);
        }

        @Test
        @DisplayName("Should throw exception when not enough mana")
        void shouldThrowExceptionWhenNotEnoughMana() {
            // Given: Mage with only 5 mana (less than 10 required)
            CharacterStats mageStats = CharacterStats.create(80, 60, 10, 100);
            mageStats = mageStats.withMana(5);
            Character mage = new Character("Tired Mage", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When/Then: Attack should throw exception
            assertThatThrownBy(() -> mage.attack(target))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Not enough mana");
        }

        @Test
        @DisplayName("Should calculate magic damage with low mana bonus")
        void shouldCalculateMagicDamageWithLowMana() {
            // Given: Mage with 60 attack and 15 mana
            CharacterStats mageStats = CharacterStats.create(80, 60, 10, 100);
            mageStats = mageStats.withMana(15);
            Character mage = new Character("Low Mana Mage", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 60 + (15/10) = 60 + 1 = 61
            int damage = mage.attack(target);

            // Then: Damage should be 61
            assertThat(damage).isEqualTo(61);
            assertThat(mage.getStats().mana()).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("RangedAttackStrategy Tests")
    class RangedAttackStrategyTests {

        @Test
        @DisplayName("Should calculate ranged damage without critical hit")
        void shouldCalculateRangedDamageNoCritical() {
            // Given: Archer with 50 attack, target with 80/100 HP (80%)
            CharacterStats archerStats = CharacterStats.create(100, 50, 15, 20);
            Character archer = new Character("Legolas", CharacterType.ARCHER,
                archerStats, new RangedAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            targetStats = targetStats.withHealth(80);
            Character target = new Character("Target", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 50 * 0.8 = 40 (no critical since 80% > 30%)
            int damage = archer.attack(target);

            // Then: Damage should be 40
            assertThat(damage).isEqualTo(40);
        }

        @Test
        @DisplayName("Should calculate ranged damage with critical hit on low health target")
        void shouldCalculateRangedDamageWithCritical() {
            // Given: Archer with 50 attack, target with 20/100 HP (20%)
            CharacterStats archerStats = CharacterStats.create(100, 50, 15, 20);
            Character archer = new Character("Legolas", CharacterType.ARCHER,
                archerStats, new RangedAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            targetStats = targetStats.withHealth(20);
            Character target = new Character("Wounded", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 50 * 0.8 = 40, then 40 * 1.5 = 60 (critical)
            int damage = archer.attack(target);

            // Then: Damage should be 60
            assertThat(damage).isEqualTo(60);
        }

        @Test
        @DisplayName("Should calculate critical hit at exactly 30% health")
        void shouldCalculateCriticalAtThirtyPercent() {
            // Given: Archer with 50 attack, target with exactly 30/100 HP
            CharacterStats archerStats = CharacterStats.create(100, 50, 15, 20);
            Character archer = new Character("Legolas", CharacterType.ARCHER,
                archerStats, new RangedAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            targetStats = targetStats.withHealth(30);
            Character target = new Character("Borderline", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // At exactly 30%, should NOT be critical (< 30% means strictly less than)
            int damage = archer.attack(target);

            // Then: Damage should be 40 (no critical)
            assertThat(damage).isEqualTo(40);
        }

        @Test
        @DisplayName("Should calculate critical hit just under 30% health")
        void shouldCalculateCriticalJustUnderThirtyPercent() {
            // Given: Archer with 50 attack, target with 29/100 HP (29%)
            CharacterStats archerStats = CharacterStats.create(100, 50, 15, 20);
            Character archer = new Character("Legolas", CharacterType.ARCHER,
                archerStats, new RangedAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            targetStats = targetStats.withHealth(29);
            Character target = new Character("Nearly Dead", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 50 * 0.8 = 40, then 40 * 1.5 = 60 (critical)
            int damage = archer.attack(target);

            // Then: Damage should be 60
            assertThat(damage).isEqualTo(60);
        }

        @Test
        @DisplayName("Should calculate ranged damage on full health target")
        void shouldCalculateRangedDamageOnFullHealth() {
            // Given: Archer with 50 attack, target at full health
            CharacterStats archerStats = CharacterStats.create(100, 50, 15, 20);
            Character archer = new Character("Legolas", CharacterType.ARCHER,
                archerStats, new RangedAttackStrategy(), standardDefense);

            CharacterStats targetStats = CharacterStats.create(100, 30, 15, 0);
            Character target = new Character("Fresh", CharacterType.WARRIOR,
                targetStats, new MeleeAttackStrategy(), standardDefense);

            // When: Calculate damage
            // Expected: 50 * 0.8 = 40 (no critical)
            int damage = archer.attack(target);

            // Then: Damage should be 40
            assertThat(damage).isEqualTo(40);
        }
    }
}
