package com.LiLYxa.http;

import java.io.*;
import java.net.Socket;

public class HttpClient {

    public static void connection(String host, int port) {
        try (Socket socket = new Socket(host, port);
             InputStream response = socket.getInputStream();
             OutputStream request = socket.getOutputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {

            System.out.println("Введите http запрос:");

            String input;
            StringBuilder httpRequestBuilder = new StringBuilder();

            while ((input = bufferedReader.readLine()) != null && !input.isEmpty()) {
                httpRequestBuilder.append(input).append("\r\n");
            }
            httpRequestBuilder.append("\r\n");
            String httpRequest = httpRequestBuilder.toString();

            byte[] data = httpRequest.getBytes();
            request.write(data);

            int c;
            while ((c = response.read()) != -1) {
                System.out.print((char) c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
