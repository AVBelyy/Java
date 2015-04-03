package ru.ifmo.ctddev.belyy.webcrawler;

import info.kgeorgiy.java.advanced.CachingDownloader;
import info.kgeorgiy.java.advanced.Crawler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String url = args[0];
            int downloads = 1;
            int extractors = 1;
            int perHost = 1;
            int depth = 3;

            if (args.length >= 2) {
                downloads = Integer.parseInt(args[1]);
            }
            if (args.length >= 3) {
                extractors = Integer.parseInt(args[2]);
            }
            if (args.length == 4) {
                perHost = Integer.parseInt(args[3]);
            }
            if (args.length < 2 || args.length > 4) {
                throw new NullPointerException();
            }

            try (Crawler crawler = new WebCrawler(new CachingDownloader(), downloads, extractors, perHost)) {
                for (String link : crawler.download(url, depth)) {
                    System.out.println("-> " + link);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Usage: WebCrawler url [downloads [extractors [perHost]]]");
        }
    }
}
