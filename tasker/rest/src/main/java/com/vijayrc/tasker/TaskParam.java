package com.vijayrc.tasker;

import com.vijayrc.meta.ToString;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@ToString
public class TaskParam {
    @PathParam("field")
    private String field;

    @HeaderParam("User-Agent")
    private String agent;

    private String format;

    public TaskParam(@DefaultValue("xml") @QueryParam("format") String format) {
        this.format = format;
    }
    public String field(){ return field; }
    public String agent(){ return agent; }
    public String format(){ return format; }
}
