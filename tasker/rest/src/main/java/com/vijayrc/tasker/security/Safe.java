package com.vijayrc.tasker.security;

import com.google.common.collect.HashMultimap;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Safe {
    static Map<String, String> passwords = new HashMap<>();
    static HashMultimap<String, String> roles = HashMultimap.create();

    static{
        passwords.put("pierre", encrypt("green"));
        passwords.put("paul", encrypt("blue"));
        roles.put("paul", "vip");
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
}
