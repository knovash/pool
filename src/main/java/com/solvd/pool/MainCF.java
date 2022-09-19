package com.solvd.pool;

import java.util.concurrent.CompletableFuture;

public class MainCF {

    public static void main(String[] args) {
        /** CompletableFuture для запуска потоков */
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> { // запускается в отдельный поток
            connectionThread();
        });
        CompletableFuture<Void> c2 = CompletableFuture.runAsync(MainCF::connectionThread); // запускается в отдельный поток
        CompletableFuture<Void> c3 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c4 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c5 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c6 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c7 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c8 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c9 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> c10 = CompletableFuture.runAsync(MainCF::connectionThread);
        CompletableFuture<Void> cAll = CompletableFuture.allOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
        // из многих комплфюч собирается одна, ее окончания потом и будем дожидаться
        cAll.join(); // после завершения всех потоков. значение Void
    }

    public static void connectionThread() {
        ConnectionPool connectionPool = ConnectionPool.getInstance(3);
        System.out.println("START new thread");
        Connection connection = connectionPool.getConnection(); /** получить соединение в пуле */
        connection.read(1); /** выполнение конекшена. например чтение. со случайной задержкой */
        connectionPool.releaseConnection(connection); /** после выполнения освободить соединение */
    }

}