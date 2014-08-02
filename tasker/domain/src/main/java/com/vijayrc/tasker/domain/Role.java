package com.vijayrc.tasker.domain;

import com.vijayrc.meta.ToString;

@ToString
public class Role {
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return !(roleName != null ? !roleName.equals(role.roleName) : role.roleName != null);
    }
    @Override
    public int hashCode() {
        return roleName != null ? roleName.hashCode() : 0;
    }
}
