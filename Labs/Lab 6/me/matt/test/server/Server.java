package me.matt.test.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors
                    .newSingleThreadExecutor());
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(
                    new InetSocketAddress(port));

            server.accept(null,
                    new CompletionHandler<AsynchronousSocketChannel, ClientReference>() {
                        public void completed(AsynchronousSocketChannel client, ClientReference att) {
                            try {
                                SocketAddress clientAddr = client.getRemoteAddress();
                                System.out.format("Accepted a  connection from  %s%n", clientAddr);
                                server.accept(att, this);
                                ClientConnectionHandler handler = new ClientConnectionHandler();
                                ClientReference ref = new ClientReference();
                                ref.server = server;
                                ref.client = client;
                                ref.buffer = ByteBuffer.allocate(2048);
                                ref.isRead = true;
                                ref.clientAddr = clientAddr;
                                client.read(ref.buffer, ref, handler);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        public void failed(Throwable exc, ClientReference att) {
                            System.out.println("Failed to accept connection");
                        }
                    });

            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleConnection() {

    }

}