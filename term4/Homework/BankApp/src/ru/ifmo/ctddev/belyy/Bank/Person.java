package ru.ifmo.ctddev.belyy.Bank;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface to person in bank.
 */
public interface Person extends Remote, Serializable {
    /**
     * Returns the name of person.
     *
     * @return name of person.
     * @throws RemoteException on any server exception.
     */
    public String getName() throws RemoteException;

    /**
     * Returns the surname of person.
     *
     * @return surname of person.
     * @throws RemoteException on any server exception.
     */
    public String getSurname() throws RemoteException;

    /**
     * Creates new account with zero funds.
     *
     * @param accountId id for new account.
     * @return new account with zero funds.
     * @throws RemoteException on any server exception.
     */
    public Account addAccount(String accountId) throws RemoteException;

    /**
     * Returns specific account by its account id.
     *
     * @param accountId account id of desired account.
     * @return remote reference to, or serialized representation of desired account.
     * @throws RemoteException on any server exception.
     */
    public Account getAccount(String accountId) throws RemoteException;
}
