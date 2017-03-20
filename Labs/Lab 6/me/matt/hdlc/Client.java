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
        System.out.println("[Client] Connecting to localhost on port " + port);
        try {
            Socket socket = new Socket("127.0.0.1", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            System.out.println("[Client] Connected to localhost on port " + port);

            // Send Request to send message to server
            System.out.println("[Client] Sending rts message");


            String message;
//            inputStream:
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