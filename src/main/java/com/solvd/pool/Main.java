package com.solvd.pool;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nMain: ConnectionPool.getInstance: Create conection pool size=5\n");
        ConnectionPool connectionPool = ConnectionPool.getInstance(5);

        System.out.println("\nMain: Start for 20 create connections\n");
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            new Thread(() -> {
                Connection connection = null; // получить соединение в пуле
                try {
                    connection = connectionPool.getConnection();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                connection.read(finalI); // выполнение конекшена. например чтение. со случайной задержкой
                System.out.println("Main: connectionPool.releaseConnection(connection) " + finalI);
                connectionPool.releaseConnection(connection); // после выполнения освободить соединение
            }).start();

        }
    }
}