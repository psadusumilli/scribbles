package com.vijayrc.tasker.domain;


import com.vijayrc.meta.ToString;

import java.util.Set;
import java.util.TreeSet;

import static java.util.Collections.addAll;
import static org.apache.commons.lang.StringUtils.split;

@ToString
public class User {
    private String name;
    private String password;
    private Set<String> roles = new TreeSet<>();

    public User(String name, String password, String rolesStr) {
        this.name = name;
        this.password = password;
        addAll(roles, split(rolesStr, ","));
    }
    public boolean hasRole(String role){
        return roles.contains(role);
    }
    public Set<String> roles() {
        return roles;
    }
    public char[] password(){
        return password.toCharArray();
    }
    public String name() {
        return name;
    }
}
