package com.ssm.aoptest.aop;

public class Role {
    private int ID;
    private String roleName;
    private String note;

    public Role(){}

    public Role(int ID,String roleName,String note){
        this.ID = ID;
        this.roleName = roleName;
        this.note = note;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
