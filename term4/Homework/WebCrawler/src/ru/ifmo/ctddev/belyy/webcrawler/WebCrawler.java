package ru.ifmo.ctddev.belyy.webcrawler;

import info.kgeorgiy.java.advanced.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WebCrawler implements Crawler {
    private Downloader downloader;
    private FixedThreadPool downloadPool;
    private FixedThreadPool extractPool;
    private int perHost;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloadPool = new FixedThreadPool(downloaders);
        this.extractPool = new FixedThreadPool(extractors);
        this.perHost = perHost;
    }

    @Override
    public List<String> download(String url, int depth) throws IOException {
        if (depth == 1) {
            return Arrays.asList(url);
        } else {
            List<String> links = new ArrayList<>();
            AtomicInteger v = new AtomicInteger(1);
            Throwable[] maybeE = new Throwable[1];
            maybeE[0] = null;

            downloadPool.execute(new ExceptionableRunnable() {
                @Override
                public void run() throws Throwable {
                    Document document = downloader.download(url);

                    extractPool.execute(new LockedTask(v, new ExceptionableRunnable() {
                        @Override
                        public void run() throws Throwable {
                            links.add(url);

                            for (String link : document.extractLinks()) {
                                links.addAll(download(link, depth - 1));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            maybeE[0] = e;
                        }
                    }));
                }

                @Override
                public void onError(Throwable e) {
                    maybeE[0] = e;
                    v.set(0);
                }
            });

            while (!v.compareAndSet(0, 1));

            Throwable e = maybeE[0];
            if (e instanceof IOException) {
                throw (IOException) e;
            } else {
                return links;
            }
        }
    }

    @Override
    public void close() {
        try {
            downloadPool.shutdown();
            extractPool.shutdown();
        } catch (InterruptedException ignore) {
        }
    }
}
