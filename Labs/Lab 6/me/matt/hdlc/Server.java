package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private final int port;
    private int id;

    public Server() {
        this(Constants.DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        System.out.println("[Server]Starting on port " + port);
        try {
            new ClientServiceThread(new ServerSocket(port).accept(), id++).start();
        } catch (IOException e) {
            System.out.println("[Server]An unexpected error has occurred");
        }
        System.out.println("[Server] Finished");
    }
}

class ClientServiceThread extends Thread {
    Socket clientSocket;
    int clientID = -1;
    boolean running = true;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    public void run() {
        System.out.println("[Server] Accepted Client: " + clientID + " from "
                + clientSocket.getInetAddress().getHostName());
        try {
            BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);
                if (clientCommand.equalsIgnoreCase("quit")) {
                    running = false;
                    System.out.print("Stopping client thread for client : " + clientID);
                } else {
                    out.println(clientCommand);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


