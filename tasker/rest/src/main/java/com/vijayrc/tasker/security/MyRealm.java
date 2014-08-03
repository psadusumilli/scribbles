package com.vijayrc.tasker.security;

import com.vijayrc.tasker.domain.User;
import com.vijayrc.tasker.repository.AllUsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {
    private static Logger log = LogManager.getLogger(MyMatcher.class);
    private AllUsers allUsers;

    @Autowired
    public MyRealm(MyMatcher matcher, MyResolver resolver, AllUsers allUsers) {
        this.allUsers = allUsers;
        this.setCredentialsMatcher(matcher);
        this.setRolePermissionResolver(resolver);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        check(principals, "principal-collection method argument cannot be null.");
        String username = (String) principals.getPrimaryPrincipal();
        log.info("called "+username);
        User user = allUsers.get(username);
        return new SimpleAuthorizationInfo(user.roles());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        log.info("user entry|" + username);
        check(username, "blank user name");
        User user = allUsers.get(username);
        check(user, "no account found for user [" + username + "]");
        return new SimpleAuthenticationInfo(username, user.password(), getName());
    }

    private void check(Object reference, String
            message) {
        if (reference == null)
            throw new AuthenticationException(message);
    }
}
