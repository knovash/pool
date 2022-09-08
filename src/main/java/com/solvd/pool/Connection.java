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
    public void run() {
        int t = pauseRnd(5);
        System.out.println("Connection run " + number + " t=" + t);
    }

    public synchronized Connection getConnection() {
        
        System.out.println("Connection start");
        pauseRnd(3);
        System.out.println("Connection end");
        return new Connection();
    }

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
