package edu.trincoll.game.model;

import edu.trincoll.game.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Character Tests")
class CharacterTest {

    private CharacterStats warriorStats;
    private CharacterStats mageStats;
    private AttackStrategy meleeStrategy;
    private DefenseStrategy heavyArmorStrategy;
    private DefenseStrategy standardDefenseStrategy;

    @BeforeEach
    void setUp() {
        warriorStats = CharacterStats.create(150, 40, 30, 0);
        mageStats = CharacterStats.create(80, 60, 10, 100);
        meleeStrategy = new MeleeAttackStrategy();
        heavyArmorStrategy = new HeavyArmorDefenseStrategy();
        standardDefenseStrategy = new StandardDefenseStrategy();
    }

    @Nested
    @DisplayName("Constructor Validation")
    class ConstructorValidation {

        @Test
        @DisplayName("Should create valid character with all required fields")
        void shouldCreateValidCharacter() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            assertThat(warrior.getName()).isEqualTo("Conan");
            assertThat(warrior.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(warrior.getStats()).isEqualTo(warriorStats);
            assertThat(warrior.getAttackStrategy()).isEqualTo(meleeStrategy);
            assertThat(warrior.getDefenseStrategy()).isEqualTo(heavyArmorStrategy);
        }

        @Test
        @DisplayName("Should throw exception for null name")
        void shouldRejectNullName() {
            assertThatThrownBy(() -> new Character(null, CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name cannot be null");
        }

        @Test
        @DisplayName("Should throw exception for null type")
        void shouldRejectNullType() {
            assertThatThrownBy(() -> new Character("Conan", null,
                warriorStats, meleeStrategy, heavyArmorStrategy))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Type cannot be null");
        }

        @Test
        @DisplayName("Should throw exception for null stats")
        void shouldRejectNullStats() {
            assertThatThrownBy(() -> new Character("Conan", CharacterType.WARRIOR,
                null, meleeStrategy, heavyArmorStrategy))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Stats cannot be null");
        }

        @Test
        @DisplayName("Should throw exception for null attack strategy")
        void shouldRejectNullAttackStrategy() {
            assertThatThrownBy(() -> new Character("Conan", CharacterType.WARRIOR,
                warriorStats, null, heavyArmorStrategy))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Attack strategy cannot be null");
        }

        @Test
        @DisplayName("Should throw exception for null defense strategy")
        void shouldRejectNullDefenseStrategy() {
            assertThatThrownBy(() -> new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Defense strategy cannot be null");
        }
    }

    @Nested
    @DisplayName("Strategy Pattern - Runtime Strategy Changes")
    class StrategyChanges {

        @Test
        @DisplayName("Should allow changing attack strategy at runtime")
        void shouldAllowAttackStrategyChange() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            AttackStrategy newStrategy = new MagicAttackStrategy();
            warrior.setAttackStrategy(newStrategy);

            assertThat(warrior.getAttackStrategy()).isEqualTo(newStrategy);
        }

        @Test
        @DisplayName("Should allow changing defense strategy at runtime")
        void shouldAllowDefenseStrategyChange() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            warrior.setDefenseStrategy(standardDefenseStrategy);

            assertThat(warrior.getDefenseStrategy()).isEqualTo(standardDefenseStrategy);
        }

        @Test
        @DisplayName("Should throw exception when setting null attack strategy")
        void shouldRejectNullAttackStrategyChange() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            assertThatThrownBy(() -> warrior.setAttackStrategy(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Attack strategy cannot be null");
        }

        @Test
        @DisplayName("Should throw exception when setting null defense strategy")
        void shouldRejectNullDefenseStrategyChange() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            assertThatThrownBy(() -> warrior.setDefenseStrategy(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Defense strategy cannot be null");
        }
    }

    @Nested
    @DisplayName("Health Management")
    class HealthManagement {

        @Test
        @DisplayName("Should heal character up to max health")
        void shouldHealCharacter() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats.withHealth(50), meleeStrategy, heavyArmorStrategy);

            warrior.heal(30);

            assertThat(warrior.getStats().health()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should not heal beyond max health")
        void shouldCapHealingAtMaxHealth() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats.withHealth(140), meleeStrategy, heavyArmorStrategy);

            warrior.heal(50);

            assertThat(warrior.getStats().health()).isEqualTo(150); // Max health
        }

        @Test
        @DisplayName("Should directly set health")
        void shouldSetHealthDirectly() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            warrior.setHealth(75);

            assertThat(warrior.getStats().health()).isEqualTo(75);
        }

        @Test
        @DisplayName("Should check if character is alive")
        void shouldCheckIfAlive() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            assertThat(warrior.isAlive()).isTrue();
            assertThat(warrior.isDead()).isFalse();
        }

        @Test
        @DisplayName("Should check if character is dead")
        void shouldCheckIfDead() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats.withHealth(0), meleeStrategy, heavyArmorStrategy);

            assertThat(warrior.isAlive()).isFalse();
            assertThat(warrior.isDead()).isTrue();
        }
    }

    @Nested
    @DisplayName("Mana Management")
    class ManaManagement {

        @Test
        @DisplayName("Should use mana when enough available")
        void shouldUseMana() {
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefenseStrategy);

            mage.useMana(30);

            assertThat(mage.getStats().mana()).isEqualTo(70);
        }

        @Test
        @DisplayName("Should throw exception when not enough mana")
        void shouldThrowExceptionWhenNotEnoughMana() {
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats.withMana(20), new MagicAttackStrategy(), standardDefenseStrategy);

            assertThatThrownBy(() -> mage.useMana(30))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Not enough mana");
        }

        @Test
        @DisplayName("Should restore mana up to max")
        void shouldRestoreMana() {
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats.withMana(50), new MagicAttackStrategy(), standardDefenseStrategy);

            mage.restoreMana(30);

            assertThat(mage.getStats().mana()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should not restore beyond max mana")
        void shouldCapManaAtMax() {
            Character mage = new Character("Gandalf", CharacterType.MAGE,
                mageStats.withMana(90), new MagicAttackStrategy(), standardDefenseStrategy);

            mage.restoreMana(30);

            assertThat(mage.getStats().mana()).isEqualTo(100); // Max mana
        }
    }

    @Nested
    @DisplayName("Equals and HashCode")
    class EqualsAndHashCode {

        @Test
        @DisplayName("Should be equal when name and type match")
        void shouldBeEqualWhenNameAndTypeMatch() {
            Character warrior1 = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);
            Character warrior2 = new Character("Conan", CharacterType.WARRIOR,
                mageStats, new MagicAttackStrategy(), standardDefenseStrategy);

            assertThat(warrior1).isEqualTo(warrior2);
            assertThat(warrior1.hashCode()).isEqualTo(warrior2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when name differs")
        void shouldNotBeEqualWhenNameDiffers() {
            Character warrior1 = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);
            Character warrior2 = new Character("Aragorn", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            assertThat(warrior1).isNotEqualTo(warrior2);
        }

        @Test
        @DisplayName("Should not be equal when type differs")
        void shouldNotBeEqualWhenTypeDiffers() {
            Character warrior = new Character("Hero", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);
            Character mage = new Character("Hero", CharacterType.MAGE,
                mageStats, new MagicAttackStrategy(), standardDefenseStrategy);

            assertThat(warrior).isNotEqualTo(mage);
        }
    }

    @Nested
    @DisplayName("ToString")
    class ToString {

        @Test
        @DisplayName("Should have meaningful toString")
        void shouldHaveMeaningfulToString() {
            Character warrior = new Character("Conan", CharacterType.WARRIOR,
                warriorStats, meleeStrategy, heavyArmorStrategy);

            String toString = warrior.toString();

            assertThat(toString)
                .contains("Conan")
                .contains("WARRIOR")
                .contains("150") // HP
                .contains("40")  // ATK
                .contains("30"); // DEF
        }
    }

    @Nested
    @DisplayName("Builder Pattern")
    class BuilderPattern {

        @Test
        @DisplayName("Should create builder instance")
        void shouldCreateBuilder() {
            Character.Builder builder = Character.builder();

            assertThat(builder).isNotNull();
        }

        @Test
        @DisplayName("Builder methods should return builder for chaining")
        void shouldSupportMethodChaining() {
            Character.Builder builder = Character.builder()
                .name("Conan")
                .type(CharacterType.WARRIOR)
                .stats(warriorStats)
                .attackStrategy(meleeStrategy)
                .defenseStrategy(heavyArmorStrategy);

            assertThat(builder).isNotNull();
        }
    }
}
