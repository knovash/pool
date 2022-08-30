package com.solvd.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        System.out.println("test ConnectionPull");

        // Создать connection pool с фиксированным количеством коннекшенов и много потоков (с помощью Runnable и Thread)

//        System.out.println("\nRun one connection");
//        Connection connection1 = new Connection(1);
//        connection1.start(); // запуск потока
//        Connection connection2 = new Connection(2);
//        connection2.start();
//        Connection connection3 = new Connection(3);
//        connection3.start();


        // запуск потока стримом 10 раз
//        System.out.println("\nRun 10 connection");
//        IntStream.range(0, 10)
//                .forEach(i -> new Connection(i).start());


        ConnectionPool cp = ConnectionPool.getInstance(10);

//        cp.getConnection();
//        cp.putback(new Connection());
//        cp.retrieve();
//        cp.getAvailableConnsCnt();

//        Connection con = cp.getConnection();
//        Connection con1 = cp.getConnection();
//        Connection con2 = cp.getConnection();





//        ExecutorService es = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 10; i++){
//            es.execute(new MyThred(i+"A"));
//            es.execute(new MyThred(i+"B"));
//            es.execute(new MyThred(i+"C"));
//            es.execute(new MyThred(i+"D"));
//            es.execute(new MyThred(i+"E"));
//        }
//        es.shutdown();
    }

    static class MyThred extends Thread {
        String name;

        public MyThred(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i < 2; i++)
                System.out.println(i + " " + name);
        }
    }

    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int pauseRnd(int seconds) {
        int t = (int) (Math.random() * seconds);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}