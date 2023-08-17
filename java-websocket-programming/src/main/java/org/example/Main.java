package org.example;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        Context ctx = tomcat.addContext("", null);

        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();

    }
}