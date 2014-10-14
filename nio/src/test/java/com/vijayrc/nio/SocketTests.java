package com.vijayrc.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static com.vijayrc.nio.Util.print;

public class SocketTests {
    @Test
    public void shouldReadFromAServer() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("google.com", 80));
        ByteBuffer buffer = ByteBuffer.allocate(50);
        int bytesToRead = socketChannel.read(buffer);
        print("ss");
        while(bytesToRead != -1){
            buffer.flip();
            print("s");
            StringBuilder builder = new StringBuilder();
            while (buffer.hasRemaining()){
                builder.append((char) buffer.get());
                print("D");
            }
            print(builder);
            buffer.clear();
            bytesToRead = socketChannel.read(buffer);
        }
    }
}
