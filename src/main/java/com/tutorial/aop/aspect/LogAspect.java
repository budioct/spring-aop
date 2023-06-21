package com.tutorial.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // Secara otomatis Spring akan membuatkan object Aspect dari class tersebut
@Component
@Slf4j
public class LogAspect {

    /**
     * Aspect
     * ● Seperti yang dibahas sebelumnya, inti dari AOP adalah Aspect
     * ● Oleh karena itu, hal yang harus kita lakukan adalah membuat Aspect
     * ● Untuk membuat Aspect, kita cukup membuat Bean dan menambahkan annotation Aspect pada Bean tersebut
     * ● Secara otomatis Spring akan membuatkan object Aspect dari class tersebut
     * ● https://javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Aspect.html
     * <p>
     * Kegunaan Aspect
     * ● Secara default, Aspect tidak berguna jika kita tidak menambahkan behavior pada Aspect tersebut
     * ● Oleh karena itu, kita perlu menambahkan behavior ke Aspect dengan cara menambahkan method pada Aspect tersebut
     * ● Namun tidak sembarang method, ada ketentuannya
     * <p>
     * Join Point
     * ● Join Point adalah titik lokasi eksekusi program
     * ● AspectJ sendiri sebenarnya mendukung banyak sekali Join Point, namun Spring AOP hanya mendukung Join Point pada eksekusi method di Bean
     * <p>
     * Contoh Join Point di Spring AOP
     * ● Eksekusi method hello() di class HelloService
     * ● Eksekusi semua method public di class HelloService
     * ● Eksekusi semua method yang terdapat annotation @Test
     * ● Eksekusi method di package service yang throw Exception
     * ● Dan lain-lain
     * ● Intinya adalah titik lokasi eksekusi method dengan kriteria tertentu, sehingga bisa melintasi satu
     * atau lebih method dan object
     * <p>
     * Pointcut
     * ● Pointcut adalah predikat yang cocok dengan Join Point
     * ● Secara sederhana, Pointcut merupakan kondisi yang digunakan untuk menentukan Join Point
     * ● Dan ketika kondisi terpenuhi, maka Aspect akan mengeksekusi Advice (akan dibahas di materi sendiri)
     * ● Untuk membuat Pointcut, kita perlu menggunakan annotation Pointcut
     * ● https://javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/Pointcut.html
     * <p>
     * Pointcut Expression
     * ● Saat kita membuat Pointcut, maka kita harus menambahkan expression yang berisi kondisi untuk Join Point nya
     * ● Misal kita ingin membuat Pointcut untuk semua method di class HelloService, maka kita harus buat
     * kondisi tersebut dalam bentuk Pointcut Expression
     * ● AspectJ sebenarnya mendukung banyak sekali Pointcut Expression, namun Spring AOP hanya
     * mendukung yang berhubungan dengan eksekusi method
     * <p>
     * Daftar Pointcut
     * Expression       Keterangan
     * execution        eksekusi method
     * within           object sesuai yang ditentukan
     * this             bean reference adalah instance tipe yang ditentukan
     * target           object adalah instance dari tipe yang ditentukan
     * args             argument method adalah instance dari tipe yang ditentukan
     * <p>
     * Daftar Pointcut 2
     * Expression       Keterangan
     *
     * @target object memiliki annotation yang ditentukan
     * @args arguments method memiliki annotation yang ditentukan
     * @within method di object yang memiliki annotation yang ditentukan
     * @annotation method memiliki annotation yang ditentukan
     * bean object      dengan nama bean sesuai yang ditentukan
     */


    @Pointcut("target(com.tutorial.aop.service.HelloService)")
    // Pointcut("target(lokasi class joint point)") // object adalah instance dari tipe yang ditentukan. Target Join Point adalah titik lokasi eksekusi program
    public void helloServiceMethod() {

    }

    /**
     * Advice
     * ● Advice adalah aksi yang dilakukan oleh Aspect pada Join Point
     * ● Terdapat banyak sekali jenis Advice, misal Before, After, dan Around
     * <p>
     * Jenis Advice Annotation
     * Advice           Keterangan
     *
     * @Before Aspect akan menjalankan aksi sebelum Join Point
     * @AfterReturning Aspect akan menjalankan aksi setelah Join Point return secara normal
     * @AfterThrowing Aspect akan menjalankan aksi setelah Joint Point throw exception
     * @After Aspect akan menjalankan aksi setelah selesai Join Point, After harus bisa menangani return normal atau throw exception
     * @Around Aspect memiliki kesempatan untuk menjalankan aksi sebelum dan setelah
     * <p>
     * Advices Annotation
     * ● Semua jenis Advice direpresentasikan dalam annotation di package org.aspectj.lang.annotation
     * ● https://javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/annotation/package-summary.html
     * ● Saat menggunakan Advice, kita harus tentukan Pointcut yang akan kita gunakan, caranya dengan
     * menyebutkan nama method dari Pointcut nya
     * <p>
     * Advice Parameter
     * ● Saat kita membuat Advice, kita juga bisa mendapat informasi dari detail eksekusi method nya dari object JoinPoint
     * ● Kita bisa tambahkan parameter JointPoint di method Advice yang kita buat
     * ● https://javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/JoinPoint.html
     */

    @Before("helloServiceMethod()")
    // @Before(value = "method pointcut") // jenis Aspect advice akan menjalankan aksi sebelum Join Point
    public void beforeHelloServiceMethod(JoinPoint joinPoint) {

        // Advice Parameter, mendapatkan detail resource
        // Object getTarget(); // return object target
        // final native Class<?> getClass() // return class object
        // String getName() // return nama class
        // Signature getSignature(); // return object yang sama
        String className = joinPoint.getTarget().getClass().getName(); // com.tutorial.aop.service.HelloService
        String methodName = joinPoint.getSignature().getName(); // .hello() // .bye()

        log.info("Before HelloService Method"); // log yang akan jalan sebelum joint point di eksekusi
        log.info("Detail Advice Parameter: Before: {}.{}()", className, methodName); // log yang akan jalan sebelum joint poit di eksekusi dengan path resource
    }

    /**
     * Proceeding Join Point
     * ● Khusus untuk Advice dengan jenis Around, maka kita gunakan parameter ProceedingJoinPoint, hal
     * ini karena untuk Around, kita bisa melakukan sebelum dan setelah
     * ● Dimana untuk mengeksekusi method aslinya dari Join Point, kita harus memanggil method ProceedingJoinPoint.proceed(args)
     * ● https://javadoc.io/doc/org.aspectj/aspectjrt/latest/org/aspectj/lang/ProceedingJoinPoint.html
     */

    @Around("helloServiceMethod()")
    // @Around(value = "method pointcut") // jenis Aspect advice memiliki kesempatan untuk menjalankan aksi sebelum dan setelah
    public Object aroundHelloServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // Advice Parameter, mendapatkan detail resource
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        try {
            log.info("Detail Proceeding Join Point : Advice Around Before: {}.{}()", className, methodName);
            // Object proceed(Object[] var1) throws Throwable; // Lanjutkan dengan nasihat berikutnya atau pemanggilan metode target
            // Object[] getArgs(); //
            return joinPoint.proceed(joinPoint.getArgs());

        } catch (Throwable throwable) {
            log.info("Detail Proceeding Join Point: Advice Around Error: {}.{}()", className, methodName);
            throw throwable;
        } finally {
            log.info("Detail Proceeding Join Point: Advice Around Finally: {}.{}()", className, methodName);
        }

    }

    /**
     * Pointcut Expression Format
     * ● Sebelumnya kita sudah coba menggunakan Pointcut Expression untuk target
     * ● Tiap jenis Pointcut memiliki format sendiri-sendiri, dan yang paling sering digunakan biasanya adalah execution
     * <p>
     * Non Execution
     * ● Untuk format yang Pointcut Expression yang buka execution sangat sederhana, cukup sebutkan nama target nya
     * ● Target juga bisa menggunakan regex jika kita mau ke lebih dari satu Type
     * <p>
     * Contoh Non Execution
     * ● within(com.xyz.service.*) : Semua method di bean di package service
     * ● within(com.xyz.service..*) : Semua method di bean di package service dan di sub package nya
     * ● target(com.xyz.service.AccountService) : Semua method di bean AccountService
     * ● args(java.lang.String, java.lang.String) : Semua method di bean yang memiliki dua parameter String
     * ● @target(org.springframework.transaction.annotation.Transactional) : Semua method yang memiliki annotation Transactional
     * ● bean(traceService) : Semua method di bean dengan nama traceService
     * ● bean(*Service) : Semua method di bean dengan akhiran Service
     * <p>
     * Execution
     * ● Untuk execution, format expression nya lebih kompleks, yaitu dengan format :
     * ● execution(modifier-pattern type-patter.method-pattern(param-pattern) throws-pattern)
     * ● https://www.eclipse.org/aspectj/doc/released/progguide/semantics-pointcuts.html
     * <p>
     * Contoh Execution
     * ● execution(public * *(..)) : Semua method public
     * ● execution(* set*(..)) : Semua method dengan prefix set
     * ● execution(* com.xyz.service.AccountService.*(..)) : Semua method di class AccountService
     * ● execution(* com.xyz.service.*.*(..)) : Semua method di package service
     * ● execution(* com.xyz.service..*.*(..)) : Semua method di package service dan sub package nya
     */

    @Pointcut("execution(* com.tutorial.aop.service.HelloService.*(java.lang.String))")
    public void pointcutHelloServiceStringParam() {

    }

    //@Before("pointcutHelloServiceStringParam()")
    //public void logStringParameter(JoinPoint joinPoint){

    //  String value = (String) joinPoint.getArgs()[0]; // Object[] getArgs(); // return parameter dari method yang ada di joint point dengan array
    //log.info("Execute method with parameter : {}", value);
    // }

    /**
     * Multiple Pointcut
     * ● Saat kita menggunakan Pointcut atau Advice, kita bisa menggunakan lebih dari satu Pointcut Expression
     * ● Hal ini kadang bermanfaat ketika kita mau melakukan kombinasi untuk beberapa Pointcut sehingga lebih mudah digunakan
     * ● Misal kita membuat pointcut khusus untuk package service
     * ● Lalu kita membuat pointcut khusus untuk bean dengan suffix Service
     * ● Lalu kita membuat pointcut khusus untuk public method
     * ● Kita bisa gabung semuanya sehingga terbentuk pointcut baru, package service, semua bean yang suffix nya Service dan method nya public
     * ● Kita bisa gunakan tanda && untuk menggabungkan Pointcut
     */

    @Before("pointcutHelloServiceStringParam() && args(name)") // args(name) // karena kita sudah return parameter yang ada di method join point jadi tidak perlu lagi menggunakan object lalu mengkonversinya
    public void logStringParameter(String name) {
        log.info("Execute method with parameter : {}", name);
    }

    @Pointcut("execution(* com.tutorial.aop.service.*.*(..))")
    public void pointcutServicePackage(){

    }

    @Pointcut("bean(*Service)")
    public void pointcutServiceBean(){

    }

    @Pointcut("execution(public * *(..))")
    public void pointcutPublicMethod(){

    }

    // multiple pointcut
    @Pointcut("pointcutServicePackage() && pointcutServiceBean() && pointcutPublicMethod()")
    public void publicMethodForService(){

    }

    @Before("publicMethodForService()")
    public void logAllPublicServiceMethod(){
        log.info("Log for all public service method");
    }


}
