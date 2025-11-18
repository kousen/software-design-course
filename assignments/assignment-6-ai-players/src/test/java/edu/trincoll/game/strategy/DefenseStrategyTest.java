package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterStats;
import edu.trincoll.game.model.CharacterType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Defense Strategy Tests")
class DefenseStrategyTest {

    @Nested
    @DisplayName("StandardDefenseStrategy Tests")
    class StandardDefenseStrategyTests {

        @Test
        @DisplayName("Should reduce damage by half of defense stat")
        void shouldReduceDamage() {
            // Given: Defender with 20 defense, incoming 50 damage
            CharacterStats defenderStats = CharacterStats.create(100, 30, 20, 0);
            Character defender = new Character("Target", CharacterType.ARCHER,
                defenderStats, new MeleeAttackStrategy(), new StandardDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 50 - (20/2) = 50 - 10 = 40
            int actualDamage = defender.defend(50);

            // Then: Actual damage should be 40
            assertThat(actualDamage).isEqualTo(40);
        }

        @Test
        @DisplayName("Should handle high defense reducing damage to minimum")
        void shouldHandleHighDefense() {
            // Given: Defender with 60 defense, incoming 50 damage
            CharacterStats defenderStats = CharacterStats.create(100, 30, 60, 0);
            Character defender = new Character("Tank", CharacterType.WARRIOR,
                defenderStats, new MeleeAttackStrategy(), new StandardDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 50 - (60/2) = 50 - 30 = 20
            int actualDamage = defender.defend(50);

            // Then: Actual damage should be 20
            assertThat(actualDamage).isEqualTo(20);
        }

        @Test
        @DisplayName("Should never return negative damage")
        void shouldNeverReturnNegativeDamage() {
            // Given: Defender with 100 defense, incoming 20 damage
            CharacterStats defenderStats = CharacterStats.create(100, 30, 100, 0);
            Character defender = new Character("Fortress", CharacterType.WARRIOR,
                defenderStats, new MeleeAttackStrategy(), new StandardDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 20 - (100/2) = 20 - 50 = -30, but should return 0
            int actualDamage = defender.defend(20);

            // Then: Actual damage should be 0 (not negative)
            assertThat(actualDamage).isEqualTo(0);
        }

        @Test
        @DisplayName("Should handle zero defense")
        void shouldHandleZeroDefense() {
            // Given: Defender with 0 defense, incoming 50 damage
            CharacterStats defenderStats = CharacterStats.create(100, 30, 0, 0);
            Character defender = new Character("Naked", CharacterType.MAGE,
                defenderStats, new MeleeAttackStrategy(), new StandardDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 50 - (0/2) = 50 - 0 = 50
            int actualDamage = defender.defend(50);

            // Then: Full damage should be taken
            assertThat(actualDamage).isEqualTo(50);
        }

        @Test
        @DisplayName("Should handle low defense values")
        void shouldHandleLowDefense() {
            // Given: Defender with 5 defense, incoming 50 damage
            CharacterStats defenderStats = CharacterStats.create(100, 30, 5, 0);
            Character defender = new Character("Light Armor", CharacterType.ROGUE,
                defenderStats, new MeleeAttackStrategy(), new StandardDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 50 - (5/2) = 50 - 2 = 48 (integer division)
            int actualDamage = defender.defend(50);

            // Then: Actual damage should be 48
            assertThat(actualDamage).isEqualTo(48);
        }
    }

    @Nested
    @DisplayName("HeavyArmorDefenseStrategy Tests")
    class HeavyArmorDefenseStrategyTests {

        @Test
        @DisplayName("Should reduce damage by full defense value")
        void shouldReduceDamageByFullDefense() {
            // Given: Warrior with 30 defense, incoming 100 damage
            CharacterStats warriorStats = CharacterStats.create(150, 40, 30, 0);
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 100 - 30 = 70
            int actualDamage = warrior.defend(100);

            // Then: Actual damage should be 70
            assertThat(actualDamage).isEqualTo(70);
        }

        @Test
        @DisplayName("Should cap damage reduction at 75%")
        void shouldCapDamageReductionAt75Percent() {
            // Given: Warrior with 80 defense, incoming 100 damage
            // Theoretical reduction would be 80, leaving 20 damage
            // But 75% cap means minimum 25% damage: 100 * 0.25 = 25
            CharacterStats warriorStats = CharacterStats.create(150, 40, 80, 0);
            Character warrior = new Character("Fortress", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Expected: Max 75% reduction, so 25% of 100 = 25
            int actualDamage = warrior.defend(100);

            // Then: Actual damage should be 25 (not 20)
            assertThat(actualDamage).isEqualTo(25);
        }

        @Test
        @DisplayName("Should cap damage reduction at 75% for very high defense")
        void shouldCapDamageReductionForVeryHighDefense() {
            // Given: Warrior with 200 defense, incoming 100 damage
            CharacterStats warriorStats = CharacterStats.create(150, 40, 200, 0);
            Character warrior = new Character("Invincible", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Expected: Max 75% reduction, so 25% of 100 = 25
            int actualDamage = warrior.defend(100);

            // Then: Actual damage should be 25
            assertThat(actualDamage).isEqualTo(25);
        }

        @Test
        @DisplayName("Should not apply cap when defense is reasonable")
        void shouldNotApplyCapForReasonableDefense() {
            // Given: Warrior with 50 defense, incoming 100 damage
            // Reduction: 50, leaving 50 damage
            // This is exactly 50% reduction, under the 75% cap
            CharacterStats warriorStats = CharacterStats.create(150, 40, 50, 0);
            Character warrior = new Character("Knight", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 100 - 50 = 50
            int actualDamage = warrior.defend(100);

            // Then: Actual damage should be 50
            assertThat(actualDamage).isEqualTo(50);
        }

        @Test
        @DisplayName("Should handle zero defense")
        void shouldHandleZeroDefense() {
            // Given: Warrior with 0 defense, incoming 50 damage
            CharacterStats warriorStats = CharacterStats.create(150, 40, 0, 0);
            Character warrior = new Character("Naked Warrior", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Expected: 50 - 0 = 50
            int actualDamage = warrior.defend(50);

            // Then: Full damage should be taken
            assertThat(actualDamage).isEqualTo(50);
        }

        @Test
        @DisplayName("Should apply 75% cap even with low incoming damage")
        void shouldApplyCapWithLowIncomingDamage() {
            // Given: Warrior with 60 defense, incoming 30 damage
            CharacterStats warriorStats = CharacterStats.create(150, 40, 60, 0);
            Character warrior = new Character("Heavy", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When: Calculate actual damage
            // Max reduction: 30 * 0.75 = 22.5 → 22, defense is 60, so reduce by 22
            // Damage: 30 - 22 = 8 (25% of 30 gets through due to cap)
            int actualDamage = warrior.defend(30);

            // Then: Actual damage should be 8 (not 0, due to 75% cap)
            assertThat(actualDamage).isEqualTo(8);
        }

        @Test
        @DisplayName("Should apply 75% cap with different incoming damage values")
        void shouldApply75PercentCapWithDifferentDamage() {
            // Given: Warrior with 100 defense, various incoming damage amounts
            CharacterStats warriorStats = CharacterStats.create(150, 40, 100, 0);
            Character warrior = new Character("Tank", CharacterType.WARRIOR,
                warriorStats, new MeleeAttackStrategy(), new HeavyArmorDefenseStrategy());

            // When/Then: Test with 200 damage
            // Max reduction: 200 * 0.75 = 150, defense is 100, so reduce by 100
            // Damage: 200 - 100 = 100
            assertThat(warrior.defend(200)).isEqualTo(100);

            // When/Then: Test with 80 damage
            // Max reduction: 80 * 0.75 = 60, defense is 100, so reduce by 60
            // Damage: 80 - 60 = 20
            assertThat(warrior.defend(80)).isEqualTo(20);
        }
    }
}
