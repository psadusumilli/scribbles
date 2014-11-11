package com.capitalone.p2p;


import com.capitalone.p2p.action.AllActions;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static java.lang.System.getProperty;

/**
 * Created by xwg532 on 11/6/14.
 */
public class AgentHandler extends AbstractHandler {

    private AllActions allActions = new AllActions();

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        forUpload(baseRequest, request);

        String content = allActions.execute(request, response);

        baseRequest.setHandled(true);
        response.getWriter().println(content);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void forUpload(Request baseRequest, HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data"))
            baseRequest.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT,
                    new MultipartConfigElement(getProperty("java.io.tmpdir")));
    }


}
