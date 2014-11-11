package com.capitalone.p2p.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by xwg532 on 11/9/14.
 */
public class AllActions {

    private List<BaseAction> actions = asList(
            new DeployAction(),
            new LogAction(),
            new UploadFormAction(),
            new UploadAction());

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
