package com.lgh.rvadapter.impl;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lgh.rvadapter.R;
import com.lgh.rvadapter.RViewHelper;
import com.lgh.rvadapter.SwipeRefreshHelper;
import com.lgh.rvadapter.base.ItemType;
import com.lgh.rvadapter.core.RViewCreate;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * author:lgh on 2019-11-14 11:32
 */
public abstract class BaseAdapterActivity extends AppCompatActivity implements
        RViewCreate<ItemType>, SwipeRefreshHelper.SwipeRefreshListener {

    protected RViewHelper<ItemType> helper;


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
        RecyclerView rv = findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this ));
        return rv;
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    public void notifyAdapterDataSetChanged(List<ItemType> datas) {
        helper.notifyAdapterDataSetChanged(datas);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh(View view) {
        Log.e("TAG", "refresh: " );
        helper.setOrientation(RecyclerView.HORIZONTAL);
    }
}
