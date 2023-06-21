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
    void testHelloServiceTanpaAspect(){

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

    @Test
    void testHelloServiceAspectPointcutdanAdvice(){

        // pointcut dengan method target, object adalah instance dari tipe yang ditentukan
        // advice dengan @Before, Aspect akan menjalankan aksi sebelum Join Point

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T21:27:44.253+07:00  INFO 7192 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T21:27:44.253+07:00  INFO 7192 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T21:27:44.273+07:00  INFO 7192 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T21:27:44.273+07:00  INFO 7192 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         */
    }

    @Test
    void testHelloServiceAspectAdviceParameter(){

        // Advace akan mendapat detail resource dari pointcut dan join point

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T21:49:27.928+07:00  INFO 12868 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T21:49:27.928+07:00  INFO 12868 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T21:49:27.939+07:00  INFO 12868 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T21:49:27.940+07:00  INFO 12868 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T21:49:27.940+07:00  INFO 12868 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T21:49:27.940+07:00  INFO 12868 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         */
    }

    @Test
    void testHelloServiceAspectProceedingJoinPointAdviceAround(){

        // Advace akan mendapat detail resource dari pointcut dan join point

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T22:31:59.449+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:31:59.452+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:31:59.452+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:31:59.452+07:00  INFO 17364 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T22:31:59.452+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:31:59.458+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:31:59.458+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:31:59.458+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:31:59.458+07:00  INFO 17364 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         * 2023-06-21T22:31:59.459+07:00  INFO 17364 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.bye()
         */
    }

    @Test
    void testHelloServiceAspectExecutionPointcutAdviceBefore(){

        // menggunakan pointcut execution

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T22:50:57.992+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:50:57.994+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:50:57.994+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:50:57.994+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Execute method with parameter : Budhi
         * 2023-06-21T22:50:57.994+07:00  INFO 12336 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T22:50:57.994+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:50:58.000+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:50:58.000+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:50:58.000+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:50:58.000+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Execute method with parameter : Budhi
         * 2023-06-21T22:50:58.001+07:00  INFO 12336 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         * 2023-06-21T22:50:58.001+07:00  INFO 12336 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.bye()
         */
    }

    @Test
    void testHelloServiceAspectMultiplePointcut(){

        // menggunakan pointcut execution

        Assertions.assertEquals("Hello Budhi", helloService.hello("Budhi")); // String hello(String name)
        Assertions.assertEquals("Bye Budhi", helloService.bye("Budhi")); // String bye(String name)

        /**
         * result log:
         * 2023-06-21T22:58:36.028+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Log for all public service method
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Execute method with parameter : Budhi
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.hello()
         * 2023-06-21T22:58:36.040+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.hello()
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point : Advice Around Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Before HelloService Method
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Advice Parameter: Before: com.tutorial.aop.service.HelloService.bye()
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Log for all public service method
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Execute method with parameter : Budhi
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.service.HelloService    : Panggil HelloService.bye()
         * 2023-06-21T22:58:36.058+07:00  INFO 14760 --- [           main] com.tutorial.aop.aspect.LogAspect        : Detail Proceeding Join Point: Advice Around Finally: com.tutorial.aop.service.HelloService.bye()
         */
    }




}
