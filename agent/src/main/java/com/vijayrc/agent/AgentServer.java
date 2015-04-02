package com.vijayrc.agent;


import org.eclipse.jetty.server.Server;

public class AgentServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8989);
        server.setHandler(new AgentHandler());
        server.start();
        server.join();
    }
}
