package ru.ifmo.ctddev.belyy.filecopy;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        // Create frame
        UIApplication app = new UIApplication();

        // Copy files
        app.copyFiles(Paths.get("/Users/anton/Projects"), Paths.get("/Users/anton/Downloads/za/gra/da"), app::destroy);
    }
}
