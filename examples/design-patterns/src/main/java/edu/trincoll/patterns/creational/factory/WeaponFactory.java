package edu.trincoll.patterns.creational.factory;

import edu.trincoll.patterns.creational.factory.Weapon.WeaponType;

/**
 * Factory Method pattern - Encapsulates object creation.
 *
 * Benefits:
 * - Hides complex construction
 * - Provides sensible defaults
 * - Open-Closed Principle (add new types without modifying code)
 * - Clear, named methods instead of constructors
 */
public class WeaponFactory {

    /**
     * Creates a sword - high damage, short range melee weapon.
     */
    public static Weapon createSword(String name) {
        return new Weapon(name, WeaponType.SWORD, 50, 1);
    }

    /**
     * Creates a bow - medium damage, long range weapon.
     */
    public static Weapon createBow(String name) {
        return new Weapon(name, WeaponType.BOW, 35, 20);
    }

    /**
     * Creates a staff - magical weapon with medium damage and range.
     */
    public static Weapon createStaff(String name) {
        return new Weapon(name, WeaponType.STAFF, 40, 15);
    }

    /**
     * Creates a dagger - low damage, short range, fast weapon.
     */
    public static Weapon createDagger(String name) {
        return new Weapon(name, WeaponType.DAGGER, 30, 1);
    }

    /**
     * Generic factory method using switch expression (Java 21).
     */
    public static Weapon createWeapon(String name, WeaponType type) {
        return switch (type) {
            case SWORD -> createSword(name);
            case BOW -> createBow(name);
            case STAFF -> createStaff(name);
            case DAGGER -> createDagger(name);
        };
    }
}
