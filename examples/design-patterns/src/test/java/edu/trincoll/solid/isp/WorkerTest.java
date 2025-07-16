package edu.trincoll.solid.isp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class WorkerTest {
    
    @Test
    void humanWorkerImplementsAllInterfaces() {
        HumanWorker worker = new HumanWorker("John");
        
        assertAll(
            () -> assertThat(worker).isInstanceOf(Workable.class),
            () -> assertThat(worker).isInstanceOf(Eatable.class),
            () -> assertThat(worker).isInstanceOf(Sleepable.class),
            () -> assertThat(worker.getName()).isEqualTo("John")
        );
    }
    
    @Test
    void robotOnlyImplementsWorkable() {
        Robot robot = new Robot("R2D2");
        
        assertAll(
            () -> assertThat(robot).isInstanceOf(Workable.class),
            () -> assertThat(robot).isNotInstanceOf(Eatable.class),
            () -> assertThat(robot).isNotInstanceOf(Sleepable.class),
            () -> assertThat(robot.getModel()).isEqualTo("R2D2")
        );
    }
    
    @Test
    void workableInterfaceIsSegregated() {
        Workable[] workers = {
            new HumanWorker("Alice"),
            new Robot("C3PO")
        };
        
        for (Workable worker : workers) {
            worker.work(); // Should not throw exception
        }
    }
    
    @Test
    void humanWorkerCanEat() {
        HumanWorker worker = new HumanWorker("Bob");
        
        assertThat(worker).isInstanceOf(Eatable.class);
        worker.eat(); // Should not throw exception
    }
    
    @Test
    void humanWorkerCanSleep() {
        HumanWorker worker = new HumanWorker("Carol");
        
        assertThat(worker).isInstanceOf(Sleepable.class);
        worker.sleep(); // Should not throw exception
    }
    
    @Test
    void robotCanWork() {
        Robot robot = new Robot("WALL-E");
        
        assertThat(robot).isInstanceOf(Workable.class);
        robot.work(); // Should not throw exception
    }
}