package me.matt.hdlc.test.server;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

public class ClientReference {
    protected AsynchronousServerSocketChannel server;
    protected AsynchronousSocketChannel client;
    protected ByteBuffer buffer;
    protected SocketAddress clientAddr;
    protected boolean isRead;
}