package com.solvd.pool;

import static com.solvd.pool.Main.pauseRnd;

public class Connection extends Thread {
    private int number;

    public Connection() {
    }

    public Connection(int number) {
        this.number = number;
    }

    @Override // для выполнения моей задачи надо заовеерайдить метод run
    public void run() { // выполнится при запуске множества потоков из списка connections
        System.out.println("Connection start");
        pauseRnd(3);
        System.out.println("Connection end");
    }

    public synchronized Connection getConnection() { // незнаю зачем
        System.out.println("Connection start");
        pauseRnd(3);
        System.out.println("Connection end");
        return new Connection();
    }

//    Есть какой то класс (обычный) с 5 методами (например CRUD f.e System.out.println(“I saved”)), назовите его Connection
//    а зачем эти пять методов? конекшен будет выполнять только run()?


    public void create() {
        System.out.println("Create");
    }

    public void read() {
        System.out.println("Read");
    }

    public void update() {
        System.out.println("Update");
    }

    public void delete() {
        System.out.println("Delete");
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
