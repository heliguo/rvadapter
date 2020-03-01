package com.lgh.rvadapter.base;

import android.view.View;
import android.view.ViewGroup;

import com.lgh.rvadapter.holder.RViewHolder;
import com.lgh.rvadapter.listener.ItemListener;
import com.lgh.rvadapter.manager.RViewItemManager;
import com.lgh.rvadapter.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author:lgh on 2019-11-14 16:16
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemStyle;//条目类型管理
    private ItemListener<T> itemListener;//item点击事件监听
    private List<T> datas;//数据源

    //单一布局
    public RViewAdapter(List<T> datas) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
    }

    //嵌套（多样式布局）
    public RViewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
        addItemStyle(item);
    }

    public void addItemStyle(RViewItem<T> item) {
        itemStyle.addStyle(item);
    }

    //是否有多样式布局
    private boolean hasMutiStyle() {
        return itemStyle.getItemViewStylesCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        //多样式布局
        if (hasMutiStyle()) return itemStyle.getItemViewType(datas.get(position), position);
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RViewItem rViewItem = itemStyle.getRViewItem(viewType);
        RViewHolder holder = RViewHolder.createViewHolder(parent.getContext(), parent, rViewItem.getItemLayout());
        if (rViewItem.openClick()) setListener(holder);
        return holder;
    }

    private void setListener(final RViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    int position = holder.getAdapterPosition();//当前整个条目类型可点击
                    itemListener.onItemClick(v, datas.get(position), position);
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, datas.get(position));
    }

    private void convert(RViewHolder holder, T entity) {
        itemStyle.convert(holder, entity, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    /**
     * 监听事件
     *
     * @param itemListener listener
     */
    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }

    /**
     * 更新所有数据
     *
     * @param datas data
     */
    public void updateDatas(List<T> datas) {
        if (datas == null) return;
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas data
     */
    public void addDatas(List<T> datas) {
        if (datas == null) return;
        datas.addAll(datas);
        notifyDataSetChanged();
    }

}
