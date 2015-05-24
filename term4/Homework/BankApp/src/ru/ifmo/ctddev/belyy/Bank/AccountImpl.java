package ru.ifmo.ctddev.belyy.Bank;

import java.math.BigDecimal;

public class AccountImpl implements Account {
    private final String id;
    private BigDecimal amount;

    public AccountImpl(String id) {
        this.id = id;
        this.amount = new BigDecimal(0);
    }

    public AccountImpl(AccountImpl other) {
        this.id = other.id;
        this.amount = other.amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void incAmount(BigDecimal deltaAmount) {
        synchronized (this) {
            System.out.println("Increasing amount of money for account " + id);
            amount = amount.add(deltaAmount);
        }
    }
}
