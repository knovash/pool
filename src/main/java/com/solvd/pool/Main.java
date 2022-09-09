package com.solvd.pool;

public class Main {

    public static void main(String[] args) {

// Создать connection pool с фиксированным количеством коннекшенов и много потоков (с помощью Runnable и Thread)

        System.out.println("ConnectionPull");

        // создаю конекшен пулл на 5 потоков
        System.out.println("\nTry create conection pool size=5\n");
        ConnectionPool connectionPool = ConnectionPool.getInstance(5);

        System.out.println("\nStart for 20 create connections\n");
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            new Thread(() -> {
                Connection connection = connectionPool.retrieve(); // получить соединение в пуле
                connection.read(finalI); // выполнение конекшена. например чтение. со случайной задержкой
                System.out.println("connectionPool.releaseConnection(connection)");
                connectionPool.releaseConnection(connection); // после выполнения освободить соединение
            }).start();

        }


//        в мэйне мы создаем множество потоков имитруя пользователей
//        и в параметрах каждого потока обращаемся к пулу за коннекшином
//        но как? вот тут не понятно...
//        в конекшен пуле есть списки availableConns и usedConns. надо както туда передать список connections ???
        // я в конекшенпул при создании передаю толлько размер пула


// Михаил
// Суть пула в том что мы создаем конекшины при инициализации пула,
// помещаем их в какую нибудь коллекцию и потом ими постоянно пользуемя,
// а утебя в методе getConnection  создаются новые Connection каждый раз при вызове метод

    }


    public static void pause(int seconds) {  // для иммитации задержки работы конекшена
        try {
            Thread.sleep(seconds * 2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int pauseRnd(int seconds) { // для иммитации случайной задержки работы конекшена
        int t = (int) (Math.random() * seconds);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}