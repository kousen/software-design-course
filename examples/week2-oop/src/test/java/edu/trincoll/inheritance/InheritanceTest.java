package edu.trincoll.inheritance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

class InheritanceTest {
    
    @Nested
    @DisplayName("Employee Tests")
    class EmployeeTests {
        private Employee employee;
        
        @BeforeEach
        void setUp() {
            employee = new Employee("John Doe", "john@example.com", 60000);
        }
        
        @Test
        @DisplayName("Should calculate monthly pay correctly")
        void testCalculateMonthlyPay() {
            assertThat(employee.calculateMonthlyPay()).isEqualTo(5000.0);
        }
        
        @Test
        @DisplayName("Should calculate base bonus as 5% of salary")
        void testCalculateBonus() {
            assertThat(employee.calculateBonus()).isEqualTo(3000.0);
        }
        
        @Test
        @DisplayName("Should give raise correctly")
        void testGiveRaise() {
            employee.giveRaise(10);
            assertThat(employee.getSalary()).isEqualTo(66000.0);
        }
        
        @Test
        @DisplayName("Should reject invalid raise percentage")
        void testInvalidRaise() {
            assertThatThrownBy(() -> employee.giveRaise(-5))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("between 0 and 50");
            
            assertThatThrownBy(() -> employee.giveRaise(60))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        
        @Test
        @DisplayName("Should format employee info correctly")
        void testEmployeeInfo() {
            assertThat(employee.getEmployeeInfo())
                    .contains("John Doe")
                    .contains("Employee")
                    .contains("60000.00");
        }
    }
    
    @Nested
    @DisplayName("Manager Tests")
    class ManagerTests {
        private Manager manager;
        private Employee employee1;
        private Employee employee2;
        
        @BeforeEach
        void setUp() {
            manager = new Manager("Jane Smith", "jane@example.com", 80000, 15, "Engineering");
            employee1 = new Employee("Dev One", "dev1@example.com", 50000);
            employee2 = new Employee("Dev Two", "dev2@example.com", 55000);
        }
        
        @Test
        @DisplayName("Should calculate manager bonus with team size")
        void testManagerBonus() {
            manager.addTeamMember(employee1);
            manager.addTeamMember(employee2);
            
            double bonus = manager.calculateBonus();
            assertThat(bonus).isEqualTo(4000.0 + 12000.0 + 2000.0);
        }
        
        @Test
        @DisplayName("Should manage team members correctly")
        void testTeamManagement() {
            manager.addTeamMember(employee1);
            manager.addTeamMember(employee2);
            
            assertThat(manager.getTeamSize()).isEqualTo(2);
            assertThat(manager.getTeamMembers()).containsExactlyInAnyOrder(employee1, employee2);
            
            manager.removeTeamMember(employee1);
            assertThat(manager.getTeamSize()).isEqualTo(1);
        }
        
        @Test
        @DisplayName("Should prevent manager from managing themselves")
        void testCannotManageSelf() {
            assertThatThrownBy(() -> manager.addTeamMember(manager))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("cannot manage themselves");
        }
        
        @Test
        @DisplayName("Should calculate team total salary")
        void testTeamTotalSalary() {
            manager.addTeamMember(employee1);
            manager.addTeamMember(employee2);
            
            assertThat(manager.getTeamTotalSalary()).isEqualTo(105000.0);
        }
        
        @Test
        @DisplayName("Should give team raise")
        void testGiveTeamRaise() {
            manager.addTeamMember(employee1);
            manager.addTeamMember(employee2);
            
            manager.giveTeamRaise(10);
            
            assertThat(employee1.getSalary()).isCloseTo(55000.0, within(0.01));
            assertThat(employee2.getSalary()).isCloseTo(60500.0, within(0.01));
        }
    }
    
    @Nested
    @DisplayName("Developer Tests")
    class DeveloperTests {
        private Developer developer;
        
        @BeforeEach
        void setUp() {
            developer = new Developer("Bob Developer", "bob@example.com", 70000, "Java");
        }
        
        @Test
        @DisplayName("Should manage programming languages")
        void testProgrammingLanguages() {
            developer.addProgrammingLanguage("Python");
            developer.addProgrammingLanguage("JavaScript");
            
            assertThat(developer.getProgrammingLanguages())
                    .hasSize(3)
                    .contains("Java", "Python", "JavaScript");
            
            assertThat(developer.knowsLanguage("Python")).isTrue();
            assertThat(developer.knowsLanguage("Ruby")).isFalse();
        }
        
        @Test
        @DisplayName("Should calculate developer bonus with skills")
        void testDeveloperBonus() {
            developer.addProgrammingLanguage("Python");
            developer.addProgrammingLanguage("Go");
            developer.writeLinesOfCode(3000);
            developer.setHasOnCallDuty(true);
            
            double bonus = developer.calculateBonus();
            assertThat(bonus).isGreaterThan(8000.0).isLessThan(11000.0);
        }
        
        @Test
        @DisplayName("Should calculate productivity score")
        void testProductivityScore() {
            developer.addProgrammingLanguage("Python");
            developer.addProgrammingLanguage("Go");
            developer.writeLinesOfCode(5000);
            
            assertThat(developer.getProductivityScore()).isEqualTo(35.0);
        }
        
        @Test
        @DisplayName("Should not remove primary language")
        void testCannotRemovePrimaryLanguage() {
            assertThatThrownBy(() -> developer.removeProgrammingLanguage("Java"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Cannot remove primary language");
        }
    }
    
    @Nested
    @DisplayName("Executive Tests")
    class ExecutiveTests {
        private Executive executive;
        
        @BeforeEach
        void setUp() {
            executive = new Executive("CEO Smith", "ceo@example.com", 200000, 30, "Executive", 100000);
        }
        
        @Test
        @DisplayName("Should calculate executive bonus with stock options")
        void testExecutiveBonus() {
            double bonus = executive.calculateBonus();
            assertThat(bonus).isGreaterThan(75000.0);
        }
        
        @Test
        @DisplayName("Should manage stock options")
        void testStockOptions() {
            executive.vestStockOptions(50000);
            assertThat(executive.getStockOptions()).isEqualTo(150000.0);
            
            executive.exerciseStockOptions(30000);
            assertThat(executive.getStockOptions()).isEqualTo(120000.0);
        }
        
        @Test
        @DisplayName("Should not exercise more options than available")
        void testInvalidStockExercise() {
            assertThatThrownBy(() -> executive.exerciseStockOptions(200000))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Cannot exercise more options than available");
        }
        
        @Test
        @DisplayName("Should manage board memberships")
        void testBoardMemberships() {
            executive.joinBoard("TechCorp");
            executive.joinBoard("FinanceInc");
            
            assertThat(executive.getBoardMemberships())
                    .hasSize(2)
                    .contains("TechCorp", "FinanceInc");
            
            executive.leaveBoard("TechCorp");
            assertThat(executive.getBoardMemberships())
                    .hasSize(1)
                    .contains("FinanceInc");
        }
    }
    
    @Nested
    @DisplayName("Polymorphism Tests")
    class PolymorphismTests {
        
        @Test
        @DisplayName("Should demonstrate polymorphic behavior")
        void testPolymorphism() {
            List<Employee> employees = List.of(
                    new Employee("Regular", "reg@example.com", 50000),
                    new Manager("Manager", "mgr@example.com", 80000, 15, "Sales"),
                    new Developer("Developer", "dev@example.com", 70000, "Java"),
                    new Executive("Executive", "exec@example.com", 200000, 30, "C-Suite", 100000)
            );
            
            employees.forEach(emp -> {
                assertThat(emp.calculateMonthlyPay()).isPositive();
                assertThat(emp.calculateBonus()).isPositive();
                assertThat(emp.getEmployeeInfo()).isNotEmpty();
            });
            
            assertThat(employees.get(0).calculateBonus()).isLessThan(employees.get(3).calculateBonus());
        }
        
        @Test
        @DisplayName("Should handle instanceof checks correctly")
        void testInstanceOf() {
            Employee emp = new Manager("Test", "test@example.com", 75000, 10, "IT");
            
            assertThat(emp).isInstanceOf(Employee.class);
            assertThat(emp).isInstanceOf(Manager.class);
            assertThat(emp).isNotInstanceOf(Developer.class);
            
            if (emp instanceof Manager manager) {
                manager.addTeamMember(new Employee("Member", "member@example.com", 50000));
                assertThat(manager.getTeamSize()).isEqualTo(1);
            }
        }
    }
}