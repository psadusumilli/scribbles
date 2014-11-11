package com.capitalone.p2p.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.capitalone.p2p.util.Logger.log;
import static java.lang.System.getProperty;

/**
 * Created by xwg532 on 11/9/14.
 */
public class UploadAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final Part filePart = request.getPart("artifact");
        final String srcPath = getFileName(filePart);
        final String destPath = Action.baseDir + "/" + srcPath;

        String output;
        OutputStream target = null;
        InputStream source = null;
        try {
            target = new FileOutputStream(new File(destPath));
            source = filePart.getInputStream();
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = source.read(bytes)) != -1)
                target.write(bytes, 0, read);

            output = "New file " + srcPath + " created at " + destPath;
            log(output);

            Map<String, String> model = new HashMap<String, String>();
            model.put("$srcPath", srcPath);
            model.put("$destPath", destPath);
            return fill(template(), model);

        } catch (Exception e) {
            output = e.getMessage();
            e.printStackTrace();
        } finally {
            if (target != null) target.close();
            if (source != null) source.close();
        }
        return output;
    }

    @Override
    public String template() {
        return "<body style='font-family:Arial'>" +
                "<p>New file $srcPath created at <span style='color:#6495ED'>$destPath</span></p></body>";
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/upload");
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        log("Part Header = " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";"))
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
        return null;
    }
}
