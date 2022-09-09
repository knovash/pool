package com.solvd.pool;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    // у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне
    // объект будет создаваться внутри класса статическим методом
    private static ConnectionPool INSTANCE; // поле класса для проверки существования, в него заносим тот единственный объект этого класа
    private static int poolSize;
    private List<Connection> availableConns = new ArrayList<>(); // доступные для использования соединения
    private List<Connection> usedConns = new ArrayList<>(); // используемые

    // лист конекшенов надо просетать в конекшенпул??? сделать сеттер?

    private ConnectionPool(int poolSize) { // приватный конструктор
        System.out.println("Constructor ConnectionPool size=" + poolSize);
        for (int i = 0; i < poolSize; i++) {
            System.out.println("availableConns.add(getConnection()) i=" + i);
            availableConns.add(getConnection()); // getConnection создает новое подключние
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


    private synchronized Connection getConnection() { // надо забирать конекшн из списка connections ?
        System.out.println("getConnection: new connection");
        // создает новое подключение
        Connection connection = new Connection();
//        Connection connection = availableConns.remove(availableConns.size()-1);
        return connection;
    }

    public synchronized Connection retrieve() {
        System.out.println("\nretrieve:");
        System.out.println("availableConns.size()=" + availableConns.size());
        System.out.println("usedConns.size()=" + usedConns.size());
        //забирает из aviable и добавляет его в used
        // затем возвращает это соединение, оно становится используемым
        Connection newConn = null;
        // проверяем есть ли свободные соединения
        if (availableConns.size() == 0) {
            newConn = getConnection(); // если нет то создаем новое соединение
        } else {
            // забирает из availableConns крайний Connection
            newConn = (Connection) availableConns.get(availableConns.size()-1);
            availableConns.remove(availableConns.size()-1);
        }
        // добавляет его в usedConns
        usedConns.add(newConn);
        System.out.println("usedConns.add(newConn) usedConns.size()=" + usedConns.size());
        // затем возвращает это соединение
        return newConn; // тем самым он становится используемым
    }

    public synchronized void releaseConnection(Connection connection) throws NullPointerException {
        System.out.println("\nreleaseConnection");
        System.out.println("availableConns.size()=" + availableConns.size());
        System.out.println("usedConns.size()=" + usedConns.size());
        if (connection != null) {
            if (usedConns.remove(connection)) {
                availableConns.add(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConns");
            }
        }
    }

    // получить количество свободных соединений
    public int getAvailableConnsCnt() {
        return availableConns.size();
    }

    public static int getPoolSize() {
        return poolSize;
    }

    public static void setPoolSize(int poolSize) {
        ConnectionPool.poolSize = poolSize;
    }
}
