package com.LiLYxa.http.server;

public interface HttpHandler {
    String handle(HttpRequest request, HttpResponse response);
}
