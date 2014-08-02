package com.vijayrc.tasker.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Component;

@Component
public class MyMatcher extends SimpleCredentialsMatcher {
    private static Logger log = LogManager.getLogger(MyMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
        String encryptedToken = new Sha256Hash(extract(token.getCredentials())).toString();
        boolean matched = extract(info.getCredentials()).equals(encryptedToken);
        log.info("user credentials matched ? "+matched);
        return matched;
    }

    private String extract(Object credentials) {
        return new String((char[]) credentials);
    }
}
