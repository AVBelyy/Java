package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.Remote;

public class RemotePerson extends AbstractPerson implements Remote {
    public RemotePerson(String name, String surname, String passportId, int port) {
        super(name, surname, passportId, port);
    }
}
