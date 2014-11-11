package com.vijayrc.agent.action;

import java.util.Map;

public abstract class BaseAction implements Action {
    public String fill(String template, Map<String, String> model) {
        for (String key : model.keySet())
            template = template.replace(key,model.get(key));
        return template;
    }
}
