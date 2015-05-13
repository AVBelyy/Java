package ru.ifmo.ctddev.belyy.Bank;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;

public class BankImpl implements Bank {
    private final Map<String, Person> persons;
    private final int port;

    public BankImpl(final int port) {
        this.port = port;
        this.persons = new HashMap<String, Person>();
    }

    public Person createPerson(String name, String surname, String passportId) throws RemoteException {
        Person person = new RemotePerson(name, surname, passportId, port);
        UnicastRemoteObject.exportObject(person, port);
        persons.put(passportId, person);
        return person;
    }

    public Person getPerson(String passportId, boolean isLocal) throws RemoteException {
        Person person = persons.get(passportId);
        if (person == null) {
            return null;
        }
        if (isLocal) {
            return new LocalPerson(person.getName(), person.getSurname(), person.getPassportId());
        } else {
            return person;
        }
    }
}
