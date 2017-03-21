package me.matt.hdlc;

import me.matt.test.Client;
import me.matt.test.server.Server;


public class Application {

    public static void main(String... args) {
        System.out.println("Starting app");
        new Thread(new Server(1234)).start();
        new Thread(new Client(1234)).start();
    }

}
