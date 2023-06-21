package com.tutorial.aop.tanpaaop;

import com.tutorial.aop.service.HelloService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    void testHelloService(){

        /**
         * Bayangkan
         * ● Jika method terus bertambah, apa yang dilakukan?
         * ● Maka kita harus membuat log terus menerus secara manual di tiap method nya
         * ● Padahal jika diperhatikan, isi dari log polanya hampir sama, hanya menampilkan method apa yang sedang diakses
         * ● AOP bisa membantu mempermudah ini, dimana kita bisa membuat Aspect yang melintasi semua
         *   method dari object tersebut, dimana di Aspect nya, kita hanya perlu menulis kode untuk log satu kali
         */

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T20:50:58.547+07:00  INFO 13812 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T20:50:58.558+07:00  INFO 13812 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         */
    }


}
