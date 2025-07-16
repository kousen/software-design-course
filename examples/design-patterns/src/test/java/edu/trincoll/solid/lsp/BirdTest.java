package edu.trincoll.solid.lsp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BirdTest {
    
    @Test
    void eagleIsFlyingBird() {
        Bird eagle = new Eagle();
        
        assertAll(
            () -> assertThat(eagle.getName()).isEqualTo("Eagle"),
            () -> assertThat(eagle.canFly()).isTrue(),
            () -> assertThat(eagle).isInstanceOf(FlyingBird.class)
        );
    }
    
    @Test
    void sparrowIsFlyingBird() {
        Bird sparrow = new Sparrow();
        
        assertAll(
            () -> assertThat(sparrow.getName()).isEqualTo("Sparrow"),
            () -> assertThat(sparrow.canFly()).isTrue(),
            () -> assertThat(sparrow).isInstanceOf(FlyingBird.class)
        );
    }
    
    @Test
    void penguinIsNotFlyingBird() {
        Bird penguin = new Penguin();
        
        assertAll(
            () -> assertThat(penguin.getName()).isEqualTo("Penguin"),
            () -> assertThat(penguin.canFly()).isFalse(),
            () -> assertThat(penguin).isNotInstanceOf(FlyingBird.class)
        );
    }
    
    @Test
    void flyingBirdsCanFly() {
        FlyingBird[] flyingBirds = {
            new Eagle(),
            new Sparrow()
        };
        
        for (FlyingBird bird : flyingBirds) {
            assertThat(bird.canFly()).isTrue();
        }
    }
    
    @Test
    void allBirdsCanMoveAndEatAndSleep() {
        Bird[] birds = {
            new Eagle(),
            new Sparrow(),
            new Penguin()
        };
        
        for (Bird bird : birds) {
            assertThat(bird.getName()).isNotNull();
            assertThat(bird.canFly()).isNotNull();
            // These methods would be tested with mocking in real scenarios
            // but for demonstration purposes, we're verifying they exist
            bird.move();
            bird.eat();
            bird.sleep();
        }
    }
    
    @Test
    void eagleCanHunt() {
        Eagle eagle = new Eagle();
        
        assertThat(eagle.canFly()).isTrue();
        eagle.hunt(); // Should not throw exception
    }
    
    @Test
    void sparrowCanChirp() {
        Sparrow sparrow = new Sparrow();
        
        assertThat(sparrow.canFly()).isTrue();
        sparrow.chirp(); // Should not throw exception
    }
    
    @Test
    void penguinCanSwim() {
        Penguin penguin = new Penguin();
        
        assertThat(penguin.canFly()).isFalse();
        penguin.swim(); // Should not throw exception
    }
}