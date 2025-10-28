package edu.trincoll.game.demo;

import edu.trincoll.game.command.AttackCommand;
import edu.trincoll.game.command.CommandInvoker;
import edu.trincoll.game.command.HealCommand;
import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import edu.trincoll.game.strategy.MagicAttackStrategy;
import edu.trincoll.game.strategy.RangedAttackStrategy;
import edu.trincoll.game.template.PowerAttackSequence;
import edu.trincoll.game.template.StandardBattleSequence;

/**
 * Interactive demonstration of design patterns working together.
 *
 * This demo shows:
 * - Factory Method: Creating characters with appropriate defaults
 * - Builder Pattern: Flexible character construction
 * - Strategy Pattern: Swappable attack/defense behaviors
 * - Command Pattern: Executable actions with undo
 * - Template Method: Customizable battle sequences
 *
 * Run this class to see the patterns in action!
 */
public class GameDemo {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("DESIGN PATTERNS GAME DEMO");
        System.out.println("=".repeat(60));

        demoFactoryPattern();
        demoStrategyPattern();
        demoCommandPattern();
        demoTemplateMethodPattern();
        demoPatternsWorkingTogether();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Demo complete! All patterns working together.");
        System.out.println("=".repeat(60));
    }

    /**
     * Demo 1: Factory Method Pattern
     * Shows how factories create characters with appropriate defaults.
     */
    private static void demoFactoryPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMO 1: FACTORY METHOD PATTERN");
        System.out.println("=".repeat(60));
        System.out.println("Creating characters using factory methods...\n");

        // Factory creates characters with appropriate stats and strategies
        Character warrior = CharacterFactory.createWarrior("Conan");
        Character mage = CharacterFactory.createMage("Gandalf");
        Character archer = CharacterFactory.createArcher("Legolas");
        Character rogue = CharacterFactory.createRogue("Rogue");

        System.out.println("✓ " + warrior);
        System.out.println("  - High health, melee attack, heavy armor");

        System.out.println("\n✓ " + mage);
        System.out.println("  - High mana, magic attack, standard defense");

        System.out.println("\n✓ " + archer);
        System.out.println("  - Balanced stats, ranged attack with crits");

        System.out.println("\n✓ " + rogue);
        System.out.println("  - Agile stats, melee attack");

        System.out.println("\nFactory Pattern Benefits:");
        System.out.println("• Pre-configured characters with sensible defaults");
        System.out.println("• Open-Closed Principle: Add new types without changing existing code");
        System.out.println("• Encapsulates complex object creation");
    }

    /**
     * Demo 2: Strategy Pattern
     * Shows runtime strategy swapping and different attack behaviors.
     */
    private static void demoStrategyPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMO 2: STRATEGY PATTERN");
        System.out.println("=".repeat(60));
        System.out.println("Demonstrating interchangeable attack strategies...\n");

        Character attacker = CharacterFactory.createWarrior("Fighter");
        Character target = CharacterFactory.createWarrior("Target");

        System.out.println("Initial Setup:");
        System.out.println("  Attacker: " + attacker);
        System.out.println("  Target:   " + target);

        // Strategy 1: Melee Attack (default for warrior)
        System.out.println("\n1. Using MELEE attack strategy:");
        int damage1 = attacker.attack(target);
        System.out.println("   → Damage: " + damage1 + " (attack power × 1.2)");

        // Strategy 2: Change to Magic Attack at runtime
        // Note: Warriors have 0 mana, so let's use a mage for this demo
        System.out.println("\n2. Using MAGIC attack strategy (with a Mage):");
        Character mage = CharacterFactory.createMage("Wizard");
        int damage2 = mage.attack(target);
        System.out.println("   → Damage: " + damage2 + " (attack power + mana/10)");
        System.out.println("   → Mana before: 100, after: " + mage.getStats().mana());

        // Strategy 3: Change to Ranged Attack
        System.out.println("\n3. Switching to RANGED attack strategy:");
        attacker.setAttackStrategy(new RangedAttackStrategy());
        int damage3 = attacker.attack(target);
        System.out.println("   → Damage: " + damage3 + " (attack power × 0.8)");

        System.out.println("\nStrategy Pattern Benefits:");
        System.out.println("• Algorithms are interchangeable at runtime");
        System.out.println("• Open-Closed Principle: Add new strategies without modifying existing code");
        System.out.println("• Clean separation of concerns");
    }

    /**
     * Demo 3: Command Pattern
     * Shows executable actions with undo capability.
     */
    private static void demoCommandPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMO 3: COMMAND PATTERN");
        System.out.println("=".repeat(60));
        System.out.println("Demonstrating commands with undo...\n");

        Character hero = CharacterFactory.createWarrior("Hero");
        Character enemy = CharacterFactory.createWarrior("Enemy");
        CommandInvoker invoker = new CommandInvoker();

        System.out.println("Initial State:");
        System.out.println("  Hero:  " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");
        System.out.println("  Enemy: " + enemy.getStats().health() + "/" + enemy.getStats().maxHealth() + " HP");

        // Execute attack command
        System.out.println("\n→ Executing ATTACK command:");
        AttackCommand attack = new AttackCommand(hero, enemy);
        invoker.executeCommand(attack);
        System.out.println("  Hero:  " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");
        System.out.println("  Enemy: " + enemy.getStats().health() + "/" + enemy.getStats().maxHealth() + " HP (damaged!)");

        // Undo attack
        System.out.println("\n→ Undoing ATTACK command:");
        invoker.undoLastCommand();
        System.out.println("  Hero:  " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");
        System.out.println("  Enemy: " + enemy.getStats().health() + "/" + enemy.getStats().maxHealth() + " HP (restored!)");

        // Execute heal command
        System.out.println("\n→ Executing HEAL command:");
        hero.takeDamage(30); // Damage hero first
        System.out.println("  Hero damaged: " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");

        HealCommand heal = new HealCommand(hero, 20);
        invoker.executeCommand(heal);
        System.out.println("  Hero healed:  " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");

        // Undo heal
        System.out.println("\n→ Undoing HEAL command:");
        invoker.undoLastCommand();
        System.out.println("  Hero:  " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP (restored to before heal!)");

        System.out.println("\nCommand Pattern Benefits:");
        System.out.println("• Actions are first-class objects");
        System.out.println("• Undo/redo support");
        System.out.println("• Command history and logging");
        System.out.println("• Macro commands (combine multiple commands)");
    }

    /**
     * Demo 4: Template Method Pattern
     * Shows algorithm skeleton with customizable steps.
     */
    private static void demoTemplateMethodPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMO 4: TEMPLATE METHOD PATTERN");
        System.out.println("=".repeat(60));
        System.out.println("Demonstrating customizable battle sequences...\n");

        Character attacker = CharacterFactory.createWarrior("Attacker");
        Character defender = CharacterFactory.createWarrior("Defender");

        System.out.println("Initial State:");
        System.out.println("  Attacker: " + attacker.getStats().health() + "/" + attacker.getStats().maxHealth() + " HP");
        System.out.println("  Defender: " + defender.getStats().health() + "/" + defender.getStats().maxHealth() + " HP");

        // Standard battle sequence
        System.out.println("\n1. STANDARD Battle Sequence:");
        System.out.println("   Steps: begin → preAttack → attack → postAttack → end");
        StandardBattleSequence standardBattle = new StandardBattleSequence(attacker, defender);
        standardBattle.executeTurn();
        System.out.println("   Attacker: " + attacker.getStats().health() + "/" + attacker.getStats().maxHealth() + " HP");
        System.out.println("   Defender: " + defender.getStats().health() + "/" + defender.getStats().maxHealth() + " HP (damaged!)");

        // Reset for power attack demo
        defender.heal(100);
        int attackerHealthBefore = attacker.getStats().health();

        // Power attack sequence
        System.out.println("\n2. POWER ATTACK Battle Sequence:");
        System.out.println("   Steps: begin → power-up → powered attack → recoil → end");
        PowerAttackSequence powerBattle = new PowerAttackSequence(attacker, defender);
        powerBattle.executeTurn();
        System.out.println("   Attacker: " + attacker.getStats().health() + "/" + attacker.getStats().maxHealth() + " HP (recoil damage!)");
        System.out.println("   Defender: " + defender.getStats().health() + "/" + defender.getStats().maxHealth() + " HP (MORE damage!)");

        int recoilDamage = attackerHealthBefore - attacker.getStats().health();
        System.out.println("   Recoil taken: " + recoilDamage + " HP");

        System.out.println("\nTemplate Method Pattern Benefits:");
        System.out.println("• Algorithm structure is fixed (can't be changed)");
        System.out.println("• Individual steps can be customized (hooks)");
        System.out.println("• Code reuse through inheritance");
        System.out.println("• Inversion of Control (\"Hollywood Principle\")");
    }

    /**
     * Demo 5: All Patterns Working Together
     * Shows realistic game scenario using all patterns.
     */
    private static void demoPatternsWorkingTogether() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMO 5: ALL PATTERNS WORKING TOGETHER");
        System.out.println("=".repeat(60));
        System.out.println("A complete battle scenario...\n");

        // Factory: Create characters
        System.out.println("1. FACTORY creates characters:");
        Character hero = CharacterFactory.createWarrior("Hero");
        Character boss = CharacterFactory.createMage("Boss");
        System.out.println("   ✓ Hero (Warrior) vs Boss (Mage)");

        // Strategy: Show initial strategies
        System.out.println("\n2. STRATEGY determines behavior:");
        System.out.println("   ✓ Hero uses Melee attack, Heavy armor defense");
        System.out.println("   ✓ Boss uses Magic attack, Standard defense");

        // Command: Execute turn 1
        System.out.println("\n3. COMMAND executes Turn 1:");
        CommandInvoker invoker = new CommandInvoker();
        AttackCommand heroAttack = new AttackCommand(hero, boss);
        invoker.executeCommand(heroAttack);
        System.out.println("   ✓ Hero attacks Boss");
        System.out.println("     Boss: " + boss.getStats().health() + "/" + boss.getStats().maxHealth() + " HP");

        // Template Method: Boss's turn with power attack
        System.out.println("\n4. TEMPLATE METHOD executes Boss turn:");
        PowerAttackSequence bossTurn = new PowerAttackSequence(boss, hero);
        bossTurn.executeTurn();
        System.out.println("   ✓ Boss uses POWER ATTACK");
        System.out.println("     Hero: " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");
        System.out.println("     Boss: " + boss.getStats().health() + "/" + boss.getStats().maxHealth() + " HP (recoil)");

        // Command: Heal hero
        System.out.println("\n5. COMMAND heals Hero:");
        HealCommand heal = new HealCommand(hero, 25);
        invoker.executeCommand(heal);
        System.out.println("   ✓ Hero healed +25 HP");
        System.out.println("     Hero: " + hero.getStats().health() + "/" + hero.getStats().maxHealth() + " HP");

        // Show command history
        System.out.println("\n6. COMMAND maintains history:");
        System.out.println("   ✓ Commands executed: " + invoker.getCommandHistory().size());
        System.out.println("   ✓ Can undo last " + invoker.getCommandHistory().size() + " actions");

        System.out.println("\nPatterns Collaboration:");
        System.out.println("• FACTORY creates objects with sensible defaults");
        System.out.println("• BUILDER provides flexibility beyond factory");
        System.out.println("• STRATEGY makes behavior interchangeable");
        System.out.println("• COMMAND makes actions undoable");
        System.out.println("• TEMPLATE METHOD standardizes processes");
        System.out.println("\n→ Together they create flexible, maintainable code!");
    }
}
