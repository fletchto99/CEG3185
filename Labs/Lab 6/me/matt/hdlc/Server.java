package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;
import me.matt.hdlc.utils.Frame;
import me.matt.hdlc.utils.HDLC;

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
    private int id = 0;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ServerSocket serverSocket;

    public Server() {
        this(Constants.DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        System.out.printf("[Server] Starting on port %d%n", port);
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                    Socket client = serverSocket.accept();
                    executorService.execute(onConnect(client, id++));
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
            System.out.println("[Server] Error in server shutdown");
            e.printStackTrace();
        }
        System.exit(0);
    }

    private Runnable onConnect(final Socket client, final int id) {
        return () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);

                String response;

                //Send SNRM to both machines
                out.write(HDLC.CONTROL_SNRM);

                while ((response = in.readLine()) != null) {
                    if (Frame.fromString(response).getControl().equals(HDLC.CONTROL_UA)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}


