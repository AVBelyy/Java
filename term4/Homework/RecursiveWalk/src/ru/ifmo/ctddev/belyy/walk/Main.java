package ru.ifmo.ctddev.belyy.walk;

import java.io.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("wrong args");
            return;
        }

        File inFile = new File(args[0]);
        File outFile = new File(args[1]);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "utf8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf8"))) {
            String rootFilePath;

            while ((rootFilePath = reader.readLine()) != null) {
                RecursiveWalk walk = new RecursiveWalk(Paths.get(rootFilePath));
                try {
                    walk.processFiles((path, checksum) -> {
                        try {
                            writer.write(String.format("%08x %s\n", checksum, path));
                            return null;
                        } catch (IOException e) {
                            return e;
                        }
                    });
                } catch (IOException e) {
                    System.err.println("error traversing " + rootFilePath);
                }
            }
        } catch (IOException e) {
            System.err.println("error reading / writing file");
        }
    }
}
