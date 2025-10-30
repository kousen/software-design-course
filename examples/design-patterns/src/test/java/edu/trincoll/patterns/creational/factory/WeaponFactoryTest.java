package edu.trincoll.patterns.creational.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.trincoll.patterns.creational.factory.Weapon.WeaponType.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Factory Method Pattern Tests")
class WeaponFactoryTest {

    @Test
    @DisplayName("Create sword with correct stats")
    void createSword() {
        Weapon sword = WeaponFactory.createSword("Excalibur");

        assertThat(sword.name()).isEqualTo("Excalibur");
        assertThat(sword.type()).isEqualTo(SWORD);
        assertThat(sword.damage()).isEqualTo(50);
        assertThat(sword.range()).isEqualTo(1);
    }

    @Test
    @DisplayName("Create bow with correct stats")
    void createBow() {
        Weapon bow = WeaponFactory.createBow("Longbow");

        assertThat(bow.name()).isEqualTo("Longbow");
        assertThat(bow.type()).isEqualTo(BOW);
        assertThat(bow.damage()).isEqualTo(35);
        assertThat(bow.range()).isEqualTo(20);
    }

    @Test
    @DisplayName("Create staff with correct stats")
    void createStaff() {
        Weapon staff = WeaponFactory.createStaff("Elder Wand");

        assertThat(staff.name()).isEqualTo("Elder Wand");
        assertThat(staff.type()).isEqualTo(STAFF);
        assertThat(staff.damage()).isEqualTo(40);
        assertThat(staff.range()).isEqualTo(15);
    }

    @Test
    @DisplayName("Create dagger with correct stats")
    void createDagger() {
        Weapon dagger = WeaponFactory.createDagger("Shadow Blade");

        assertThat(dagger.name()).isEqualTo("Shadow Blade");
        assertThat(dagger.type()).isEqualTo(DAGGER);
        assertThat(dagger.damage()).isEqualTo(30);
        assertThat(dagger.range()).isEqualTo(1);
    }

    @Test
    @DisplayName("Generic factory creates correct weapon types")
    void genericFactoryCreatesCorrectTypes() {
        Weapon sword = WeaponFactory.createWeapon("Sword", SWORD);
        Weapon bow = WeaponFactory.createWeapon("Bow", BOW);

        assertThat(sword.type()).isEqualTo(SWORD);
        assertThat(bow.type()).isEqualTo(BOW);
    }
}
