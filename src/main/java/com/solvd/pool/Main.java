package com.solvd.pool;

public class Main {

    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPool.getInstance(3);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("START new thread i=" + finalI);
                Connection connection = connectionPool.getConnection(); /** получить соединение в пуле */
                connection.read(finalI); /** выполнение конекшена. например чтение. со случайной задержкой */
                connectionPool.releaseConnection(connection); /** после выполнения освободить соединение */
            }).start();
        }
    }
}