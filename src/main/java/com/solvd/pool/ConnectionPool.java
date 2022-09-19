package com.solvd.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Создать 2 класса: ConnectionPool и Connection (см выше). List<Connection> внутри ConnectionPool. Будут методы внутри пула:
 * - getInstance (Singleton - потокобезопасный)            +
 * - synchronize Connection getConnection()                +
 * - void releaseConnection(Connection connection)         +s*/

public class ConnectionPool {

    /** у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне объект будет создаваться внутри класса статическим методомs*/
    private static ConnectionPool INSTANCE;
    /** поле класса для проверки существования, в него заносим тот единственный объект этого класа */

    private final BlockingQueue<Connection> availableConns = new LinkedBlockingQueue<>(); /** доступные для использования соединенияs*/
    private final BlockingQueue<Connection> usedConns = new LinkedBlockingQueue<>(); /** используемыеs*/

    private ConnectionPool(int poolSize) { /** приватный конструктор */
        for (int i = 0; i < poolSize; i++) {
            availableConns.add(createConnection()); /** createConnection создает новое подключние */
        }
    }

    /** метод для создание объекта класса ConnectionPool. создать инстансs*/
    public static ConnectionPool getInstance(int poolSize) { /** первый раз для создания объекта вызываем метод getInstance */
        /** сначало проверить небыл ли создан ранее. для этого есть поле */
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(poolSize); /** если небыло то создается объект */
            System.out.println("\nGETINSTANCE created ConnectionPool size=" + poolSize);
        }
        return INSTANCE; /** если был создан уже то возвращается ранее созданный объект */
    }

    public static Connection createConnection() {
        return new Connection(); // этот класс можно вообще удалить...
    }

    public synchronized Connection getConnection() { /** надо забирать конекшн из списка connections */
        Connection connection = null;
        try {
            connection = availableConns.take(); /** забираем из свободных */
            usedConns.offer(connection); /** добавляем его в активные */
        } catch (InterruptedException e) {
            System.out.println("try getConnection: error");
        }
        System.out.println("GET connection: used=" + usedConns.size() + " availible=" + availableConns.size());
        return connection;
    }

    public void releaseConnection(Connection connection) {
        usedConns.remove(connection); /** удаляем конекшен из пула активных конекшенов */
        availableConns.offer(connection); /** добавляем его в пул свободных конекшенов */
        System.out.println("RELEASE connection: used=" + usedConns.size() + " availible=" + availableConns.size());
    }
}