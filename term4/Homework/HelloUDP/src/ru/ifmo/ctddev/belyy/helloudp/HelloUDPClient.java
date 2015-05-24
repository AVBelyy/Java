package ru.ifmo.ctddev.belyy.helloudp;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloUDPClient implements HelloClient {
    @Override
    public void start(String host, int port, String prefix, int requests, int threads) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        Charset charset = Charset.forName("utf-8");

        InetAddress address;
        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < threads; i++) {
            String threadPrefix = prefix + i + "_";
            threadPool.execute(() -> {
                byte[] sendBuf;
                byte[] recvBuf;

                DatagramSocket socket;
                try {
                    socket = new DatagramSocket();
                    socket.setSoTimeout(200);
                    recvBuf = new byte[socket.getReceiveBufferSize()];
                } catch (SocketException e) {
                    e.printStackTrace();
                    return;
                }

                for (int j = 0; j < requests; j++) {
                    String request = threadPrefix + j;
                    sendBuf = request.getBytes(charset);

                    try {
                        socket.send(new DatagramPacket(sendBuf, sendBuf.length, address, port));
                    } catch (IOException e) {
                        // very sorry, so sad
                        System.out.println("socket is broken");
                        return;
                    }

                    try {
                        //System.out.println("-> " + new String(sendBuf, charset));
                        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                        socket.receive(packet);
                        String response = new String(packet.getData(), 0, packet.getLength());
                        if (response.equals("Hello, " + request)) {
                            //System.out.println("<- " + response);
                        } else {
                            j--;
                            System.out.println("bad response");
                        }
                    } catch (IOException e) {
                        j--;
                    }
                }
            });
        }

        threadPool.shutdown();
        while (!threadPool.isTerminated());
    }
}
