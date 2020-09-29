package com.lgh.rvadapter.impl;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgh.rvadapter.ItemType;
import com.lgh.rvadapter.R;
import com.lgh.rvadapter.holder.RViewHolder;
import com.lgh.rvadapter.model.RViewItem;

public class AItem implements RViewItem<ItemType> {
    @Override
    public int getItemLayout() {
        return R.layout.item_list;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(ItemType entity, int position) {
        return entity.getType() == 1;
    }

    @Override
    public void convert(RViewHolder holder, ItemType entity, int position) {
        UserInfo1 userInfo1 ;
        if (entity instanceof UserInfo1){
            userInfo1 = ((UserInfo1) entity);
            TextView account = holder.getView(R.id.single_account);
            account.setText(userInfo1.getAccount());
            TextView password = holder.getView(R.id.single_password);
            password.setText(userInfo1.getPassword());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 200;
            account.setLayoutParams(params);
        }

    }

}

