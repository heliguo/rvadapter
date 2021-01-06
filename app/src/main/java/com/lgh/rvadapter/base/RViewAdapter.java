package com.lgh.rvadapter.base;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.rvadapter.holder.RViewHolder;
import com.lgh.rvadapter.listener.ItemListener;
import com.lgh.rvadapter.manager.RViewItemManager;
import com.lgh.rvadapter.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-14 16:16
 */
public class RViewAdapter<T extends ItemType> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemStyle;//条目类型管理
    private ItemListener<T> itemListener;//item点击事件监听
    private List<T> mDatas;//数据源

    //嵌套（多样式布局）,需调用addItemStyle方法添加多布局
    public RViewAdapter(List<T> datas) {
        if (datas == null)
            this.mDatas = new ArrayList<>();
        this.mDatas = datas;
        itemStyle = new RViewItemManager<>();
    }

    //单一布局
    public RViewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null)
            this.mDatas = new ArrayList<>();
        this.mDatas = datas;
        itemStyle = new RViewItemManager<>();
        addItemStyle(item);
    }

    public RViewAdapter addItemStyle(RViewItem<T> item) {
        itemStyle.addStyle(item);
        return this;
    }

    //是否有多样式布局
    private boolean hasMutiStyle() {
        return itemStyle.getItemViewStylesCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        //多样式布局
        if (hasMutiStyle())
            return itemStyle.getItemViewType(mDatas.get(position), position);
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RViewItem<T> rViewItem = itemStyle.getRViewItem(viewType);
        RViewHolder holder = RViewHolder.createViewHolder(parent.getContext(), parent, rViewItem.getItemLayout());
        if (rViewItem.openClick()) {
            setListener(holder);
        }
        Log.e("onCreateViewHolder", "item: " + rViewItem.getClass().getSimpleName() + "  type:  " + viewType);
        return holder;
    }

    /**
     * 可通过view.findViewById(R.id.*).setOnClickListener来完成对子view的点击监听
     *
     * @param holder holder
     */
    private void setListener(final RViewHolder holder) {
        Log.e("===", "setListener: " + holder.getAdapterPosition());
        holder.getConvertView().setOnClickListener(v -> {
            if (itemListener != null) {
                int position = holder.getAdapterPosition();//当前整个条目类型可点击
                itemListener.onItemClick(v, mDatas.get(position), position);
            }
        });

        holder.getConvertView().setOnLongClickListener(v -> {
            if (itemListener != null) {
                int position = holder.getAdapterPosition();//当前整个条目类型可长按
                itemListener.onItemLongClick(v, mDatas.get(position), position);
            }
            return true;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    private void convert(RViewHolder holder, T entity) {
        itemStyle.convert(holder, entity, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
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
        if (datas == null)
            return;
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas data
     */
    public void addDatas(List<T> datas) {
        if (datas == null)
            return;
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }



    private int currentItemType = -1;

    public boolean isFirstViewOfType(int position) {
        boolean isFirstView ;
        isFirstView = currentItemType != getItemViewType(position);
        currentItemType = getItemViewType(position);
        return isFirstView;
    }

}
