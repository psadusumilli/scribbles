package com.vijayrc.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.vijayrc.agent.util.Logger.*;

public class ShellAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
            String script = request.getParameter("script");
            String output;

            log("running script "+script);
            ProcessBuilder pb = new ProcessBuilder(Action.baseDir + script);
            Process process = null;
            try {
                process = pb.start();
                output = readOutput(process);
                log("process output: " + output);
            } catch (Exception e) {
                e.printStackTrace();
                output = e.getMessage();
                log("process error!!");
            } finally {
                if (process != null) process.destroy();
                log("process stopped");
            }
        Map<String, String> model = new HashMap<String, String>();
        model.put("$output", output);
        return fill(template(), model);
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/run");
    }

    @Override
    public String template() {
        return "<body style='font-family:Arial'><h1 style='color:#6495ED'>Ran your script..</h1><hr>" +
                "<h3>Output:</h3><pre>$output</pre></body>";
    }

    private String readOutput(Process process) throws IOException {
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = br.readLine()) != null)
            builder.append(line).append("\n");
        return builder.toString();
    }
}
