package edu.trincoll.game.player;

import edu.trincoll.game.command.AttackCommand;
import edu.trincoll.game.command.GameCommand;
import edu.trincoll.game.command.HealCommand;
import edu.trincoll.game.model.Character;

import java.util.List;
import java.util.Scanner;

/**
 * Human player implementation that reads input from console.
 * <p>
 * Design Pattern: STRATEGY (concrete strategy)
 * <p>
 * - Implements Player interface
 * - Uses console I/O for decision-making
 * - Interchangeable with AI players
 */
public class HumanPlayer implements Player {
    private final Scanner scanner;

    public HumanPlayer() {
        this.scanner = new Scanner(System.in);
    }

    // Constructor for testing with custom scanner
    public HumanPlayer(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public GameCommand decideAction(Character self,
                                   List<Character> allies,
                                   List<Character> enemies,
                                   GameState gameState) {
        displayGameState(self, allies, enemies, gameState);

        while (true) {
            System.out.println("\nYour turn, " + self.getName() + "!");
            System.out.println("1. Attack an enemy");
            System.out.println("2. Heal an ally");
            System.out.print("Choose action (1-2): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                return switch (choice) {
                    case 1 -> chooseAttackTarget(self, enemies);
                    case 2 -> chooseHealTarget(allies);
                    default -> {
                        System.out.println("Invalid choice. Please try again.");
                        yield null;
                    }
                };
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private GameCommand chooseAttackTarget(Character self, List<Character> enemies) {
        Character target = chooseCharacter("attack", enemies);
        if (target != null) {
            return new AttackCommand(self, target);
        }
        return null;
    }

    private GameCommand chooseHealTarget(List<Character> allies) {
        Character target = chooseCharacter("heal", allies);
        if (target != null) {
            return new HealCommand(target, 30);
        }
        return null;
    }

    private Character chooseCharacter(String action, List<Character> characters) {
        System.out.println("\nAvailable targets to " + action + ":");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.printf("%d. %s (%s) - HP: %d/%d%n",
                i + 1,
                c.getName(),
                c.getType(),
                c.getStats().health(),
                c.getStats().maxHealth());
        }

        System.out.print("Choose target (1-" + characters.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= characters.size()) {
                return characters.get(choice - 1);
            } else {
                System.out.println("Invalid target. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return null;
    }

    private void displayGameState(Character self, List<Character> allies,
                                 List<Character> enemies, GameState gameState) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TURN " + gameState.turnNumber() + " - ROUND " + gameState.roundNumber());
        System.out.println("=".repeat(60));

        System.out.println("\nYour Team:");
        for (Character ally : allies) {
            displayCharacter(ally, ally == self);
        }

        System.out.println("\nEnemy Team:");
        for (Character enemy : enemies) {
            displayCharacter(enemy, false);
        }
    }

    private void displayCharacter(Character c, boolean isSelf) {
        String marker = isSelf ? " (YOU)" : "";
        System.out.printf("  %s (%s)%s - HP: %d/%d, Mana: %d/%d%n",
            c.getName(),
            c.getType(),
            marker,
            c.getStats().health(),
            c.getStats().maxHealth(),
            c.getStats().mana(),
            c.getStats().maxMana());
    }
}
