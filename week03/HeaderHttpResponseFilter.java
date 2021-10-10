package org.example.week02.netty;

import io.netty.handler.codec.http.FullHttpResponse;
import org.example.week03.gateway.filter.HttpResponseFilter;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
    }
}
