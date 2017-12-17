package com.smart.lajim.util;

import java.util.List;

public class TreeNode {
    private String id;
    private String text;
    private List children;
    private boolean checked = false;

    public TreeNode(){ }

    public TreeNode(String id,String text){
        this.setId(id);
        this.setText(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
