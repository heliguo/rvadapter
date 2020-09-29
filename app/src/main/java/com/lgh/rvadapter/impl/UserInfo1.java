package com.lgh.rvadapter.impl;

import com.lgh.rvadapter.ItemType;

public class UserInfo1 implements ItemType {

    private String account;
    private String password;
    private int type;
    private boolean grouping;

    public UserInfo1() {
    }

    public UserInfo1(String account, String password, int type) {
        this.account = account;
        this.password = password;
        this.type = type;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean grouping() {
        return false;
    }

    public int getType() {
        return type;
    }

    public boolean isGrouping() {
        return grouping;
    }

    public void setGrouping(boolean grouping) {
        this.grouping = grouping;
    }
}
