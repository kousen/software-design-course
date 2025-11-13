package edu.trincoll.game.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CharacterStats Tests")
class CharacterStatsTest {

    @Nested
    @DisplayName("Constructor Validation")
    class ConstructorValidation {

        @Test
        @DisplayName("Should create valid stats with all positive values")
        void shouldCreateValidStats() {
            CharacterStats stats = new CharacterStats(100, 100, 50, 30, 50, 50);

            assertThat(stats.health()).isEqualTo(100);
            assertThat(stats.maxHealth()).isEqualTo(100);
            assertThat(stats.attackPower()).isEqualTo(50);
            assertThat(stats.defense()).isEqualTo(30);
            assertThat(stats.mana()).isEqualTo(50);
            assertThat(stats.maxMana()).isEqualTo(50);
        }

        @Test
        @DisplayName("Should allow health to be less than max health")
        void shouldAllowHealthLessThanMax() {
            CharacterStats stats = new CharacterStats(50, 100, 50, 30, 25, 50);

            assertThat(stats.health()).isEqualTo(50);
            assertThat(stats.maxHealth()).isEqualTo(100);
        }

        @Test
        @DisplayName("Should throw exception for negative health")
        void shouldRejectNegativeHealth() {
            assertThatThrownBy(() -> new CharacterStats(-1, 100, 50, 30, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Health values must be positive");
        }

        @Test
        @DisplayName("Should throw exception for zero or negative max health")
        void shouldRejectInvalidMaxHealth() {
            assertThatThrownBy(() -> new CharacterStats(50, 0, 50, 30, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Health values must be positive");

            assertThatThrownBy(() -> new CharacterStats(50, -100, 50, 30, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Health values must be positive");
        }

        @Test
        @DisplayName("Should throw exception when health exceeds max health")
        void shouldRejectHealthExceedingMax() {
            assertThatThrownBy(() -> new CharacterStats(150, 100, 50, 30, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Health cannot exceed max health");
        }

        @Test
        @DisplayName("Should throw exception for negative attack power")
        void shouldRejectNegativeAttackPower() {
            assertThatThrownBy(() -> new CharacterStats(100, 100, -10, 30, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Stats cannot be negative");
        }

        @Test
        @DisplayName("Should throw exception for negative defense")
        void shouldRejectNegativeDefense() {
            assertThatThrownBy(() -> new CharacterStats(100, 100, 50, -10, 50, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Stats cannot be negative");
        }

        @Test
        @DisplayName("Should throw exception for negative mana")
        void shouldRejectNegativeMana() {
            assertThatThrownBy(() -> new CharacterStats(100, 100, 50, 30, -10, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Mana values cannot be negative");
        }

        @Test
        @DisplayName("Should throw exception for negative max mana")
        void shouldRejectNegativeMaxMana() {
            assertThatThrownBy(() -> new CharacterStats(100, 100, 50, 30, 50, -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Mana values cannot be negative");
        }

        @Test
        @DisplayName("Should throw exception when mana exceeds max mana")
        void shouldRejectManaExceedingMax() {
            assertThatThrownBy(() -> new CharacterStats(100, 100, 50, 30, 75, 50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Mana cannot exceed max mana");
        }
    }

    @Nested
    @DisplayName("Factory Method - create()")
    class FactoryMethod {

        @Test
        @DisplayName("Should create stats with full health and mana")
        void shouldCreateFullStats() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);

            assertThat(stats.health()).isEqualTo(100);
            assertThat(stats.maxHealth()).isEqualTo(100);
            assertThat(stats.mana()).isEqualTo(50);
            assertThat(stats.maxMana()).isEqualTo(50);
            assertThat(stats.attackPower()).isEqualTo(50);
            assertThat(stats.defense()).isEqualTo(30);
        }
    }

    @Nested
    @DisplayName("Immutable Updates - withHealth()")
    class WithHealth {

        @Test
        @DisplayName("Should return new instance with updated health")
        void shouldUpdateHealth() {
            CharacterStats original = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = original.withHealth(75);

            assertThat(updated.health()).isEqualTo(75);
            assertThat(original.health()).isEqualTo(100); // Original unchanged
        }

        @Test
        @DisplayName("Should cap health at max health")
        void shouldCapHealthAtMax() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = stats.withHealth(150);

            assertThat(updated.health()).isEqualTo(100);
        }

        @Test
        @DisplayName("Should floor health at zero")
        void shouldFloorHealthAtZero() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = stats.withHealth(-10);

            assertThat(updated.health()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Immutable Updates - withMana()")
    class WithMana {

        @Test
        @DisplayName("Should return new instance with updated mana")
        void shouldUpdateMana() {
            CharacterStats original = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = original.withMana(25);

            assertThat(updated.mana()).isEqualTo(25);
            assertThat(original.mana()).isEqualTo(50); // Original unchanged
        }

        @Test
        @DisplayName("Should cap mana at max mana")
        void shouldCapManaAtMax() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = stats.withMana(75);

            assertThat(updated.mana()).isEqualTo(50);
        }

        @Test
        @DisplayName("Should floor mana at zero")
        void shouldFloorManaAtZero() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);
            CharacterStats updated = stats.withMana(-10);

            assertThat(updated.mana()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Status Checks")
    class StatusChecks {

        @Test
        @DisplayName("Should be alive when health is positive")
        void shouldBeAliveWhenHealthPositive() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);

            assertThat(stats.isAlive()).isTrue();
            assertThat(stats.isDead()).isFalse();
        }

        @Test
        @DisplayName("Should be dead when health is zero")
        void shouldBeDeadWhenHealthZero() {
            CharacterStats stats = new CharacterStats(0, 100, 50, 30, 50, 50);

            assertThat(stats.isAlive()).isFalse();
            assertThat(stats.isDead()).isTrue();
        }

        @Test
        @DisplayName("Should still be alive with 1 HP")
        void shouldBeAliveWithOneHP() {
            CharacterStats stats = new CharacterStats(1, 100, 50, 30, 50, 50);

            assertThat(stats.isAlive()).isTrue();
            assertThat(stats.isDead()).isFalse();
        }
    }

    @Nested
    @DisplayName("Record Behavior")
    class RecordBehavior {

        @Test
        @DisplayName("Should have proper equals() behavior")
        void shouldHaveProperEquals() {
            CharacterStats stats1 = CharacterStats.create(100, 50, 30, 50);
            CharacterStats stats2 = CharacterStats.create(100, 50, 30, 50);
            CharacterStats stats3 = CharacterStats.create(100, 60, 30, 50);

            assertThat(stats1).isEqualTo(stats2);
            assertThat(stats1).isNotEqualTo(stats3);
        }

        @Test
        @DisplayName("Should have proper hashCode() behavior")
        void shouldHaveProperHashCode() {
            CharacterStats stats1 = CharacterStats.create(100, 50, 30, 50);
            CharacterStats stats2 = CharacterStats.create(100, 50, 30, 50);

            assertThat(stats1.hashCode()).isEqualTo(stats2.hashCode());
        }

        @Test
        @DisplayName("Should have meaningful toString()")
        void shouldHaveMeaningfulToString() {
            CharacterStats stats = CharacterStats.create(100, 50, 30, 50);

            assertThat(stats.toString())
                .contains("100")
                .contains("50")
                .contains("30");
        }
    }
}
