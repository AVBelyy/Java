package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote {
    // Узнать идентификатор
    public String getId() throws RemoteException;

    // Узнать количество денег
    public long getAmount() throws RemoteException;;

    // Установить количество денег
    public void setAmount(long amount) throws RemoteException;;
}