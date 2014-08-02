package com.vijayrc.tasker.domain;


import com.vijayrc.meta.ToString;

import java.util.Set;
import java.util.TreeSet;

@ToString
public class User {
    private String name;
    private String password;
    private Set<Role> roles = new TreeSet<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User withRole(Role role){
        roles.add(role);
        return this;
    }
    public boolean hasRole(String roleName){
        return roles.contains(new Role(roleName));
    }

}
