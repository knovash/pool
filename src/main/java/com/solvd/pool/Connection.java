package com.solvd.pool;

public class Connection {

    public void create() {
        System.out.println("Create");
    }

    public void read(int i) {
        int t = (int) (1 + Math.random() * 6);
        System.out.println("Connection: Read START i=" + i + " pause t=" + t);
        try {
            Thread.sleep(t * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection: Read END i=" + i + " pause t=" + t);
    }

    public void update() {
        System.out.println("Update");
    }

    public void delete() {
        System.out.println("Delete");
    }
}