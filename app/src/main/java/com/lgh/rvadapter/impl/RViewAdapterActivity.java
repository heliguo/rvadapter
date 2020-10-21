package com.lgh.rvadapter.impl;

import android.os.Bundle;
import android.widget.Toast;

import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;
import com.lgh.rvadapter.impl.bean.RvInfo;
import com.lgh.rvadapter.impl.bean.UserInfo;
import com.lgh.rvadapter.impl.bean.UserInfo1;

import java.util.ArrayList;
import java.util.List;

public class RViewAdapterActivity extends BaseAdapterActivity {

    private List<ItemType> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    protected void initDatas() {
        if (datas.isEmpty()) {
            List<RvInfo.Bean> beans = new ArrayList<>();
            beans.add(new RvInfo.Bean("content1"));
            beans.add(new RvInfo.Bean("content2"));
            beans.add(new RvInfo.Bean("content3"));
            beans.add(new RvInfo.Bean("content4"));
            beans.add(new RvInfo.Bean("content5"));
            beans.add(new RvInfo.Bean("content6"));
            datas.add(new RvInfo(3, true, beans));

            for (int i = 0; i < 100; i++) {

                if (i < 50) {
                    if (i == 10) {
                        datas.add(new RvInfo(3, true, beans));
                    }
                    UserInfo1 userInfo = new UserInfo1();
                    userInfo.setType(1);
                    userInfo.setAccount("UserInfo1_" + i);
                    userInfo.setPassword(i + "");
                    datas.add(userInfo);
                } else {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setType(2);
                    userInfo.setAccount("UserInfo_" + i);
                    userInfo.setPassword(i + "_");
                    datas.add(userInfo);
                }

            }
            datas.add(new RvInfo(3, true, beans));
        }
    }

    @Override
    public void OnRefresh() {
        initDatas();
        datas.add(new UserInfo1("hhh", "345", 1));
        notifyAdapterDataSetChanged(datas);
    }

    @Override
    public RViewAdapter<ItemType> createRViewAdapter() {
        //        //单一布局
        //        RViewItem<UserInfo> item1 = new RViewItem<UserInfo>() {
        //            @Override
        //            public int getItemLayout() {
        //                return R.layout.item_list;
        //            }
        //
        //            @Override
        //            public boolean openClick() {
        //                return true;
        //            }
        //
        //            @Override
        //            public boolean isItemView(UserInfo entity, int position) {
        //                return true;
        //            }
        //
        //            @Override
        //            public void convert(RViewHolder holder, UserInfo entity, int position) {
        //                TextView account = holder.getView(R.id.single_account);
        //                account.setText(entity.getAccount());
        //                TextView password = holder.getView(R.id.single_password);
        //                password.setText(entity.getPassword());
        //            }
        //        };
        //        RViewAdapter adapter = new RViewAdapter<UserInfo>(datas, item1);
        MutilAdapter adapter = new MutilAdapter(datas);
        adapter.setItemListener((view, entity, position) -> Toast.makeText(RViewAdapterActivity.this, "type: " + entity.getType() +
                " position: " + (position + 1), Toast.LENGTH_SHORT).show());
        return adapter;
    }
}
