package com.company;

import java.io.IOException;

public class HttpInvoker {

    private Http http;

    public HttpInvoker(Http http) {
        this.http = http;
    }

    public String getHttp(String url) throws IOException {
        return this.http.get(url);
    }

}
