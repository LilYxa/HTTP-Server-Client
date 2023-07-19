package com.LiLYxa.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Server {
    private final static int BUFFER_SIZE = 256;
    private AsynchronousServerSocketChannel server;

    private static final String HEADERS = """
                                            HTTP/1.1 200 OK
                                            Server: LilYxaServer
                                            Content-Type: text/html
                                            Content-Length: %s
                                            Connection: close\n\n
                                          """;

    public void bootstrap() {
        try {
            server = AsynchronousServerSocketChannel.open();
            server.bind(new InetSocketAddress("127.0.0.1", 80));

            while (true) {
                Future<AsynchronousSocketChannel> future = server.accept();
                handleClient(future);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    private static void handleClient(Future<AsynchronousSocketChannel> future)
            throws InterruptedException, ExecutionException, TimeoutException, IOException {
        System.out.println("New client thread");

        AsynchronousSocketChannel clientChannel = future.get(30, TimeUnit.SECONDS);

        while (clientChannel != null && clientChannel.isOpen()) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            StringBuilder builder = new StringBuilder();
            boolean keepReading = true;

            while (keepReading) {
                clientChannel.read(buffer).get();

                int position = buffer.position();
                keepReading = position == BUFFER_SIZE;

                byte[] array = keepReading
                        ? buffer.array()
                        : Arrays.copyOfRange(buffer.array(), 0, position);

                builder.append(new String(array));
                buffer.clear();
            }

            String body = "<html><body><h1>Hello, that's a simple server example!</h1></body></html>";
            String page = String.format(HEADERS, body.length()) + body;

            ByteBuffer response = ByteBuffer.wrap(page.getBytes());
            clientChannel.write(response);

            clientChannel.close();

        }
    }
}
