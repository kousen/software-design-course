package edu.trincoll;

/**
 * Bank account for demonstrating TDD with state and exceptions.
 */
public class BankAccount {
    private final String accountNumber;
    private final String owner;
    private double balance;

    public BankAccount(String accountNumber, String owner) {
        this(accountNumber, owner, 0.0);
    }

    public BankAccount(String accountNumber, String owner, double initialBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner cannot be null or blank");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + " from account with balance " + balance);
        }
        balance -= amount;
    }

    public void transferTo(BankAccount target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        withdraw(amount);
        target.deposit(amount);
    }
}
