package com.capitalone.p2p.action;

import java.util.Map;

/**
 * Created by xwg532 on 11/9/14.
 */
public abstract class BaseAction implements Action {
    public String fill(String template, Map<String, String> model) {
        for (String key : model.keySet())
            template = template.replace(key,model.get(key));
        return template;
    }
}
