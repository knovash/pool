package com.solvd.pool;

import static com.solvd.pool.Main.pauseRnd;

public class Connection {

    public void create() {
        System.out.println("Create start");
        pauseRnd(3);
        System.out.println("Create end");
        System.out.println("Create");
    }

    public void read() {
        System.out.println("Read start");
        pauseRnd(3);
        System.out.println("Read end");
        System.out.println("Read");
    }

    public void update() {
        System.out.println("Update start");
        pauseRnd(3);
        System.out.println("Update end");
        System.out.println("Update");
    }

    public void delete() {
        System.out.println("Delete start");
        pauseRnd(3);
        System.out.println("Delete end");
        System.out.println("Delete");
    }
}
