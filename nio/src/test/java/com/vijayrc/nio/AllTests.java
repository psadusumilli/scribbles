package com.vijayrc.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.vijayrc.nio.Util.*;

public class AllTests {
    @Test
    public void shouldReadFromAFileAndPutIntoBuffer() throws Exception {
        RandomAccessFile file = new RandomAccessFile(resource("/samples/1.txt"),"rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(120);//bytes to read in one shot

        int bytesToRead = channel.read(buffer);
        while(bytesToRead != -1){
            buffer.flip();
            StringBuilder builder = new StringBuilder();
            while(buffer.hasRemaining()) builder.append((char) buffer.get());
            print(builder);
            buffer.clear();
            bytesToRead = channel.read(buffer);
        }
        channel.close();
        file.close();
    }
}
