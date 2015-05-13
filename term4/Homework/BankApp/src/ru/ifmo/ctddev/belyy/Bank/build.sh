#!/bin/bash
export CLASSPATH=../../../../..

javac Server.java Client.java
rmic -d $CLASSPATH ru.ifmo.ctddev.belyy.Bank.AccountImpl ru.ifmo.ctddev.belyy.Bank.RemotePerson ru.ifmo.ctddev.belyy.Bank.BankImpl
