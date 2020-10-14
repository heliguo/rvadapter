package com.lgh.rvadapter.impl.item;

import android.widget.TextView;

import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.R;
import com.lgh.rvadapter.holder.RViewHolder;
import com.lgh.rvadapter.impl.bean.UserInfo;
import com.lgh.rvadapter.model.RViewItem;

public class BItem implements RViewItem<ItemType> {
    @Override
    public int getItemLayout() {
        return R.layout.item_list1;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(ItemType entity, int position) {
        return entity.getType() == 2;
    }

    @Override
    public void convert(RViewHolder holder, ItemType entity, int position) {
        if (entity instanceof UserInfo) {
            UserInfo entity1 = (UserInfo) entity;
            TextView account = holder.getView(R.id.single_account1);
            account.setText(entity1.getAccount());
            TextView password = holder.getView(R.id.single_password1);
            password.setText(entity1.getPassword());
        }
    }
}
