package com.capitalone.p2p.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.capitalone.p2p.action.Action.baseDir;
import static java.lang.System.getProperty;
import static com.capitalone.p2p.util.Logger.*;
import static java.lang.System.out;

/**
 * Created by xwg532 on 11/9/14.
 */
public class DeployAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String artifact = request.getParameter("artifact");
        String output = "";
        String name =
                "rs".equalsIgnoreCase(artifact) ? "RS-API" :
                        "ads".equalsIgnoreCase(artifact) ? "ADS-IS" :
                                "cxc".equalsIgnoreCase(artifact) ? "CXC-IS" : "nothing";

        if (!name.equals("nothing")) {
            log("running deploy script for " + name);
            ProcessBuilder pb = new ProcessBuilder(Action.baseDir + "/deploy-" + artifact + ".sh");
            Process process = null;
            try {
                process = pb.start();
                output = readOutput(process);
                artifact = artifact+" deployed!";
                log("process output: " + output);

            } catch (Exception e) {
                e.printStackTrace();
                output = e.getMessage();
                artifact = artifact+" not deployed!";
                log("process error!!");

            } finally {
                if (process != null) process.destroy();
                log("process stopped");
            }
        }
        Map<String, String> model = new HashMap<String, String>();
        model.put("$artifact", artifact);
        model.put("$output", output);
        return fill(template(), model);
    }

    @Override
    public boolean canHandle(String path) {
        return path.contains("/deploy");
    }

    @Override
    public String template() {
        return "<body style='font-family:Arial'><h1 style='color:#6495ED'>$artifact</h1><hr><h3>Output:</h3><pre>$output</pre></body>";
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
