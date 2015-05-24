package ru.ifmo.ctddev.belyy.Bank;

public class LocalPerson extends AbstractPerson {
    public LocalPerson(String name, String surname, String passportId) {
        super(name, surname, passportId, 0);
    }

    public LocalPerson(AbstractPerson other) {
        super(other);
    }
}
