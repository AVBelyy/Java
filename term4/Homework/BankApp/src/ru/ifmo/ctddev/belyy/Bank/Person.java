package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Person extends Remote {
    public String getName() throws RemoteException;

    public String getSurname() throws RemoteException;

    public String getPassportId() throws RemoteException;

    public Account addAccount(String accountId) throws RemoteException;

    public Account getAccount(String accountId) throws RemoteException;
}
