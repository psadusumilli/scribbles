package com.vijayrc.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadAction extends BaseAction{
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
