package ru.ifmo.ctddev.belyy.Bank;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalPerson implements Person, Serializable {
    private String name;
    private String surname;
    private String passportId;
    private Map<String, Account> accounts;

    public LocalPerson(String name, String surname, String passportId) {
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
        this.accounts = new ConcurrentHashMap<String, Account>();
    }

    public String getName() throws RemoteException {
        return name;
    }

    public String getSurname() throws RemoteException {
        return surname;
    }

    public String getPassportId() throws RemoteException {
        return passportId;
    }

    public Account getAccount(String accountId) throws RemoteException {
        return accounts.get(accountId);
    }

    public Account addAccount(String accountId) throws RemoteException {
        Account account;
        synchronized (this) {
            account = new AccountImpl(accountId);
            accounts.put(accountId, account);
        }
        return account;
    }
}
