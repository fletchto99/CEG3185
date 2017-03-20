package me.matt.hdlc.test;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    int port;

    public Server() {
        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors
                    .newSingleThreadExecutor());
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(
                    new InetSocketAddress(port));

            server.accept(null,
                    new CompletionHandler<AsynchronousSocketChannel, Object>() {
                        public void completed(AsynchronousSocketChannel ch, Object att) {
                            System.out.println("Accepted a connection");

                            // accept the next connection
                            server.accept(null, this);

                            // handle this connection
                            //TODO handle(ch);
                        }

                        public void failed(Throwable exc, Object att) {
                            System.out.println("Failed to accept connection");
                        }
                    });

            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            Thread.sleep(60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}