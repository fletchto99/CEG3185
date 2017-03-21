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

    private Runnable onConnect(final Socket client, final int addr) {
        return () -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                p0(in, out, addr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }


    void p0(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;

        //Send SNRM to both machines
        out.write(new Frame("0000000" + String.valueOf(id), HDLC.CONTROL_SNRM).toString());

        //receive UA
        while ((response = in.readLine()) != null) {
            if (Frame.fromString(response).getControl().equals(HDLC.CONTROL_UA)) {
                break;
            }
        }
        p1(in, out, addr);
    }

    void p1(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;
        if (addr == 0) {
            out.write(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_RRP).toString());
            while ((response = in.readLine()) != null) {
                String control = Frame.fromString(response).getControl();
                if (control.equals(HDLC.CONTROL_RRF)) {
                    p2(in, out, addr);
                    break;
                } else if (control.equals(HDLC.CONTROL_INFO)) {
                    p5(in, out, addr);
                    break;
                }
            }
        }
    }

    void p2(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response;
        if (addr == 1) {
            out.write(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_RRP).toString());
            while ((response = in.readLine()) != null) {
                String control = Frame.fromString(response).getControl();
                if (control.equals(HDLC.CONTROL_RRF)) {
                    p3(in, out, addr);
                    break;
                } else if (control.equals(HDLC.CONTROL_INFO)) {
                    p6(in, out, addr);
                    break;
                }
            }
        }
    }

    void p3(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        if (addr == 0) {
            out.write(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_INFO).toString());
        }
    }

    void p4(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        if (addr == 1) {
            out.write(new Frame("0000000"+String.valueOf(addr), HDLC.CONTROL_INFO).toString());
        }
        p0();
    }

    void p5(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response = in.readLine();
        String address = Frame.fromString(response).getAddress();
        if (address.equals("00000001") && addr == 1) {
            //C Consume
            out.write(response);
        } else {
            System.out.printf("[Server] consume: %s%n", response);
        }
        p2(in, out, addr);
    }

    void p6(final BufferedReader in, final PrintWriter out, final int addr) throws IOException {
        String response = in.readLine();
        String address = Frame.fromString(response).getAddress();
        if (address.equals("00000000") && addr == 0) {
            //B Consume
            out.write(response);
        } else {
            System.out.printf("[Server] consume: %s%n", response);
        }
        p3(in, out, addr);
    }
}


