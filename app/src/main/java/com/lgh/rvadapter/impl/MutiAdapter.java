package com.lgh.rvadapter.impl;

import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;
import com.lgh.rvadapter.impl.item.AItem;
import com.lgh.rvadapter.impl.item.BItem;
import com.lgh.rvadapter.impl.item.RvItem;

import java.util.List;

public class MutiAdapter extends RViewAdapter<ItemType> {

    public MutiAdapter(List<ItemType> datas) {
        super(datas);
        addItemStyle(new AItem());
        addItemStyle(new BItem());
        addItemStyle(new RvItem());
    }
}
