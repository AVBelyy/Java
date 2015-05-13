package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RemotePerson implements Person {
    private String name;
    private String surname;
    private String passportId;
    private int port;
    private Map<String, Account> accounts;

    public RemotePerson(String name, String surname, String passportId, int port) {
        this.name = name;
        this.surname = surname;
        this.passportId = passportId;
        this.port = port;
        this.accounts = new HashMap<String, Account>();
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
        Account account = accounts.get(accountId);
        if (account == null) {
            return null;
        }
        return account;
    }

    public Account addAccount(String accountId) throws RemoteException {
        Account account = new AccountImpl(accountId);
        accounts.put(accountId, account);
        UnicastRemoteObject.exportObject(account, port);
        return account;
    }
}
