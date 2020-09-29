package com.lgh.rvadapter.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lgh.rvadapter.ItemType;
import com.lgh.rvadapter.base.RViewAdapter;
import com.lgh.rvadapter.listener.ItemListener;

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
            for (int i = 0; i < 100; i++) {

                if (i < 50) {
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
        }
    }

    @Override
    public void OnRefresh() {
        initDatas();
        datas.add(new UserInfo1("hhh", "345", 1));
        notifyAdapterDataSetChanged(datas);
    }

    @Override
    public RViewAdapter createRViewAdapter() {
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
        MutiAdapter adapter = new MutiAdapter(datas);
        adapter.setItemListener(new ItemListener<ItemType>() {
            @Override
            public void onItemClick(View view, ItemType entity, int position) {
                Log.e("123455666777", "onItemClick: " + position);
            }

            @Override
            public boolean onItemLongClick(View view, ItemType entity, int position) {
                return false;
            }
        });
        return adapter;
    }
}
