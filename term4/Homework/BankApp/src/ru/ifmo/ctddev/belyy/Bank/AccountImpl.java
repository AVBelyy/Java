package ru.ifmo.ctddev.belyy.Bank;

import java.util.concurrent.atomic.AtomicLong;

public class AccountImpl implements Account {
    private final String id;
    private AtomicLong amount;

    public AccountImpl(String id) {
        this.id = id;
        amount = new AtomicLong(0);
    }

    public String getId() {
        return id;
    }

    public long getAmount() {
        return amount.get();
    }

    public void setAmount(long amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount.set(amount);
    }
}
