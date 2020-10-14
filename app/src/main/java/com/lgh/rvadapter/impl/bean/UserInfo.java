package com.lgh.rvadapter.impl.bean;

import com.lgh.rvadapter.base.ItemType;

public class UserInfo implements ItemType {

    private int type;

    private String account;
    private String password;

    public UserInfo() {
    }

    public UserInfo(String account, String password) {
        this.account = account;
        this.password = password;
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

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean grouping() {
        return false;
    }
}
