package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private final int port;

    public Client() {
        this(Constants.DEFAULT_PORT);
    }

    public Client(int port) {
        this.port = port;
    }


    public void run() {
        System.out.printf("[Client] Connecting to localhost on port %d%n ", port);
        try {
            Socket socket = new Socket("127.0.0.1", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            System.out.printf("[Client] Connected to localhost on port %d%n", port);

            // Send Request to send message to server
            System.out.println("[Client] Sending rts message");


            String message;
            while ((message = in.readLine()) != null) {
                switch (message) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Client] Complete");
    }
}