package com.tutorial.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloService {

    /**
     * Tanpa AOP
     * ● Sekarang kita akan membuat contoh terlebih dahulu sebuah kode program tanpa AOP
     * ● Kita akan membuat Service Class, lalu di tiap method di Service Class tersebut, kita akan selalu menambahkan log di awal method
     */

    public String hello(String name){
        log.info("Panggil HelloService.hello()"); // untuk log memangil object HelloService dengan method hello()
        return "Hello " + name;
    }

    public String bye(String name){
        log.info("Panggil HelloService.bye()");
        return "Bye " + name;
    }

}
