package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;
import me.matt.hdlc.utils.Frame;
import me.matt.hdlc.utils.HDLC;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final int port;
    private int id = 0;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Server() {
        this(Constants.DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        System.out.printf("[Server] Starting on port %d%n", port);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                    Socket client = serverSocket.accept();
                    executorService.execute(onConnect(client, id++));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Finished");
    }

    private Runnable onConnect(final Socket client, final int addr) {
        return () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                System.out.printf("[Server] Received connection from client at %s given the id %d%n", client.getInetAddress().toString(), addr);
                p0(in, out, addr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }


    private void p0(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;

        //Send SNRM to both machines
        out.println(new Frame("0000000" + String.valueOf(id), HDLC.CONTROL_SNRM).toString());

        //receive UA
        while ((response = in.readLine()) != null) {
            if (Frame.fromString(response).getControl().equals(HDLC.CONTROL_UA)) {
                break;
            }
        }
        p1(in, out, addr);
    }

    private void p1(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;
        if (addr == 0) {
            out.println(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_RRP).toString());
            while ((response = in.readLine()) != null) {
                String control = Frame.fromString(response).getControl();
                if (control.equals(HDLC.CONTROL_UA)) {
                    p2(in, out, addr);
                    break;
                } else if (control.equals(HDLC.CONTROL_INFO)) {
                    p5(in, out, addr);
                    break;
                }
            }
        }
    }

    private void p2(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;
        if (addr == 1) {
            out.println(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_RRP).toString());
            while ((response = in.readLine()) != null) {
                String control = Frame.fromString(response).getControl();
                if (control.equals(HDLC.CONTROL_UA)) {
                    p4(in, out, addr);
                    break;
                } else if (control.equals(HDLC.CONTROL_INFO)) {
                    p6(in, out, addr);
                    break;
                }
            }
        }
    }

    private void p3(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        if (addr == 0) {
            out.println(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_INFO).toString());
        }
        p0(in, out, addr);
    }

    private void p4(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        if (addr == 1) {
            out.println(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_INFO).toString());
        }
        p0(in, out, addr);
    }

    private void p5(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response = in.readLine();
        String address = Frame.fromString(response).getAddress();
        if (address.equals("00000001") && addr == 1) {
            //C Consume
            out.println(response);
        } else {
            System.out.printf("[Server] consuming message: %s%n", response);
        }
        p4(in, out, addr);
    }

    private void p6(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response = in.readLine();
        String address = Frame.fromString(response).getAddress();
        if (address.equals("00000000") && addr == 0) {
            //B Consume
            out.println(response);
        } else {
            System.out.printf("[Server] consume: %s%n", response);
        }
        p3(in, out, addr);
    }
}


