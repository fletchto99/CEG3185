package me.matt.hdb3;

public class Application {

    public static void main(String... args) {
        new Thread(new Server()).start();
        new Thread(new Client()).start();
    }

}
