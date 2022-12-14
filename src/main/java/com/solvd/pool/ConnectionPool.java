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

    private ConnectionPool(int poolSize) { // приватный конструктор
        //.....
        for (int i = 0; i < poolSize; i++) {
            System.out.println("add connection " + i);
            availableConns.add(getConnection());
        }
        for (Connection con : availableConns) {
            System.out.println("start" );
            con.start();
        }
    }

//  Будут методы внутри пула:
//- getInstance (Singleton - потокобезопасный)
//- synchronize Connection getConnection()
//- void releaseConnection(Connection connection)

    //метод для создание объекта класса. создать инстанс
    public static ConnectionPool getInstance(int poolSize) { // первый раз для создания объекта вызываем метод getInstance
        // сначало проверить небыл ли создан ранее. для этого есть поле
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(poolSize); // если небыло то создается объект
        }
        return INSTANCE; // если был создан уже то возвращается ранее созданный объект
    }

    public synchronized Connection getConnection() {
        // создает новое подключение
        System.out.println("get connection");
        return new Connection(3);
    }


    public synchronized Connection retrieve() {
        Connection newConn = null;
        // проверяем есть ли свободные соединения
        if (availableConns.size() == 0) {
            newConn = getConnection(); // если нет то создаем новое соединение
        } else {
            // забирает из availableConns крайний Connection
            newConn = (Connection) availableConns.get(availableConns.size());
            availableConns.remove(availableConns.size());
        }
        // добавляет его в usedConns
        usedConns.add(newConn);
        // затем возвращает это соединение
        return newConn; // тем самым он становится используемым
    }

    public synchronized void releaseConnection (Connection connection) throws NullPointerException {
        if (connection != null) {
            if (usedConns.remove(connection)) {
                availableConns.add(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConns");
            }
        }
    }

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
