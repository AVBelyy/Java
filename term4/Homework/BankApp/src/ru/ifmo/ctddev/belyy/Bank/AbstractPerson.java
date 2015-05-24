package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractPerson implements Person {
    protected final String name;
    protected final String surname;
    protected final String passportId;
    protected final Map<String, AccountImpl> accounts;
    protected final int port;

    public AbstractPerson(String name, String surname, String passportId, int port) {
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
        this.accounts = new ConcurrentHashMap<String, AccountImpl>();
        this.port = port;
    }

    public AbstractPerson(AbstractPerson other) {
        this.name = other.name;
        this.surname = other.surname;
        this.passportId = other.passportId;
        this.accounts = new ConcurrentHashMap<String, AccountImpl>();
        synchronized (other.accounts) {
            for (Map.Entry<String, AccountImpl> kv : other.accounts.entrySet()) {
                this.accounts.put(kv.getKey(), new AccountImpl(kv.getValue()));
            }
        }
        this.port = other.port;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public String getSurname() throws RemoteException {
        return surname;
    }

    public Account getAccount(String accountId) throws RemoteException {
        return accounts.get(accountId);
    }

    public Account addAccount(String accountId) throws RemoteException {
        if (accounts.containsKey(accountId)) {
            throw new IllegalStateException();
        }

        AccountImpl account = new AccountImpl(accountId);
        accounts.put(accountId, account);
        if (this instanceof RemotePerson) {
            UnicastRemoteObject.exportObject(account, port);
        }
        return account;
    }
}
