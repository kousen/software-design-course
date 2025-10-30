package edu.trincoll.patterns.creational.factory;

/**
 * Product class for Factory Method pattern.
 */
public record Weapon(
    String name,
    WeaponType type,
    int damage,
    int range
) {
    public enum WeaponType {
        SWORD, BOW, STAFF, DAGGER
    }
}
