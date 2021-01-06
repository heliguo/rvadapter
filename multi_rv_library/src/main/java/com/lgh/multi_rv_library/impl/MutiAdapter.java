package com.lgh.multi_rv_library.impl;


import com.lgh.multi_rv_library.base.RViewAdapter;
import com.lgh.multi_rv_library.model.RViewItem;

import java.util.List;

public class MutiAdapter extends RViewAdapter<UserInfo1, RViewItem<UserInfo1>> {

    public MutiAdapter(List<UserInfo1> datas) {
        super(datas);
        addItemStyle(new AItem());
        addItemStyle(new BItem());
    }
}
