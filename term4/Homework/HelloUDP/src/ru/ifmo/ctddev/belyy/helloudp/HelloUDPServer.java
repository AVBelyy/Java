package ru.ifmo.ctddev.belyy.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloUDPServer implements HelloServer {
    private static final Charset CHARSET = Charset.forName("utf-8");

    private final List<DatagramSocket> sockets = new ArrayList<>();
    private final List<ExecutorService> threadPools = new ArrayList<>();
    private volatile boolean isRunning = true;

    @Override
    public void start(int port, int threads) {
        synchronized (this) {
            if (!isRunning) {
                throw new IllegalStateException();
            }
        }

        final DatagramSocket newSocket;
        DatagramPacket request;

        try {
            newSocket = new DatagramSocket(port);
            byte[] reqBuf = new byte[newSocket.getReceiveBufferSize()];
            System.err.println(newSocket.getReceiveBufferSize());
            request = new DatagramPacket(reqBuf, reqBuf.length);
        } catch (SocketException e) {
            System.out.println("socket exception");
            return;
        }

        final ExecutorService newThreadPool = Executors.newFixedThreadPool(threads);

        synchronized (this) {
            sockets.add(newSocket);
            threadPools.add(newThreadPool);
        }

        (new Thread(() -> {
            while (isRunning) {
                try {
                    newSocket.receive(request);
                    InetAddress clientAddress = request.getAddress();
                    int clientPort = request.getPort();
                    // save buffer in thread-local variable
                    final String requestText = new String(request.getData(), 0, request.getLength());

                    newThreadPool.execute(() -> {
                        try {
                            byte[] respBuf = ("Hello, " + requestText).getBytes(CHARSET);
                            DatagramPacket response = new DatagramPacket(respBuf, respBuf.length, clientAddress, clientPort);

                            newSocket.send(response);
                        } catch (IOException ignore) {}
                    });
                } catch (IOException ignore) {}
            }
        })).start();
    }

    @Override
    public void close() {
        synchronized (this) {
            isRunning = false;
            for (int i = 0; i < sockets.size(); i++) {
                sockets.get(i).close();
                threadPools.get(i).shutdown();
            }
        }
    }
}