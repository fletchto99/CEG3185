package me.matt.hdlc.test;

import me.matt.hdlc.test.server.Server;


public class Applcation {

    public static void main(String... args) {
        System.out.println("Starting app");
        new Thread(new Server(1234)).start();
        new Thread(new Client(1234)).start();
    }

}
