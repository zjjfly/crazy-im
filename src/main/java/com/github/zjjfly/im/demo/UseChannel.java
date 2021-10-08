package com.github.zjjfly.im.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author zjjfly[[https://github.com/zjjfly]]
 * @date 2021/10/7
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UseChannel {

    @Test
    void fileChannel() {
        try (RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
             FileChannel channel = file.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            int length = -1;
            //从文件通道中读取数据
            while ((length = channel.read(buffer)) != -1) {
                log.info("length = " + length);
            }
            buffer.put("6\n7\n8\n9\n10\n".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            length = -1;
            //把缓冲区的数据写回到文件
            while ((length = channel.write(buffer)) != 0) {
                log.info("written bytes: " + length);
            }
            //强制把通道的中的数据写入磁盘
            channel.force(true);
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    @Test
    void copyFile() {
        long startTime = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream("1.txt");
             FileOutputStream fos = new FileOutputStream("2.txt");
             FileChannel inChannel = fis.getChannel();
             FileChannel outChannel = fos.getChannel()) {
            int length = -1;
            ByteBuffer buffer = ByteBuffer.allocate(100);
            while ((length = inChannel.read(buffer)) != -1) {
                buffer.flip();
                int outLength = 0;
                while ((outLength = outChannel.write(buffer)) != 0) {
                    log.info("written bytes: " + outLength);
                }
                buffer.clear();
            }
            outChannel.force(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("cost time: " + (endTime - startTime) + "ms");
        }
    }
}
