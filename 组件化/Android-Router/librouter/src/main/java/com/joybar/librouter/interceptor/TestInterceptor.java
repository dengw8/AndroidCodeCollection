package com.joybar.librouter.interceptor;


import com.joybar.librouter.RouterRequest;

/**
 * Created by joybar on 15/11/2017.
 */

public class TestInterceptor implements RouteInterceptor {
    @Override
    public boolean isIntercepted(RouterRequest routerRequest) {
       // Toast.makeText(routerRequest.getContext(), "I am a test interceptor", Toast.LENGTH_LONG).show();
        return false;
    }
}
