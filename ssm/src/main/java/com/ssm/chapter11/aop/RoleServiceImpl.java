package com.ssm.chapter11.aop;

import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public void printRole(Role role) {
        System.out.println("{id = " + role.getID() + ",roleName = "+ role.getRoleName() + ",note = "+ role.getNote() + "}");
    }

    @Override
    public void Hello() {
        System.out.println("Helloooooooooooooooooooooo");
    }
}
