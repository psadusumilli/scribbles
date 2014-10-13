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
            readFrom(buffer);
            buffer.clear();
            bytesToRead = channel.read(buffer);
        }
        channel.close();
        file.close();
    }

    @Test
    public void shouldDoAScatterReadAndWrite() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile(resource("/samples/1.txt"),"rw");
        RandomAccessFile writeFile = new RandomAccessFile(resource("/samples/2.txt"),"rw");
        FileChannel readChannel = readFile.getChannel();
        FileChannel writeChannel = writeFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(30);
        ByteBuffer buffer2 = ByteBuffer.allocate(120);
        ByteBuffer[] buffers = {buffer1, buffer2};

        readChannel.read(buffers);

        print("first 30 chars(bytes).....");
        readFrom(buffer1);
        print("next 120 chars(bytes).....");
        readFrom(buffer2);

        writeChannel.write(buffers);
        buffer1.clear();
        buffer2.clear();

        readChannel.close();
        writeChannel.close();
        readFile.close();
    }

    private void readFrom(ByteBuffer buffer) {
        buffer.flip();
        StringBuilder builder = new StringBuilder();
        while(buffer.hasRemaining())
            builder.append((char) buffer.get());
        print(builder);
    }
}
