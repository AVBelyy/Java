package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.*;

public interface Bank extends Remote {
    public Person createPerson(String name, String surname, String passportId) throws RemoteException;

    public Person getPerson(String passportId, boolean isLocal) throws RemoteException;
}
