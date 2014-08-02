package com.vijayrc.tasker.security;

import com.google.common.collect.HashMultimap;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Safe {
    static Map<String, String> passwords = new HashMap<>();
    static HashMultimap<String, String> roles = HashMultimap.create();
    static HashMultimap<String, String> permissions = HashMultimap.create();

    static{
        passwords.put("paul1", encrypt("green"));
        passwords.put("paul2", encrypt("blue"));

        roles.put("paul1", "creator");
        roles.put("paul1", "viewer");
        roles.put("paul1", "deleter");

        permissions.put("paul", "safe:*");
    }

    public static String encrypt(String password) {
        return new Sha256Hash(password).toString();
    }
    public static String getPassword(String username) {
        return passwords.get(username);
    }
    public static Set<String> getRoles(String username) {
        return roles.get(username);
    }
    public static Set<String> getPermissions(String username) {
        return null;
    }
}
