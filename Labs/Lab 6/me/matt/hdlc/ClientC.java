package me.matt.hdlc;

import me.matt.hdlc.utils.Constants;
import me.matt.hdlc.utils.Frame;
import me.matt.hdlc.utils.HDLC;

import java.io.*;
import java.net.Socket;

public class ClientC implements Runnable {

    private final int port;

    public ClientC() {
        this(Constants.DEFAULT_PORT);
    }

    public ClientC(int port) {
        this.port = port;
    }


    public void run() {
        System.out.println("[Client C] Connecting to localhost on port " + port);
        try {
            Socket socket = new Socket("127.0.0.1", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            System.out.println("[Client C] Connected to localhost on port " + port);

            if (s0(in.readLine(), out)) {
                String message;
                while ((message = in.readLine()) != null) {
                    s1(message, fromUser, out);
                    s2(message);
                }
            } else {
                System.out.println("[Client C] Received invalid message");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Client C] Complete");
    }

    private boolean s0 (String message, final PrintWriter out){

        //Check for SNRM
        if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_SNRM)){
            //Return UA to A - Address field indicates where UA is from
            out.write(new Frame(HDLC.ADDRESS_B, HDLC.CONTROL_UA).toString());
            System.out.println("[Client C] SNRM Received");
            return true;
        }
        System.out.println("[Client C] Error checking SNRM");
        return false;
    }


    private void s1(String message, final BufferedReader fromUser, final PrintWriter out){
    	if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_RRP)){
    		//Send message to B
    		if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_RRP) && fromUser!=null){
    			out.write(new Frame(HDLC.ADDRESS_B, HDLC.CONTROL_INFO, fromUser.toString()).toString());
    		} else{
    			out.write(new Frame(HDLC.ADDRESS_A, HDLC.CONTROL_RRF).toString());
    		}
    	}
    	
    }
    
    private void s2(String message){
        System.out.printf("[Client C] Message Received: %s%n", message);
    }

}
