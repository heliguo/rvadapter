package com.lgh.rvadapter.impl;

import com.lgh.rvadapter.base.RViewAdapter;

import java.util.List;

public class MutiAdapter extends RViewAdapter<UserInfo1> {

    public MutiAdapter(List<UserInfo1> datas) {
        super(datas);
        addItemStyle(new AItem());
        addItemStyle(new BItem());
    }
}
