package com.solvd.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
Создать 2 класса: ConnectionPool и Connection (см выше). List<Connection> внутри ConnectionPool. Будут методы внутри пула:
        - getInstance (Singleton - потокобезопасный)            +
        - synchronize Connection getConnection()                +
        - void releaseConnection(Connection connection)         +
        */

public class ConnectionPool {

    /** у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне объект будет создаваться внутри класса статическим методом */
    private static ConnectionPool INSTANCE; /** поле класса для проверки существования, в него заносим тот единственный объект этого класа */

    private final BlockingQueue<Connection> availableConns = new LinkedBlockingQueue<>(); /** доступные для использования соединения */
    private final BlockingQueue<Connection> usedConns = new LinkedBlockingQueue<>(); /** используемые */

    private ConnectionPool(int poolSize) { /** приватный конструктор */
        System.out.println("Constructor: ConnectionPool size=" + poolSize);
        for (int i = 0; i < poolSize; i++) {
            System.out.println("availableConns.add(createConnection() i=" + i);
            availableConns.add(createConnection()); /** createConnection создает новое подключние */
        }
        System.out.println("availableConns: " + availableConns);
        System.out.println("Constructor: availableConns.size=" + availableConns.size());

    }

    /**  метод для создание объекта класса ConnectionPool. создать инстанс */
    public static ConnectionPool getInstance(int poolSize) { /** первый раз для создания объекта вызываем метод getInstance */
        /** сначало проверить небыл ли создан ранее. для этого есть поле */
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(poolSize); /** если небыло то создается объект */
            System.out.println("\ngetInstance: Created ConnectionPool size=" + poolSize);
        }
        return INSTANCE; /** если был создан уже то возвращается ранее созданный объект */
    }

    public static Connection createConnection() {
        System.out.println("createConnection:");
        return new Connection(); // этот класс можно вообще удалить...
    }

    public synchronized Connection getConnection() { /** надо забирать конекшн из списка connections */
        System.out.println("\nSTART getConnection: used=" + usedConns.size() + " availible=" + availableConns.size());
        Connection connection = null;
        try {
            connection = availableConns.take(); /** забираем из свободных */
            System.out.println("availableConns.take " + connection);
            usedConns.offer(connection); /** добавляем его в активные */
            System.out.println("try getConnection ok");
        } catch (InterruptedException e) {
            System.out.println("try getConnection: error");
        }
        System.out.println("END getConnection: used=" + usedConns.size() + " availible=" + availableConns.size());
        System.out.println("availableConns: " + availableConns);
        System.out.println("usedConns: " + usedConns);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        System.out.println("\nSTART releaseConnection: used=" + usedConns.size() + " availible=" + availableConns.size());
        usedConns.remove(connection); /** удаляем конекшен из пула активных конекшенов */
        availableConns.offer(connection); /** добавляем его в пул свободных конекшенов */
        System.out.println("END releaseConnection: used=" + usedConns.size() + " availible=" + availableConns.size());
    }
}
