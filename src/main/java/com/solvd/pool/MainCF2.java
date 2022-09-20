package com.solvd.pool;

import java.util.concurrent.CompletableFuture;

public class MainCF2 {

    public static void main(String[] args) {
        /** CompletableFuture для запуска потоков */
        for (int i = 0; i < 10; i++) {
            CompletableFuture<Void> с = CompletableFuture.runAsync(MainCF2::connectionThread); /** запуск потока */
            с.join(); /** дожидаемся завершения потока */
        }
    }

//        for (int i = 0; i < 10; i++) {
//            int finalI = i;
//            new Thread(() -> {
//                System.out.println("START new thread i=" + finalI);
//                Connection connection = connectionPool.getConnection(); /** получить соединение в пуле */
//                connection.read(finalI); /** выполнение конекшена. например чтение. со случайной задержкой */
//                connectionPool.releaseConnection(connection); /** после выполнения освободить соединение */
//            }).start();
//        }
//        CompletableFuture<Void> cAll = CompletableFuture.allOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
//        // из многих комплфюч собирается одна, ее окончания потом и будем дожидаться
//        cAll.join(); // после завершения всех потоков. значение Void

//    CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> { // запускается в отдельный поток
//        connectionThread();
//    });
//    CompletableFuture<Void> c2 = CompletableFuture.runAsync(MainCF::connectionThread); // запускается в отдельный поток

    public static void connectionThread() {
        ConnectionPool connectionPool = ConnectionPool.getInstance(3);
        System.out.println("START new thread");
        Connection connection = connectionPool.getConnection(); /** получить соединение в пуле */
        connection.read(0); /** выполнение конекшена. например чтение. со случайной задержкой */
        connectionPool.releaseConnection(connection); /** после выполнения освободить соединение */
    }

}