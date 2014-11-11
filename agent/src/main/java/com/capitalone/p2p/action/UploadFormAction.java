package com.capitalone.p2p.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xwg532 on 11/9/14.
 */
public class UploadFormAction extends BaseAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return template();
    }

    @Override
    public String template() {
        return "<body style='font-family:Arial'><h2 style='color:#6495ED'>Select file to upload..</h2><hr>" +
                "<form method=\"POST\" action=\"upload\" enctype=\"multipart/form-data\">" +
                "<input type=\"file\" name=\"artifact\" id=\"artifact\" /> <br/>" +
                "<input type=\"submit\" value=\"Upload\" name=\"upload\" id=\"upload\" />\n" +
                "</form></body>";
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/form");
    }
}
