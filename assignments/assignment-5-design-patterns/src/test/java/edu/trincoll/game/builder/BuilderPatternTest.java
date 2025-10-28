package edu.trincoll.game.builder;

import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterStats;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.strategy.MeleeAttackStrategy;
import edu.trincoll.game.strategy.StandardDefenseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Builder Pattern Tests")
class BuilderPatternTest {

    @Nested
    @DisplayName("Builder Construction")
    class BuilderConstructionTests {

        @Test
        @DisplayName("TODO 3: Builder creates character with all required fields")
        void testBuilderComplete() {
            Character character = Character.builder()
                .name("TestHero")
                .type(CharacterType.WARRIOR)
                .stats(CharacterStats.create(100, 50, 20, 0))
                .attackStrategy(new MeleeAttackStrategy())
                .defenseStrategy(new StandardDefenseStrategy())
                .build();

            assertThat(character.getName()).isEqualTo("TestHero");
            assertThat(character.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(character.getStats()).isNotNull();
            assertThat(character.getAttackStrategy()).isNotNull();
            assertThat(character.getDefenseStrategy()).isNotNull();
        }

        @Test
        @DisplayName("TODO 3: Builder throws exception when name is missing")
        void testBuilderMissingName() {
            assertThatThrownBy(() ->
                Character.builder()
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
             .hasMessageContaining("name");
        }

        @Test
        @DisplayName("TODO 3: Builder throws exception when type is missing")
        void testBuilderMissingType() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("TestHero")
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
             .hasMessageContaining("type");
        }

        @Test
        @DisplayName("TODO 3: Builder throws exception when stats are missing")
        void testBuilderMissingStats() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("TestHero")
                    .type(CharacterType.WARRIOR)
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
             .hasMessageContaining("stats");
        }

        @Test
        @DisplayName("TODO 3: Builder throws exception when attack strategy is missing")
        void testBuilderMissingAttackStrategy() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("TestHero")
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
             .hasMessageContaining("attackStrategy");
        }

        @Test
        @DisplayName("TODO 3: Builder throws exception when defense strategy is missing")
        void testBuilderMissingDefenseStrategy() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("TestHero")
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
             .hasMessageContaining("defenseStrategy");
        }
    }

    @Nested
    @DisplayName("Builder Fluent API")
    class BuilderFluentAPITests {

        @Test
        @DisplayName("Builder supports method chaining")
        void testBuilderChaining() {
            Character character = Character.builder()
                .name("ChainTest")
                .type(CharacterType.MAGE)
                .stats(CharacterStats.create(80, 60, 10, 100))
                .attackStrategy(new MeleeAttackStrategy())
                .defenseStrategy(new StandardDefenseStrategy())
                .build();

            assertThat(character).isNotNull();
        }

        @Test
        @DisplayName("Builder can set fields in any order")
        void testBuilderFieldOrder() {
            Character character1 = Character.builder()
                .defenseStrategy(new StandardDefenseStrategy())
                .stats(CharacterStats.create(100, 50, 20, 0))
                .attackStrategy(new MeleeAttackStrategy())
                .type(CharacterType.WARRIOR)
                .name("Order1")
                .build();

            Character character2 = Character.builder()
                .name("Order2")
                .type(CharacterType.WARRIOR)
                .stats(CharacterStats.create(100, 50, 20, 0))
                .attackStrategy(new MeleeAttackStrategy())
                .defenseStrategy(new StandardDefenseStrategy())
                .build();

            assertThat(character1).isNotNull();
            assertThat(character2).isNotNull();
        }
    }

    @Nested
    @DisplayName("Builder vs Factory Comparison")
    class BuilderVsFactoryTests {

        @Test
        @DisplayName("Builder allows custom configurations not possible with factory")
        void testBuilderCustomization() {
            // Builder allows creating warriors with mage-like stats
            Character customWarrior = Character.builder()
                .name("BattleMage")
                .type(CharacterType.WARRIOR)
                .stats(CharacterStats.create(120, 55, 25, 50)) // Custom stats
                .attackStrategy(new MeleeAttackStrategy())
                .defenseStrategy(new StandardDefenseStrategy())
                .build();

            assertThat(customWarrior.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(customWarrior.getStats().maxMana())
                .as("Custom warrior has mana")
                .isEqualTo(50);
        }
    }
}
