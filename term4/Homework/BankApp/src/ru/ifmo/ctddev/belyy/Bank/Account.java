package ru.ifmo.ctddev.belyy.Bank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface to person account in bank.
 */
public interface Account extends Remote, Serializable {
    /**
     * Returns the amount of funds available on account.
     *
     * @return the amount of funds.
     * @throws RemoteException on any server exception.
     */
    public BigDecimal getAmount() throws RemoteException;

    /**
     * Add <tt>amount</tt> items of money to account.
     *
     * @param amount amount of funds to increment account with.
     * @throws RemoteException on any server exception.
     */
    public void incAmount(BigDecimal amount) throws RemoteException;
}