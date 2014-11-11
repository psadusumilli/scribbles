package com.capitalone.p2p.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xwg532 on 11/9/14.
 */
public class LogAction extends BaseAction{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    @Override
    public String template() {
        return "<body style='font-family:Arial'></body>";
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/log");
    }
}
