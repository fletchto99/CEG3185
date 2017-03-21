package me.matt.hdlc.utils;

import java.util.concurrent.ExecutionException;

public class Frame {

    private String address;
    private String control;
    private String message;

    public Frame(final String address, final String control) {
        this(address, control, "0000000000000000000000000000000000000000000000000000000000000000");
    }

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

    public String toString() {
        return HDLC.FLAG + address + control + message + HDLC.FCS + HDLC.FLAG;
    }

    public static Frame fromString(final String frame) {
        if (frame.length() != 112) {
            throw new RuntimeException("Frame too small");
        }
        //8 bits + 8 bits + 8 bits + 64 bits + 16 + 8
        //flag + address + control + message + fcs + flag
        return new Frame(frame.substring(8, 16), frame.substring(16, 24),frame.substring(24, 88));
    }

}
