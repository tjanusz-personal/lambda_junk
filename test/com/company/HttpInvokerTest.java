package com.company;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class HttpInvokerTest {

    @Ignore
    @Test
    public void doHttpCallReal() throws IOException {
        HttpImpl httpImpl = new HttpImpl();
        HttpInvoker httpInvoker = new HttpInvoker(httpImpl);
        String result = httpInvoker.getHttp("http://www.google.com");
        System.out.println(result);
    }

    @Test
    public void doHttpCallMock() throws IOException {
        Http stubHttp = (String url) ->
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}";
        HttpInvoker httpInvoker = new HttpInvoker(stubHttp);
        String result = httpInvoker.getHttp("http://www.google.com");
        System.out.println(result);
    }


}