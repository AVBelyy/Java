package ru.ifmo.ctddev.belyy.webcrawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {
    private final int perHost;
    private final Downloader downloader;
    private final ExecutorService downloadPool;
    private final ExecutorService extractPool;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloadPool = Executors.newFixedThreadPool(downloaders);
        this.extractPool = Executors.newFixedThreadPool(extractors);
        this.perHost = perHost;
    }

    @Override
    public Result download(String initialUrl, int depth) {
            final Map<String, IOException> errors = new ConcurrentHashMap<>();

            Queue<String> queue = new ConcurrentLinkedQueue<>();
            final Set<String> visited = Collections.newSetFromMap(new ConcurrentHashMap<>());
            queue.add(initialUrl);
            visited.add(initialUrl);

            for (int d = 0; d < depth; d++) {
                final int curDepth = d;
                final Set<String> queue2 = Collections.newSetFromMap(new ConcurrentHashMap<>());
                final Map<String, ConcurrentLinkedQueue<String>> perHostQueue = new ConcurrentHashMap<>();
                final Map<String, Integer> semaphores = new ConcurrentHashMap<>();
                final ConcurrentLinkedQueue<Future> pending = new ConcurrentLinkedQueue<>();

                for (String url : queue) {
                    String host;
                    try {
                        host = URLUtils.getHost(url);
                    } catch (MalformedURLException e) {
                        continue;
                    }

                    synchronized (perHostQueue) {
                        perHostQueue.putIfAbsent(host, new ConcurrentLinkedQueue<>());
                        perHostQueue.get(host).add(url);
                    }

                    synchronized (semaphores) {
                        semaphores.putIfAbsent(host, 0);
                        int count = semaphores.get(host);
                        if (count < perHost) {
                            semaphores.put(host, count + 1);
                            pending.add(downloadPool.submit(() -> {
                                while (true) {
                                    String curUrl = "";

                                    synchronized (perHostQueue) {
                                        if (!perHostQueue.get(host).isEmpty()) {
                                            curUrl = perHostQueue.get(host).poll();
                                        }
                                    }

                                    if (!curUrl.equals("")) {
                                        final String curUrlCopy = curUrl;
                                        try {
                                            Document document = downloader.download(curUrl);

                                            if (curDepth + 1 < depth) {
                                                pending.add(extractPool.submit(() -> {
                                                    try {
                                                        List<String> links = document.extractLinks();
                                                        for (String link : links) {
                                                            synchronized (visited) {
                                                                if (!visited.contains(link)) {
                                                                    visited.add(link);
                                                                    queue2.add(link);
                                                                }
                                                            }
                                                        }
                                                    } catch (IOException e) {
                                                        errors.putIfAbsent(curUrlCopy, e);
                                                    }
                                                }));
                                            }
                                        } catch (IOException e) {
                                            errors.putIfAbsent(curUrl, e);
                                        }
                                    } else {
                                        synchronized (semaphores) {
                                            semaphores.put(host, semaphores.get(host) - 1);
                                        }
                                        return;
                                    }
                                }
                            }));
                        }
                    }
                }

                while (!pending.isEmpty()) {
                    Future f = pending.poll();
                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                queue = new ConcurrentLinkedQueue<>(queue2);
            }

            visited.removeAll(errors.keySet());
            return new Result(new ArrayList<>(visited), errors);
    }

    @Override
    public void close() {
        downloadPool.shutdown();
        extractPool.shutdown();
    }
}
