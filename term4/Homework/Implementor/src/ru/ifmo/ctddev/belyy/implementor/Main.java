package ru.ifmo.ctddev.belyy.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 1 || args[0] == null) {
            System.err.println("specify class name");
            return;
        }

        try {
            Implementor impler = new Implementor();
            impler.implement(Class.forName(args[0]), new File("."));
        } catch (ImplerException e) {
            System.err.println("Impler exception: " + e.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("class " + args[0] + " not found");
        }
    }
}
