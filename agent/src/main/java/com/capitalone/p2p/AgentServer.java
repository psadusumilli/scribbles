package com.capitalone.p2p;


import org.eclipse.jetty.server.Server;

/**
 * Created by xwg532 on 11/6/14.
 */
public class AgentServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new AgentHandler());
        server.start();
        server.join();
    }

}
