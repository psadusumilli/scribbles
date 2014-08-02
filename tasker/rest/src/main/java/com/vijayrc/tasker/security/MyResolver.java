package com.vijayrc.tasker.security;

import com.google.common.collect.HashMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MyResolver implements RolePermissionResolver{
    private static Logger log = LogManager.getLogger(MyResolver.class);

    private HashMultimap<String, Permission> permissions;

    public MyResolver() {
        permissions = HashMultimap.create();
        permissions.putAll("admin", make("*"));
        permissions.putAll("viewer", make("tasks:read", "cards:read", "files:read", "search:read"));
        permissions.putAll("creator", make("tasks:create", "cards:create", "files:create", "search:create"));
        permissions.putAll("deleter", make("tasks:delete", "cards:delete", "files:delete", "search:delete"));
        log.info("init|"+permissions.size());
    }

    @Override
    public Collection<Permission> resolvePermissionsInRole(String role) {
        log.info("called for:"+role);
        return permissions.get(role);
    }

    private List<Permission> make(String... tokens){
        List<Permission> list = new ArrayList<>();
        for (String token : tokens)
            list.add(new WildcardPermission(token));
        return list;
    }
}
