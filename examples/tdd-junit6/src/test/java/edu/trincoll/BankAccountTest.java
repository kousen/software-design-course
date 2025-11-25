package edu.trincoll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Nested test example demonstrating state-based testing with BankAccount.
 * Tests are organized hierarchically to build up context.
 */
@DisplayName("BankAccount")
class BankAccountTest {

    @Nested
    @DisplayName("when created")
    class WhenCreated {

        @Test
        @DisplayName("with valid parameters has correct initial state")
        void hasCorrectInitialState() {
            BankAccount account = new BankAccount("12345", "John Doe", 100.0);

            assertThat(account.getAccountNumber()).isEqualTo("12345");
            assertThat(account.getOwner()).isEqualTo("John Doe");
            assertThat(account.getBalance()).isEqualTo(100.0);
        }

        @Test
        @DisplayName("without initial balance defaults to zero")
        void defaultsToZeroBalance() {
            BankAccount account = new BankAccount("12345", "John Doe");

            assertThat(account.getBalance()).isZero();
        }

        @SuppressWarnings("DataFlowIssue")
        @Test
        @DisplayName("with null account number throws exception")
        void throwsForNullAccountNumber() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new BankAccount(null, "John Doe"))
                    .withMessageContaining("Account number");
        }

        @Test
        @DisplayName("with blank owner throws exception")
        void throwsForBlankOwner() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new BankAccount("12345", "  "))
                    .withMessageContaining("Owner");
        }

        @Test
        @DisplayName("with negative balance throws exception")
        void throwsForNegativeBalance() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new BankAccount("12345", "John Doe", -100.0))
                    .withMessageContaining("negative");
        }
    }

    @Nested
    @DisplayName("when depositing")
    class WhenDepositing {
        private BankAccount account;

        @BeforeEach
        void setUp() {
            account = new BankAccount("12345", "John Doe", 100.0);
        }

        @Test
        @DisplayName("positive amount increases balance")
        void increasesBalance() {
            account.deposit(50.0);

            assertThat(account.getBalance()).isEqualTo(150.0);
        }

        @Test
        @DisplayName("zero amount throws exception")
        void throwsForZeroDeposit() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> account.deposit(0))
                    .withMessageContaining("positive");
        }

        @Test
        @DisplayName("negative amount throws exception")
        void throwsForNegativeDeposit() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> account.deposit(-50.0))
                    .withMessageContaining("positive");
        }
    }

    @Nested
    @DisplayName("when withdrawing")
    class WhenWithdrawing {
        private BankAccount account;

        @BeforeEach
        void setUp() {
            account = new BankAccount("12345", "John Doe", 100.0);
        }

        @Test
        @DisplayName("valid amount decreases balance")
        void decreasesBalance() {
            account.withdraw(30.0);

            assertThat(account.getBalance()).isEqualTo(70.0);
        }

        @Test
        @DisplayName("exact balance results in zero balance")
        void canWithdrawExactBalance() {
            account.withdraw(100.0);

            assertThat(account.getBalance()).isZero();
        }

        @Test
        @DisplayName("amount exceeding balance throws InsufficientFundsException")
        void throwsForInsufficientFunds() {
            assertThatThrownBy(() -> account.withdraw(150.0))
                    .isInstanceOf(InsufficientFundsException.class)
                    .hasMessageContaining("150")
                    .hasMessageContaining("100");
        }

        @Test
        @DisplayName("zero amount throws exception")
        void throwsForZeroWithdrawal() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> account.withdraw(0))
                    .withMessageContaining("positive");
        }

        @Test
        @DisplayName("negative amount throws exception")
        void throwsForNegativeWithdrawal() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> account.withdraw(-50.0))
                    .withMessageContaining("positive");
        }
    }

    @Nested
    @DisplayName("when transferring")
    class WhenTransferring {
        private BankAccount source;
        private BankAccount target;

        @BeforeEach
        void setUp() {
            source = new BankAccount("11111", "Alice", 100.0);
            target = new BankAccount("22222", "Bob", 50.0);
        }

        @Test
        @DisplayName("valid transfer updates both accounts")
        void updatesBothAccounts() {
            source.transferTo(target, 30.0);

            assertThat(source.getBalance()).isEqualTo(70.0);
            assertThat(target.getBalance()).isEqualTo(80.0);
        }

        @Test
        @DisplayName("transfer to null account throws exception")
        void throwsForNullTarget() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> source.transferTo(null, 30.0))
                    .withMessageContaining("Target");
        }

        @Test
        @DisplayName("transfer exceeding balance throws InsufficientFundsException")
        void throwsForInsufficientFunds() {
            double originalTargetBalance = target.getBalance();

            assertThatThrownBy(() -> source.transferTo(target, 150.0))
                    .isInstanceOf(InsufficientFundsException.class);

            // Target balance should be unchanged
            assertThat(target.getBalance()).isEqualTo(originalTargetBalance);
        }
    }
}
