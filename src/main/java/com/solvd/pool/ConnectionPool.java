package com.solvd.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    /** у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне
    объект будет создаваться внутри класса статическим методом */
    private static ConnectionPool INSTANCE; /** поле класса для проверки существования, в него заносим тот единственный объект этого класа */
    private static int poolSize;
    private int conNum = 0;
    private BlockingQueue<Connection> availableConns = new LinkedBlockingQueue<>(); /** доступные для использования соединения */
    private BlockingQueue<Connection> usedConns = new LinkedBlockingQueue<>(); /** используемые */

    private ConnectionPool(int poolSize) { /** приватный конструктор */
        System.out.println("Constructor ConnectionPool size=" + poolSize);
        for (int i = 0; i < poolSize; i++) {
            System.out.println("availableConns.add(getConnection()) i=" + i);
            availableConns.add(createConnection()); /** getConnection создает новое подключние */
        }
    }

    /** метод для создание объекта класса ConnectionPool. создать инстанс */
    public static ConnectionPool getInstance(int poolSize) { /** первый раз для создания объекта вызываем метод getInstance */
        /** сначало проверить небыл ли создан ранее. для этого есть поле */
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(poolSize); /** если небыло то создается объект */
            System.out.println("\nCreated ConnectionPool size=" + poolSize);
        }
        return INSTANCE; /** если был создан уже то возвращается ранее созданный объект */
    }

    public static Connection createConnection() {
        System.out.println("createConnection:");
        return new Connection();
    }

    public synchronized Connection getConnection() { /** надо забирать конекшн из списка connections */
        Connection connection = null;
        try {
            connection = availableConns.take(); /** забираем из свободных */
            usedConns.add(connection); /** добавляем его в активные */
        } catch (InterruptedException e) {
            System.out.println("no free connections");
        }
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        usedConns.remove(connection); /** удаляем конекшен из пула активных конекшенов */
        availableConns.add(connection); /** добавляем его в пул свободных конекшенов */
    }
}
