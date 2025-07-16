package edu.trincoll;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Lifecycle Example")
public class LifecycleTest {
    
    private static int classLevelCounter;
    private int instanceLevelCounter;
    
    @BeforeAll
    static void initAll() {
        classLevelCounter = 0;
        System.out.println("BeforeAll: Setting up class-level resources");
    }
    
    @BeforeEach
    void init() {
        instanceLevelCounter = 0;
        System.out.println("BeforeEach: Setting up test instance");
    }
    
    @Test
    @DisplayName("First test method")
    void testOne() {
        classLevelCounter++;
        instanceLevelCounter++;
        
        assertThat(classLevelCounter).isEqualTo(1);
        assertThat(instanceLevelCounter).isEqualTo(1);
        
        System.out.println("Test 1: classLevelCounter=" + classLevelCounter + 
                         ", instanceLevelCounter=" + instanceLevelCounter);
    }
    
    @Test
    @DisplayName("Second test method")
    void testTwo() {
        classLevelCounter++;
        instanceLevelCounter++;
        
        assertThat(classLevelCounter).isEqualTo(2);
        assertThat(instanceLevelCounter).isEqualTo(1);
        
        System.out.println("Test 2: classLevelCounter=" + classLevelCounter + 
                         ", instanceLevelCounter=" + instanceLevelCounter);
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("AfterEach: Cleaning up test instance");
    }
    
    @AfterAll
    static void tearDownAll() {
        System.out.println("AfterAll: Cleaning up class-level resources");
        System.out.println("Final classLevelCounter: " + classLevelCounter);
    }
}