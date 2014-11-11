package com.vijayrc.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    static final String baseDir = System.getProperty("user.dir");
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    String template();
    boolean canHandle(String path);
}
