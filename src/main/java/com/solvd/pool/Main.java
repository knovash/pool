package com.solvd.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

// Создать connection pool с фиксированным количеством коннекшенов и много потоков (с помощью Runnable и Thread)

        System.out.println("test ConnectionPull");
        List<Connection> connections = new ArrayList<>();
        Connection con1 = new Connection(1);
        Connection con2 = new Connection(2);
        Connection con3 = new Connection(3);
        Connection con4 = new Connection(4);
        Connection con5 = new Connection(5);
        connections.add(con1);
        connections.add(con2);
        connections.add(con3);
        connections.add(con4);
        connections.add(con5);

        ConnectionPool cp = ConnectionPool.getInstance(10);
        cp.retrieve();


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