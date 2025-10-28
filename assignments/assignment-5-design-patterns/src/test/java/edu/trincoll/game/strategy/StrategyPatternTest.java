package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterStats;
import edu.trincoll.game.model.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Strategy Pattern Tests")
class StrategyPatternTest {

    @Nested
    @DisplayName("Attack Strategies")
    class AttackStrategyTests {

        private Character attacker;
        private Character target;

        @BeforeEach
        void setUp() {
            // Create basic characters for testing
            attacker = new Character(
                "TestAttacker",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 50, 20, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            target = new Character(
                "TestTarget",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 15, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );
        }

        @Test
        @DisplayName("TODO 1a: Melee attack calculates correct damage")
        void testMeleeAttack() {
            MeleeAttackStrategy strategy = new MeleeAttackStrategy();
            int damage = strategy.calculateDamage(attacker, target);

            assertThat(damage)
                .as("Melee attack should be attack power * 1.2")
                .isEqualTo(60); // 50 * 1.2 = 60
        }

        @Test
        @DisplayName("TODO 1b: Magic attack uses mana and calculates damage")
        void testMagicAttack() {
            Character mage = new Character(
                "Mage",
                CharacterType.MAGE,
                CharacterStats.create(80, 60, 10, 100),
                new MagicAttackStrategy(),
                new StandardDefenseStrategy()
            );

            MagicAttackStrategy strategy = new MagicAttackStrategy();
            int initialMana = mage.getStats().mana();
            int damage = strategy.calculateDamage(mage, target);

            assertThat(damage)
                .as("Magic damage = attack power + (mana / 10)")
                .isEqualTo(70); // 60 + (100 / 10) = 70

            assertThat(mage.getStats().mana())
                .as("Magic attack should consume 10 mana")
                .isEqualTo(initialMana - 10);
        }

        @Test
        @DisplayName("TODO 1c: Ranged attack without critical hit")
        void testRangedAttackNoCritical() {
            RangedAttackStrategy strategy = new RangedAttackStrategy();

            // Target has high health, no critical
            Character healthyTarget = new Character(
                "HealthyTarget",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 15, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            int damage = strategy.calculateDamage(attacker, healthyTarget);

            assertThat(damage)
                .as("Ranged attack with no critical = attack power * 0.8")
                .isEqualTo(40); // 50 * 0.8 = 40
        }

        @Test
        @DisplayName("TODO 1c: Ranged attack with critical hit")
        void testRangedAttackCritical() {
            RangedAttackStrategy strategy = new RangedAttackStrategy();

            // Target has low health (< 30%), should trigger critical
            Character lowHealthTarget = new Character(
                "LowHealthTarget",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 15, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );
            // Set health to 25% directly - use setHealth to avoid defense calculation
            lowHealthTarget.setHealth(25);

            int damage = strategy.calculateDamage(attacker, lowHealthTarget);

            assertThat(damage)
                .as("Ranged critical attack = (attack power * 0.8) * 1.5")
                .isEqualTo(60); // (50 * 0.8) * 1.5 = 60
        }
    }

    @Nested
    @DisplayName("Defense Strategies")
    class DefenseStrategyTests {

        private Character defender;

        @BeforeEach
        void setUp() {
            defender = new Character(
                "Defender",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 20, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );
        }

        @Test
        @DisplayName("TODO 1d: Standard defense reduces damage correctly")
        void testStandardDefense() {
            StandardDefenseStrategy strategy = new StandardDefenseStrategy();
            int actualDamage = strategy.calculateDamageReduction(defender, 50);

            assertThat(actualDamage)
                .as("Standard defense: damage - (defense / 2)")
                .isEqualTo(40); // 50 - (20 / 2) = 40
        }

        @Test
        @DisplayName("TODO 1d: Standard defense never gives negative damage")
        void testStandardDefenseFloor() {
            StandardDefenseStrategy strategy = new StandardDefenseStrategy();
            int actualDamage = strategy.calculateDamageReduction(defender, 5);

            assertThat(actualDamage)
                .as("Defense should never result in negative damage")
                .isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("TODO 1e: Heavy armor defense with normal damage")
        void testHeavyArmorDefense() {
            HeavyArmorDefenseStrategy strategy = new HeavyArmorDefenseStrategy();
            Character heavyDefender = new Character(
                "HeavyDefender",
                CharacterType.WARRIOR,
                CharacterStats.create(150, 40, 30, 0),
                new MeleeAttackStrategy(),
                new HeavyArmorDefenseStrategy()
            );

            int actualDamage = strategy.calculateDamageReduction(heavyDefender, 100);

            assertThat(actualDamage)
                .as("Heavy armor: damage - defense")
                .isEqualTo(70); // 100 - 30 = 70
        }

        @Test
        @DisplayName("TODO 1e: Heavy armor defense respects 75% reduction cap")
        void testHeavyArmorCap() {
            HeavyArmorDefenseStrategy strategy = new HeavyArmorDefenseStrategy();
            Character superDefender = new Character(
                "SuperDefender",
                CharacterType.WARRIOR,
                CharacterStats.create(150, 40, 90, 0), // Very high defense
                new MeleeAttackStrategy(),
                new HeavyArmorDefenseStrategy()
            );

            int actualDamage = strategy.calculateDamageReduction(superDefender, 100);

            assertThat(actualDamage)
                .as("Heavy armor capped at 75% reduction, so min 25% damage")
                .isEqualTo(25); // 100 * 0.25 = 25
        }
    }

    @Nested
    @DisplayName("Strategy Swapping (Runtime flexibility)")
    class StrategySwappingTests {

        @Test
        @DisplayName("Character can change attack strategy at runtime")
        void testChangeAttackStrategy() {
            Character fighter = new Character(
                "Fighter",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 50, 20, 50),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            Character target = new Character(
                "Target",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 15, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            // Start with melee
            int meleeDamage = fighter.attack(target);
            assertThat(meleeDamage).isEqualTo(60); // 50 * 1.2

            // Switch to magic
            fighter.setAttackStrategy(new MagicAttackStrategy());
            int magicDamage = fighter.attack(target);
            assertThat(magicDamage).isEqualTo(55); // 50 + (50 / 10)
        }

        @Test
        @DisplayName("Character can change defense strategy at runtime")
        void testChangeDefenseStrategy() {
            Character fighter = new Character(
                "Fighter",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 50, 30, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            // Standard defense
            int damage1 = fighter.defend(100);
            assertThat(damage1).isEqualTo(85); // 100 - (30 / 2)

            // Switch to heavy armor
            fighter.setDefenseStrategy(new HeavyArmorDefenseStrategy());
            int damage2 = fighter.defend(100);
            assertThat(damage2).isEqualTo(70); // 100 - 30
        }
    }

    @Nested
    @DisplayName("Lambda Strategies (Modern Java)")
    class LambdaStrategyTests {

        @Test
        @DisplayName("Custom attack strategy using lambda")
        void testCustomLambdaAttackStrategy() {
            Character fighter = new Character(
                "Fighter",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 50, 20, 0),
                // Custom lambda strategy: always does 100 damage
                (attacker, target) -> 100,
                new StandardDefenseStrategy()
            );

            Character target = new Character(
                "Target",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 30, 15, 0),
                new MeleeAttackStrategy(),
                new StandardDefenseStrategy()
            );

            int damage = fighter.attack(target);
            assertThat(damage).isEqualTo(100);
        }

        @Test
        @DisplayName("Custom defense strategy using lambda")
        void testCustomLambdaDefenseStrategy() {
            Character fighter = new Character(
                "Fighter",
                CharacterType.WARRIOR,
                CharacterStats.create(100, 50, 20, 0),
                new MeleeAttackStrategy(),
                // Custom lambda strategy: always reduces damage by 50%
                (defender, damage) -> damage / 2
            );

            int actualDamage = fighter.defend(100);
            assertThat(actualDamage).isEqualTo(50);
        }
    }
}
