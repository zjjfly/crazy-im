package com.github.zjjfly.im.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.IntBuffer;

/**
 * @author zjjfly[[https://github.com/zjjfly]]
 * @date 2021/10/6
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UseBuffer {

    IntBuffer buffer;

    @Test
    @Order(1)
    void allocate() {
        //使用allocate初始化一个缓冲区,刚刚初始化之后的模式是写模式
        buffer = IntBuffer.allocate(20);
        log.info("---after allocate---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }

    @Test
    @Order(2)
    void write() {
        for (int i = 0; i < 5; i++) {
            //调用put写入数据
            buffer.put(i);
        }
        log.info("---after put---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }

    @Test
    @Order(3)
    void flip() {
        //调用flip把写模式转换为读模式,这会改变position和limit,并重置mark
        buffer.flip();
        log.info("---after flip---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }

    @Test
    @Order(4)
    void get() {
        for (int i = 0; i < 2; i++) {
            //调用get读取一个数据
            int j = buffer.get();
            log.info("j = " + j);
        }
        log.info("---after get 2 int---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
        for (int i = 0; i < 3; i++) {
            int j = buffer.get();
            log.info("j = " + j);
        }
        log.info("---after get 3 int---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }

    @Test
    @Order(5)
    void rewind() {
        //调用rewind进行倒带,即把position设置为0,同时也会重置mark
        buffer.rewind();
        log.info("---after rewind---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }

    @Test
    @Order(6)
    void markAndReset() {
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                //调用mark把buffer的mark字段设为当前的position,用于临时保存当前位置
                buffer.mark();
            }
            buffer.get();
        }
        log.info("buffer.position() = " + buffer.position());
        //reset和mark配套使用,它会把position恢复为mark中临时保存的值
        buffer.reset();
        log.info("buffer.position() = " + buffer.position());
    }

    @Test
    @Order(7)
    void clear() {
        //调用clear可以从读模式转换为写模式,会将position清零,并把limit设为capacity的值
        buffer.clear();
        log.info("---after clear---");
        log.info("buffer.position() = " + buffer.position());
        log.info("buffer.limit() = " + buffer.limit());
        log.info("buffer.capacity() = " + buffer.capacity());
    }
}
