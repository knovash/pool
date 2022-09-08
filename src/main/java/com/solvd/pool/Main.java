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

        // создаю конекшен пулл на 5 потоков
        ConnectionPool connectionPool = ConnectionPool.getInstance(5);

        List<Connection> connections = new ArrayList<>(); // создаю список конекшнов

        // создаем множетво потоков иммитируя обращения пользователей
        // создаю конекшены. добавляю их в список конекшенов и запускаю каждый
        for (int i = 0; i < 20; i++) {
            Connection con = new Connection(1);
            connections.add(con);
            connections.get(connections.size()-1).start();
            System.out.println("connection created " + i + " and added to connections");
        }
        // в параметрах каждого потока надо обратиться к пулу за конекшеном.... Но как ?????


    }


    public static void pause(int seconds) {  // для иммитацыи задержки работы конекшена
        try {
            Thread.sleep(seconds * 2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int pauseRnd(int seconds) { // для иммитацыи случайной задержки работы конекшена
        int t = (int) (Math.random() * seconds);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}