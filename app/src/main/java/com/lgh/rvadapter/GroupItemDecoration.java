package com.lgh.rvadapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.rvadapter.base.RViewAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * 分组浮动的ItemDecoration
 * Created by haibin on 2017/5/15.
 */
@SuppressWarnings("all")
public class GroupItemDecoration extends RecyclerView.ItemDecoration {
    protected int mGroupHeight;
    protected int mGroutBackground;
    protected Paint mBackgroundPaint;
    protected Paint mTextPaint;
    protected float mTextBaseLine;
    protected int mPaddingLeft, mPaddingRight;
    protected boolean isCenter;
    protected boolean isHasHeader;
    protected int mChildItemOffset;
    @SuppressLint("UseSparseArrays")
    private Map<Integer, String> mGroup = new HashMap<>();

    private Bitmap mBitmap;
    private BitmapFactory.Options mOptions;
    private Paint mBitmapPaint;

    private Region mRegion;

    public GroupItemDecoration() {
        super();
        init();
    }

    private void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xFFf5f7f8);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xFF353535);
        mTextPaint.setAntiAlias(true);

        mOptions = new BitmapFactory.Options();
        mOptions.inSampleSize = 3;

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setFilterBitmap(true);

        mRegion = new Region();

    }

    /**
     * 先于RecyclerView的Item onDraw调用
     *
     * @param c      RecyclerView canvas
     * @param parent RecyclerView
     * @param state  stare
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (mBitmap == null)
            mBitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.date, mOptions);
        super.onDraw(c, parent, state);
        onDrawGroup(c, parent);
    }

    private int currentCount;

    /**
     * 绘制分组Group
     *
     * @param c      Canvas
     * @param parent RecyclerView
     */
    protected void onDrawGroup(Canvas c, RecyclerView parent) {
        parent.addOnItemTouchListener(onItemTouchListener);
        int paddingLeft = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top, bottom;
        int count = parent.getChildCount();

        Log.e("-------------", "onDrawGroup: " + count);
        RViewAdapter adapter = (RViewAdapter) parent.getAdapter();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            String groupTitle = mGroup.get(adapter.getItemViewType(position));
            if (mGroup.containsKey(adapter.getItemViewType(position)) &&
                    !TextUtils.isEmpty(groupTitle) && adapter.isFirstViewOfType(position)) {
                top = child.getTop() - params.topMargin - mGroupHeight;
                bottom = top + mGroupHeight;
                c.drawRect(paddingLeft, top, right, bottom, mBackgroundPaint);
                float x;
                float y = top + mTextBaseLine;
                if (isCenter) {
                    x = parent.getMeasuredWidth() / 2 - getTextX(groupTitle);
                } else {
                    x = mPaddingLeft;
                }
                c.drawText(groupTitle, x, y, mTextPaint);
            }
        }
        currentCount = count;
    }

    /**
     * 后于RecyclerView的Item onDraw调用
     *
     * @param c      RecyclerView canvas
     * @param parent RecyclerView
     * @param state  stare
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        onDrawOverGroup(c, parent);
    }

    /**
     * 绘制悬浮组
     *
     * @param c      Canvas
     * @param parent RecyclerView
     */
    protected void onDrawOverGroup(Canvas c, RecyclerView parent) {
        int firstVisiblePosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisiblePosition == RecyclerView.NO_POSITION) {
            return;
        }
        RViewAdapter adapter = (RViewAdapter) parent.getAdapter();
        if (adapter == null)
            return;
        String groupTitle = mGroup.get(adapter.getItemViewType(firstVisiblePosition));

        if (TextUtils.isEmpty(groupTitle)) {
            return;
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + mGroupHeight;
        int offsetY = 0;
        boolean isRestore = false;
        if (firstVisiblePosition + 1 == adapter.getItemCount()) {
            return;
        }
        String nextGroup = mGroup.get(adapter.getItemViewType(firstVisiblePosition + 1));
        if (nextGroup != null && !groupTitle.equals(nextGroup)) {
            //说明是当前组最后一个元素，但不一定碰撞了
            View child = parent.findViewHolderForAdapterPosition(firstVisiblePosition).itemView;
            if (child.getTop() + child.getMeasuredHeight() < mGroupHeight) {
                //进一步检测碰撞
                c.save();//保存画布当前的状态
                isRestore = true;
                offsetY = child.getTop() + child.getMeasuredHeight() - mGroupHeight;
                Log.e("++++++++++", "onDrawOverGroup: " + offsetY);
                c.translate(0, offsetY);
            }
        }

        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        float x;
        float y = top + mTextBaseLine;
        if (isCenter) {
            x = parent.getMeasuredWidth() / 2 - getTextX(groupTitle);
        } else {
            x = mPaddingLeft;
        }
        c.drawText(groupTitle, x, y, mTextPaint);
        int bitmapTop = top + (mGroupHeight - mBitmap.getHeight()) / 2;
        int bitmapLeft = right - mBitmap.getWidth() - 40;
        c.drawBitmap(mBitmap, right - mBitmap.getWidth() - 40, bitmapTop - offsetY, mBitmapPaint);

        mRegion.setEmpty();
        mRegion.set(bitmapLeft, bitmapTop - offsetY, right - 40, bitmapTop + mBitmap.getHeight() - offsetY);

        if (isRestore) {
            //还原画布为初始状态
            c.restore();
        }


    }

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            if (mRegion.contains(x, y)) {
                Log.e("*************", "onTouchEvent: " + x + "  y = " + y);
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    /**
     * 设置item的上下左右偏移量
     *
     * @param outRect rect
     * @param view    item
     * @param parent  RecyclerView
     * @param state   stare
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        getItemOffsets(outRect, view, parent, parent.getChildViewHolder(view).getAdapterPosition());
    }

    /**
     * 设置item的上下左右偏移量，不做任何处理就是默认状态
     *
     * @param outRect         outRect
     * @param view            view
     * @param parent          RecyclerView
     * @param adapterPosition position
     */
    protected void getItemOffsets(Rect outRect, View view, RecyclerView parent, int adapterPosition) {

        RViewAdapter adapter = (RViewAdapter) parent.getAdapter();
        if (mGroup.containsKey(adapter.getItemViewType(adapterPosition))) {
            outRect.set(0, mGroupHeight, 0, mGroup.containsKey(adapter.getItemViewType(adapterPosition)) ? 0 : mChildItemOffset);
        } else {
            outRect.set(0, 0, 0, mGroup.containsKey(adapter.getItemViewType(adapterPosition)) ? 0 : mChildItemOffset);
        }
    }

    public void setChildItemOffset(int childItemOffset) {
        this.mChildItemOffset = childItemOffset;
    }

    public void setBackground(int groupBackground) {
        mBackgroundPaint.setColor(groupBackground);
    }

    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
    }

    public void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextBaseLine = mGroupHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2;
    }

    public void setGroupHeight(int groupHeight) {
        mGroupHeight = groupHeight;
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextBaseLine = mGroupHeight / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2;
    }

    public void setPadding(int mPaddingLeft, int mPaddingRight) {
        this.mPaddingLeft = mPaddingLeft;
        this.mPaddingRight = mPaddingRight;
    }

    public void setCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }

    public void setHasHeader(boolean hasHeader) {
        isHasHeader = hasHeader;
    }

    /**
     * 获取文本的x坐标起点
     *
     * @param str 文本
     * @return x
     */
    protected float getTextX(String str) {
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.width() / 2;
    }

    /**
     * 获取文本的长度像素
     *
     * @param str 文本
     * @return px
     */
    protected float getTextLenghtPx(String str) {
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(str, 0, str.length(), bounds);
        return bounds.width();
    }

    public void setGroup(Map<Integer, String> group) {
        mGroup = group;
    }
}
