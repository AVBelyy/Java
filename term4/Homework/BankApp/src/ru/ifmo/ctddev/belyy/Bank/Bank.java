package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.*;

/**
 * RMI interface to bank.
 */
public interface Bank extends Remote {
    /**
     * Creates a new person in bank.
     *
     * @param name name of a new person.
     * @param surname surname of a new person.
     * @param passportId passport id of a new person.
     * @param isLocal flag specifying whether a person is going to be local.
     * @return new person with no accounts.
     * @throws RemoteException on any server exception.
     */
    public Person createPerson(String name, String surname, String passportId, boolean isLocal) throws RemoteException;

    /**
     * Returns specific person by their passport id.
     *
     * @param passportId passport id of desired person.
     * @param isLocal flag specifying whether a person is going to be local.
     * @return remote reference to, or serialized representation of desired person.
     * @throws RemoteException on any server exception.
     */
    public Person getPerson(String passportId, boolean isLocal) throws RemoteException;
}
