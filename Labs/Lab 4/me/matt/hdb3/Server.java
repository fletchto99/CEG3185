package me.matt.hdb3;

import me.matt.hdb3.utils.HDB3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
    public static int DEFAULT_PORT = 8585;

    private int port;
    private boolean requestToSend = false;

    public Server() {
        this(DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("[Server]Starting on port " + port);
        try {
            Socket client = new ServerSocket(port).accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[Server] Received message '" + message + "'");
                if (message.equals("request-to-send")) {
                    System.out.println("[Server] Sending clear to send message");
                    out.println("clear-to-send");
                    requestToSend = true;
                } else {
                    if (!requestToSend) {
                        System.err.println("[Server] Client has not requested to send data");
                        continue;
                    }
                    System.out.println("[Server] Received encoded message " + message);
                    System.out.println("[Server] Decoded message is: '" + HDB3.decode(message) + "'");
                    out.println("ack");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("[Server]An unexpected error has occurred");
        }
        System.out.println("[Server] Finished");
    }
}
