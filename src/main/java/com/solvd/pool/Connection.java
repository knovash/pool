package com.solvd.pool;

public class Connection {

    public void create() {
        System.out.println("Create");
    }

    public void read(int i) {
        int t = (int) (1 + Math.random() * 3);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" Connection READ completed i=" + i + " pause t=" + t);
    }

    public void update() {
        System.out.println("Update");
    }

    public void delete() {
        System.out.println("Delete");
    }
}