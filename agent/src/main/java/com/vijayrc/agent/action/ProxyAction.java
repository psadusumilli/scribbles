package com.vijayrc.agent.action;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author bridgebuilders@capitalone.com
 * @since 1.0
 */
public class ProxyAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {

        ServletInputStream servletInputStream = servletRequest.getInputStream();
        StringWriter responseWriter = new StringWriter();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(servletInputStream, writer, "UTF-8");

//            String requestStr = StringUtils.replace(writer.toString(), ">RTM</", ">Test</");
            StringEntity stringEntity = new StringEntity(writer.toString(), "UTF-8");
            stringEntity.setChunked(true);

            HttpPost post = new HttpPost("http://some-server");
            post.setEntity(stringEntity);
            post.addHeader("Accept", "text/xml");
            post.addHeader("Content-Type", "text/xml");
            post.addHeader("SOAPAction", "");

            CloseableHttpResponse response = client.execute(post);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    IOUtils.copy(resEntity.getContent(), responseWriter, "UTF-8");
                    System.out.format("response= %s | %s ", response.getStatusLine(), responseWriter.toString());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
                servletInputStream.close();
            }
        } finally {
            client.close();
        }
        return responseWriter.toString();
    }

    @Override
    public String template() {
        return null;
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/proxy");
    }
}
/*
 * Copyright 2014 Capital One Financial Corporation All Rights Reserved.
 *
 * This software contains valuable trade secrets and proprietary information of Capital One and is protected by law. It
 * may not be copied or distributed in any form or medium, disclosed to third parties, reverse engineered or used in any
 * manner without prior written authorization from Capital One.
 */

