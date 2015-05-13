#!/bin/bash
export CLASSPATH=../../../../..

rmiregistry &
java ru.ifmo.ctddev.belyy.Bank.Server
