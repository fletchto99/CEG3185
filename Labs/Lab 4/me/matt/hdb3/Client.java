package me.matt.hdb3;

import me.matt.hdb3.utils.HDB3;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

class Client implements Runnable {

    private int port;

    public Client() {
        this(Server.DEFAULT_PORT);
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

            // Send Request to send message to server
            System.out.println("[Client] Sending rts message");
            out.println("request-to-send");


            String message;
            inputStream:
            while ((message = in.readLine()) != null) {
                switch (message) {
                    case "clear-to-send":
                        String input = JOptionPane.showInputDialog(null, "Please input a message to encode (0's and 1's only)", "Message", JOptionPane.INFORMATION_MESSAGE);
                        while (!input.matches("[0-1]+")) {
                            input = JOptionPane.showInputDialog(null, "Please input a message to encode. Must be 0's and 1's only!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                        out.println(HDB3.encode(input));
                        break;
                    case "ack":
                        System.out.println("[Client] Server received HDB3 stream");
                        break inputStream;
                    default:
                        out.println("[Client] Invalid message: " + message);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Client] Complete");
    }
}