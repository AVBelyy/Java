package ru.ifmo.ctddev.belyy.Bank;

import java.rmi.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws RemoteException {
        Bank bank;
        String name = args[0];
        String surname = args[1];
        String passportId = args[2];
        String accountId = args[3];
        int deltaMoney = Integer.parseInt(args[4]);

        try {
            bank = (Bank) Naming.lookup("rmi://localhost/bank");
        } catch (NotBoundException e) {
            System.out.println("Bank URL is invalid");
            return;
        } catch (MalformedURLException e) {
            System.out.println("Bank is not bound");
            return;
        }

        Person person = bank.getPerson(passportId, false);
        if (person == null) {
            System.out.println("Creating person");
            person = bank.createPerson(name, surname, passportId);
        } else {
            System.out.println("Person already exists");
        }

        Account account = person.getAccount(accountId);
        if (account == null) {
            System.out.println("Creating account");
            account = person.addAccount(accountId);
        } else {
            System.out.println("Account already exists");
        }

        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + deltaMoney);
        System.out.println("Money: " + account.getAmount());
    }
}
