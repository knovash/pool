package com.solvd.pool;

import java.sql.Statement;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    // у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне
    // объект будет создаваться внутри класса статическим методом
    private static ConnectionPool INSTANCE; // поле класса для проверки существования, в него заносим тот единственный объект этого класа
    private static int poolSize;
    private int conNum = 0;
    private BlockingQueue<Connection> availableConns = new LinkedBlockingDeque<>(); // доступные для использования соединения
    private BlockingQueue<Connection> usedConns = new LinkedBlockingDeque<>(); // используемые

    private ConnectionPool(int poolSize) { // приватный конструктор
        System.out.println("Constructor ConnectionPool size=" + poolSize);
//        List<Connection> pool = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            System.out.println("availableConns.add(getConnection()) i=" + i);
            availableConns.add(createConnection()); // getConnection создает новое подключние
        }
    }

    //метод для создание объекта класса ConnectionPool. создать инстанс
    public static ConnectionPool getInstance(int poolSize) { // первый раз для создания объекта вызываем метод getInstance
        // сначало проверить небыл ли создан ранее. для этого есть поле
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(poolSize); // если небыло то создается объект
            System.out.println("\nCreated ConnectionPool size=" + poolSize);
        }
        return INSTANCE; // если был создан уже то возвращается ранее созданный объект
    }

    public static Connection createConnection() {
        System.out.println("createConnection:");
        Connection connectionNew = null;
        connectionNew = new Connection(); // = DriverManager.getConnection(...);
        return connectionNew;
    }

    public synchronized Connection getConnection() throws InterruptedException { // надо забирать конекшн из списка connections ?
        Connection connection = null;
        if (availableConns.size() > 0) { // если пул свободных конекшенов больше нуля
            connection = availableConns.take(); // забираем из свободных
            usedConns.add(connection); // добавляем его в активные
        }
        else { // если пулл переполнен.
            throw new RuntimeException("no free connections");
        }
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) throws NullPointerException {
        if (connection == null) {
            throw new NullPointerException("connection null");
        } // если конекшен нул
        if (!usedConns.remove(connection)) {
            throw new RuntimeException("connection not for this pool");
        } // удаляем конекшен из пула активных конекшенов
        availableConns.add(connection); // добавляем его в пул свободных конекшенов
    }
}
