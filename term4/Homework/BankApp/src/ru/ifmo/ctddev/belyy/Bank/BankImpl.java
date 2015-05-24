package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.rmi.*;

public class BankImpl implements Bank {
    private final Map<String, RemotePerson> persons;
    private final int port;

    public BankImpl(final int port) {
        this.port = port;
        this.persons = new HashMap<String, RemotePerson>();
    }

    public Person createPerson(String name, String surname, String passportId, boolean isLocal) throws RemoteException {
        if (isLocal) {
            return new LocalPerson(name, surname, passportId);
        } else {
            synchronized (this) {
                RemotePerson person = new RemotePerson(name, surname, passportId, port);
                persons.put(passportId, person);
                UnicastRemoteObject.exportObject(person, port);
                return person;
            }
        }
    }

    public Person getPerson(String passportId, boolean isLocal) throws RemoteException {
        RemotePerson person = persons.get(passportId);
        if (person == null) {
            return null;
        }
        if (isLocal) {
            return new LocalPerson(person);
        } else {
            return person;
        }
    }
}
