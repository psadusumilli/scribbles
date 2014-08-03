package com.vijayrc.tasker.security;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class PasswordMaker {
    public static void main(String[] args) {
        System.out.println(new Sha256Hash("marsh").toString());
        System.out.println(new Sha256Hash("cartman").toString());
        System.out.println(new Sha256Hash("broflovski").toString());
        System.out.println(new Sha256Hash("mccormick").toString());
    }
}
