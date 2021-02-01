package com.lgh.rvadapter.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lgh.rvadapter.GroupItemDecoration;
import com.lgh.rvadapter.R;
import com.lgh.rvadapter.RViewHelper;
import com.lgh.rvadapter.SwipeRefreshHelper;
import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.core.RViewCreate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author:lgh on 2019-11-14 11:32
 */
public abstract class BaseAdapterActivity extends AppCompatActivity implements
        RViewCreate<ItemType>, SwipeRefreshHelper.SwipeRefreshListener {

    protected RViewHelper<ItemType> helper;
    private RecyclerView mRv;
    private LinearLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_adpter);
        helper = new RViewHelper.Builder<>(this, this).build();
    }


    @Override
    public Context context() {
        return this;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return findViewById(R.id.swiperefresh);
    }

    @Override
    public RecyclerView createRecyclerView() {
        mRv = findViewById(R.id.recyclerview);
        mItemDecoration.setTextSize(16);
        mItemDecoration.setBackground(Color.parseColor("#FFC0CB"));
        mItemDecoration.setTextColor(Color.BLACK);
        mItemDecoration.setGroupHeight(80);
        mItemDecoration.setPadding(16, 16);
        mItemDecoration.setCenter(true);
        mItemDecoration.setChildItemOffset(50);
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "每日一笑");
        map.put(1, "今日推荐");
        map.put(2, "每周热点");
        map.put(3, "最高评论");
        mItemDecoration.setGroup(map);
        mRv.addItemDecoration(mItemDecoration);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        return mRv;
    }

    GroupItemDecoration mItemDecoration = new GroupItemDecoration();

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    public void notifyAdapterDataSetChanged(List<ItemType> datas) {
        helper.notifyAdapterDataSetChanged(datas);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh(View view) {
        Log.e("TAG", "refresh: ");
        //        helper.setOrientation(RecyclerView.HORIZONTAL);
        RecyclerView.LayoutManager layoutManager = mRv.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            mManager = (LinearLayoutManager) layoutManager;
            LinearSmoothScroller s1 = new TopSmoothScroller(BaseAdapterActivity.this);
            s1.setTargetPosition(20);//平滑定位
            mManager.startSmoothScroll(s1);
        }

    }

    public void flash(View view) {
        if (mManager != null) {
            mManager.scrollToPositionWithOffset(15, 0);//瞬间定位
        }else {
            mRv.smoothScrollToPosition(20);
        }
    }

    public static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;//具体见源码注释
        }
    }
}
