package ru.ifmo.ctddev.belyy.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.File;

/**
 * Tester class for {@link ru.ifmo.ctddev.belyy.implementor.JarredImplementor JarredImplementor} class.
 *
 * @author  Anton Belyy
 * @see     ru.ifmo.ctddev.belyy.implementor.JarredImplementor
 * @version 1.0
 */
public class Main {
    /**
     * Entry point of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String className;
        boolean generateJar;

        if (args != null && args.length == 1 && args[0] != null) {
            className = args[0];
            generateJar = false;
        } else if (args != null && args.length == 2 && args[0] != null && args[1] != null && args[0].equals("-jar")) {
            className = args[1];
            generateJar = true;
        } else {
            System.err.println("incorrect args");
            return;
        }

        try {
            JarredImplementor impler = new JarredImplementor();
            impler.setGenerateJar(generateJar);
            impler.implement(Class.forName(className), new File("."));
        } catch (ImplerException e) {
            System.err.println("Impler exception: " + e.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("class " + args[0] + " not found");
        }
    }
}
