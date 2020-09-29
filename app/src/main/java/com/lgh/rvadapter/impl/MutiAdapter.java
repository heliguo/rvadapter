package com.lgh.rvadapter.impl;

import com.lgh.rvadapter.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;

import java.util.List;

public class MutiAdapter extends RViewAdapter<ItemType> {

    public MutiAdapter(List<ItemType> datas) {
        super(datas);
        addItemStyle(new AItem());
        addItemStyle(new BItem());
    }
}
