package com.mushan.im;

import com.mushan.im.netty.CoordinationNettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mushan.im.dao")
public class MushanImApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MushanImApplication.class, args);
    }
    @Autowired
    private CoordinationNettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
