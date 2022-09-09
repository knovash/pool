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


        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                Connection connection = connectionPool.retrieve(); // получить соединение в пуле
                connection.read(); // выполнение конекшена. например чтение. со случайной задержкой
                connectionPool.releaseConnection(connection); // после выполнения освободить соединение
            }).start();

        }


//        в мэйне мы создаем множество потоков имитруя пользователей
//        и в параметрах каждого потока обращаемся к пулу за коннекшином
//        но как? вот тут не понятно...
//        в конекшен пуле есть списки availableConns и usedConns. надо както туда передать список connections ???
        // я в конекшенпул при создании передаю толлько размер пула


// Михаил
// Суть пула в том что мы создаем конекшины при инициализации пула,
// помещаем их в какую нибудь коллекцию и потом ими постоянно пользуемя,
// а утебя в методе getConnection  создаются новые Connection каждый раз при вызове метод

    }


    public static void pause(int seconds) {  // для иммитации задержки работы конекшена
        try {
            Thread.sleep(seconds * 2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int pauseRnd(int seconds) { // для иммитации случайной задержки работы конекшена
        int t = (int) (Math.random() * seconds);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}