package me.matt.hdlc.utils;

public class Frame {

    private String address;
    private String control;
    private String message;

    public Frame(final String address, final String control, final String message) {
        this.address = address;
        this.control = control;
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public String getControl() {
        return control;
    }

    public String getMessage() {
        return message;
    }

//    public static Frame fromString(final String frame) {
//        return new Frame();
//    }

}
