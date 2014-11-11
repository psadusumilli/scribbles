package com.capitalone.p2p.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xwg532 on 11/9/14.
 */
public interface Action {
    static final String baseDir = System.getProperty("user.dir");

    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception;
    String template();
    boolean canHandle(String path);

}
