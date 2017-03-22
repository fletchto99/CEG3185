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
        System.out.println("[Client] Connecting to localhost on port " + port);
        try {
            Socket socket = new Socket("127.0.0.1", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            System.out.println("[Client] Connected to localhost on port " + port);

            // Send Request to send message to server
            //System.out.println("[Client] Sending rts message");


            String message;
            //inputStream:
            while ((message = in.readLine()) != null) {
            	if(checkFlags(message)){
            		if(s0(message, out)){
            			s1(message, fromUser, out);
            			s2(message);
            		}
            	}
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Client] Complete");
    }
    
    public boolean s0 (String message, final PrintWriter out){
    	boolean flag=false;
    	
    	//Check for SNRM
    	if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_SNRM)){
    		//Return UA to A - Address field indicates where UA is from
    		out.write(new Frame(HDLC.ADDRESS_C, HDLC.CONTROL_UA).toString());	
    		flag=true;
    	}
    	return flag;
    }
    
    public void s1(String message, final BufferedReader fromUser, final PrintWriter out){
    	if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_RRP)){
    		//Send message to B
    		if(Frame.fromString(message).getControl().equals(HDLC.CONTROL_RRP) && fromUser!=null){
    			out.write(new Frame(HDLC.ADDRESS_B, HDLC.CONTROL_INFO, fromUser.toString()).toString());
    		}
    		
    		//Send RRF to A
    		else{
    			out.write(new Frame(HDLC.ADDRESS_C, HDLC.CONTROL_RRF).toString());
    		}
    	}
    	
    }
    
    public void s2(String message){
    	String consume = Frame.fromString(message).getMessage();
    	System.out.print("Message Received from B:" + consume);
    }
    
    
    private boolean checkFlags(String msg){
    	boolean result= false;
    	
    	if(HDLC.FLAG.equals(msg.substring(0, 8)) 
    			&& HDLC.FLAG.equals(msg.substring(msg.length()-8, msg.length())))
    		result=true;
    	
    	return result;
    }
    
}