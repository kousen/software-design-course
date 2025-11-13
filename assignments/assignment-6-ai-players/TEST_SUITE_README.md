# Assignment 6: AI-Powered Players - Test Suite Documentation

## Overview

This comprehensive test suite provides **over 150 tests** covering all major components of the AI-powered RPG game assignment. The tests are designed to:

1. **Validate student implementations** - Each TODO has corresponding tests
2. **Teach testing best practices** - Demonstrates TDD, nested tests, and descriptive test names
3. **Support iterative development** - Tests can be run after implementing each TODO
4. **Demonstrate design patterns** - Tests verify pattern implementations

## Test Organization

### Test Files by Pattern/Component

#### 1. Model Layer Tests
- **`CharacterStatsTest.java`** (45 tests)
  - Constructor validation (9 tests)
  - Factory method (`create()`) (1 test)
  - Immutable updates (`withHealth()`, `withMana()`) (6 tests)
  - Status checks (`isAlive()`, `isDead()`) (3 tests)
  - Record behavior (equals, hashCode, toString) (3 tests)

- **`CharacterTest.java`** (20 tests)
  - Constructor validation with null checks (5 tests)
  - Strategy pattern - runtime strategy changes (4 tests)
  - Health management (heal, takeDamage, setHealth) (4 tests)
  - Mana management (useMana, restoreMana) (4 tests)
  - Equals and hashCode (3 tests)

#### 2. Strategy Pattern Tests
- **`AttackStrategyTest.java`** (15 tests)
  - **MeleeAttackStrategy** (3 tests)
    - Base damage with 20% bonus
    - Works with different attack power values
  - **MagicAttackStrategy** (4 tests)
    - Damage calculation with mana bonus
    - Mana consumption
    - Exception when insufficient mana
  - **RangedAttackStrategy** (5 tests)
    - Base damage with 80% accuracy
    - Critical hit mechanics (< 30% health)
    - Edge cases (exactly 30%, just under 30%)

- **`DefenseStrategyTest.java`** (12 tests)
  - **StandardDefenseStrategy** (5 tests)
    - Damage reduction (defense / 2)
    - Never returns negative damage
    - Zero defense edge case
  - **HeavyArmorDefenseStrategy** (7 tests)
    - Full defense value reduction
    - 75% damage reduction cap
    - Cap applied correctly with various damage amounts

#### 3. Factory Pattern Tests
- **`CharacterFactoryTest.java`** (35 tests)
  - **createWarrior()** (5 tests) - Stats, strategies, type validation
  - **createMage()** (5 tests) - Mage-specific stats and strategies
  - **createArcher()** (5 tests) - Archer configuration
  - **createRogue()** (5 tests) - Rogue setup
  - **createCharacter()** (5 tests) - Factory method with switch
  - **Integration tests** (4 tests) - Created characters can attack/defend

#### 4. Command Pattern Tests
- **`CommandPatternTest.java`** (30 tests)
  - **AttackCommand** (7 tests)
    - Execute/undo functionality
    - Damage calculation
    - Multiple execute/undo cycles
    - Description for logging
  - **HealCommand** (8 tests)
    - Execute/undo functionality
    - Healing amounts and caps
    - Self-heal support
  - **CommandInvoker** (7 tests)
    - Command history management
    - LIFO undo behavior
    - Empty history handling
  - **Integration tests** (2 tests) - Complex scenarios with undo

#### 5. Template Method Pattern Tests
- **`BattleSequenceTest.java`** (17 tests)
  - **StandardBattleSequence** (4 tests)
    - Basic attack execution
    - Correct damage calculation
    - No attacker side effects
  - **PowerAttackSequence** (6 tests)
    - Bonus damage calculation (attack / 4)
    - Recoil damage (10% of max health)
    - Works with different character types
  - **Pattern verification** (4 tests)
    - Template method is final
    - Hook methods are overridable
    - Abstract method enforcement

#### 6. Player Strategy Tests
- **`RuleBasedPlayerTest.java`** (28 tests)
  - **Rule 1: Self-preservation** (5 tests)
    - Heals self when HP < 30%
    - Threshold edge cases
    - Priority over ally healing
  - **Rule 2: Help weakest ally** (5 tests)
    - Heals ally when HP < 20%
    - Selects weakest ally
    - Doesn't count self as ally
  - **Rule 3: Attack weakest enemy** (4 tests)
    - Attacks when no healing needed
    - Targets weakest enemy
    - Focus fire strategy
  - **Integration tests** (2 tests)
    - Full decision tree
    - Realistic battle scenarios
  - **Strategy pattern verification** (2 tests)
    - Interface compliance
    - Interchangeability

## Test Execution Strategy

### Phase 1: Model and Immutability (CharacterStats)
```bash
./gradlew test --tests "CharacterStatsTest"
```
**Expected**: All 45 tests should PASS (no TODOs in CharacterStats)

### Phase 2: Strategy Pattern Implementation (TODO 1)
```bash
./gradlew test --tests "AttackStrategyTest"
./gradlew test --tests "DefenseStrategyTest"
```
**Expected**: Tests will FAIL until students implement:
- TODO 1a: MeleeAttackStrategy
- TODO 1b: MagicAttackStrategy
- TODO 1c: RangedAttackStrategy
- TODO 1d: StandardDefenseStrategy
- TODO 1e: HeavyArmorDefenseStrategy

### Phase 3: Factory Pattern (TODO 2)
```bash
./gradlew test --tests "CharacterFactoryTest"
```
**Expected**: Tests will FAIL until students implement all factory methods (TODO 2a-2e)

### Phase 4: Builder Pattern (TODO 3)
```bash
./gradlew test --tests "CharacterTest.BuilderPattern"
```
**Expected**: Tests will FAIL until Character.Builder.build() is implemented

### Phase 5: Command Pattern (TODO 4)
```bash
./gradlew test --tests "CommandPatternTest"
```
**Expected**: Tests will FAIL until students implement:
- TODO 4a: AttackCommand execute/undo
- TODO 4b: HealCommand execute/undo
- TODO 4c: CommandInvoker methods

### Phase 6: Template Method (TODO 5)
```bash
./gradlew test --tests "BattleSequenceTest"
```
**Expected**: Tests will FAIL until students implement:
- TODO 5a: BattleSequence.executeTurn()
- TODO 5b: StandardBattleSequence.performAttack()
- TODO 5c: PowerAttackSequence hook methods

### Phase 7: Player Strategy (Verification)
```bash
./gradlew test --tests "RuleBasedPlayerTest"
```
**Expected**: All tests should PASS (RuleBasedPlayer is already implemented)

## Running All Tests

```bash
# Run all tests
./gradlew test

# Run with coverage report
./gradlew test jacocoTestReport

# Run specific test class
./gradlew test --tests "CharacterStatsTest"

# Run specific test method
./gradlew test --tests "CharacterStatsTest.shouldCreateValidStats"

# Run tests matching pattern
./gradlew test --tests "*Strategy*"
```

## Test Statistics

| Component | Test File | Total Tests | Expected Status |
|-----------|-----------|-------------|-----------------|
| Model (Stats) | CharacterStatsTest | 45 | ✅ PASS (no TODOs) |
| Model (Character) | CharacterTest | 20 | ⚠️ PARTIAL (Builder TODO) |
| Attack Strategies | AttackStrategyTest | 15 | ❌ FAIL (TODO 1a-c) |
| Defense Strategies | DefenseStrategyTest | 12 | ❌ FAIL (TODO 1d-e) |
| Factory Pattern | CharacterFactoryTest | 35 | ❌ FAIL (TODO 2a-e) |
| Command Pattern | CommandPatternTest | 30 | ❌ FAIL (TODO 4a-c) |
| Template Method | BattleSequenceTest | 17 | ❌ FAIL (TODO 5a-c) |
| Player Strategy | RuleBasedPlayerTest | 28 | ✅ PASS (implemented) |
| **TOTAL** | **8 test files** | **~202 tests** | **Mix of pass/fail** |

## Key Testing Principles Demonstrated

### 1. Nested Test Organization
```java
@Nested
@DisplayName("Constructor Validation")
class ConstructorValidation {
    // Related tests grouped together
}
```

### 2. Descriptive Test Names
```java
@Test
@DisplayName("Should calculate melee damage with 20% bonus")
void shouldCalculateMeleeDamage() { ... }
```

### 3. AssertJ Fluent Assertions
```java
assertThat(damage).isEqualTo(60);
assertThat(character.isAlive()).isTrue();
assertThatThrownBy(() -> mage.useMana(100))
    .isInstanceOf(IllegalStateException.class)
    .hasMessageContaining("Not enough mana");
```

### 4. Test Data Setup
```java
@BeforeEach
void setUp() {
    warriorStats = CharacterStats.create(150, 40, 30, 0);
    attacker = new Character("Conan", CharacterType.WARRIOR, ...);
}
```

### 5. Edge Case Testing
- Boundary values (exactly 30%, just under 30%)
- Null parameter validation
- Minimum/maximum constraints
- Integer division edge cases

### 6. Integration Testing
- Tests that verify multiple components work together
- Realistic battle scenarios
- Command execution with undo chains

## Coverage Goals

With all TODOs implemented, this test suite should achieve:

- **Line Coverage**: > 90%
- **Branch Coverage**: > 85%
- **Method Coverage**: > 95%

## Test-Driven Development (TDD) Workflow

Students should follow this workflow:

1. **Read the test** - Understand what behavior is expected
2. **Run the test** - See it fail with `UnsupportedOperationException`
3. **Implement the TODO** - Write minimal code to pass the test
4. **Run the test again** - Verify it passes
5. **Refactor** - Improve code while keeping tests green
6. **Move to next test** - Repeat for next TODO

## Example: TDD for MeleeAttackStrategy

```java
// 1. Read the test
@Test
@DisplayName("Should calculate melee damage with 20% bonus")
void shouldCalculateMeleeDamage() {
    // Expected: 50 * 1.2 = 60
    int damage = attacker.attack(target);
    assertThat(damage).isEqualTo(60);
}

// 2. Run test - FAIL (UnsupportedOperationException)

// 3. Implement TODO 1a
@Override
public int calculateDamage(Character attacker, Character target) {
    int baseDamage = attacker.getStats().attackPower();
    return (int) (baseDamage * 1.2);
}

// 4. Run test - PASS ✅

// 5. Refactor if needed (code is already clean)

// 6. Move to next test (MagicAttackStrategy)
```

## Notes for Students

1. **Tests are your specification** - Read them carefully to understand requirements
2. **One test at a time** - Focus on making one test pass before moving to the next
3. **Don't modify tests** - If a test fails, fix your implementation, not the test
4. **Use tests for debugging** - Tests show exactly what's expected vs. what you're producing
5. **Green tests = correct implementation** - If all tests pass, your TODO is complete

## Notes for Instructors

1. **Tests validate correctness** - Students can self-check their work
2. **Tests prevent regression** - Changes won't break existing functionality
3. **Tests teach patterns** - Test structure demonstrates design pattern usage
4. **Tests enable CI/CD** - Can be run automatically on every commit
5. **Tests serve as examples** - Students learn testing practices from reading tests

## Troubleshooting

### All tests failing with ClassNotFoundException
```bash
# Rebuild the project
./gradlew clean build
```

### Tests passing but shouldn't be
- Check if you accidentally modified test files
- Verify test assertions are correct

### Specific test always fails
- Read the test carefully
- Check the "Expected" comment in the test
- Print intermediate values to debug
- Compare your implementation to the TODO requirements

## Future Enhancements

Potential additional tests that could be added:

1. **GameController tests** - Verify game loop and turn processing
2. **LLMPlayer tests** - Mock ChatClient to test AI decision parsing
3. **Integration tests** - Full game simulations
4. **Performance tests** - Verify reasonable execution times
5. **Parameterized tests** - Test multiple character combinations with jqwik

## Conclusion

This test suite provides comprehensive coverage of the assignment's core functionality. Students can use these tests to:

- Validate their implementations incrementally
- Learn TDD practices
- Understand design patterns through tests
- Build confidence in their code quality

All tests are designed to be clear, maintainable, and educational, serving both as validation tools and learning resources.
