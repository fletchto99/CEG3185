package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final int port;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ServerSocket serverSocket;

    public Server() {
        this(Constants.DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        System.out.printf("[Server]Starting on port %d%n", port);
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                    Socket client = serverSocket.accept();
                    executorService.execute(onConnect(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Finished");
    }

    private void stopServer() {
        //Stop the executor service.
        executorService.shutdownNow();
        try {
            //Stop accepting requests.
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error in server shutdown");
            e.printStackTrace();
        }
        System.exit(0);
    }

    private Runnable onConnect(final Socket client) {
        return () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                while (true) {
                    String clientCommand = in.readLine();
                    System.out.println("Client Says :" + clientCommand);
                    if (clientCommand.equalsIgnoreCase("quit")) {
                        stopServer();
                        break;
                    } else {
                        out.println(clientCommand);
                        out.flush();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}


