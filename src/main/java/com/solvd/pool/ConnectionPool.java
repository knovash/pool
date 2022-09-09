package com.solvd.pool;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    // у сингл тона должен быть приватный конструктор. так запрещает создавать объект класса извне
    // объект будет создаваться внутри класса статическим методом
    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
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


    private Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    private boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Connection> getUsedConnections() {
        return usedConnections;
    }

    public void setUsedConnections(List<Connection> usedConnections) {
        this.usedConnections = usedConnections;
    }

    public static int getInitialPoolSize() {
        return INITIAL_POOL_SIZE;
    }

    public static void setInitialPoolSize(int initialPoolSize) {
        INITIAL_POOL_SIZE = initialPoolSize;
    }
}
