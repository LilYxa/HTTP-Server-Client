package com.LiLYxa.http;

import com.LiLYxa.http.server.Server;

public class Main {
    public static void main(String[] args) {

        new Server((request, response) -> {
            return "<html><body><h1>Hello, that's a simple server example!</h1></body></html>";
        }).bootstrap();

//        Thread serverThread = new Thread(() -> new Server().bootstrap());
//        serverThread.start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Thread clientThread = new Thread(() -> HttpClient.connection("127.0.0.1", 80));
//        clientThread.start();
    }
}
