package com.vijayrc.agent.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.Arrays.asList;

public class AllActions {

    private List<BaseAction> actions = asList(
            new ShellAction(),
            new DownloadAction(),
            new UploadFormAction(),
            new UploadAction(),
            new ProxyAction());

    public String execute(HttpServletRequest request, HttpServletResponse response){
        for (Action action : actions)
            if(action.canHandle(request.getPathInfo()))
                try {
                    return action.execute(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return "no action";
    }

}
