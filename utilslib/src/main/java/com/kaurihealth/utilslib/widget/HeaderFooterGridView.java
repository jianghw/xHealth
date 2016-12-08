package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.MeasureSpec.getSize;

/**
 * Created by jianghw on 2016/11/24.
 * <p/>
 * Describe:
 */

public class HeaderFooterGridView extends GridView {

    private int mRequestedNumColumns;//AUTO_FIT模式
    private int mRequestedColumnWidth;
    private int mNumColumns;//列数
    private int mRequestedHorizontalSpace;//
    private int mHorizontalSpace;
    private int mVerticalSpace;
    private int mColumns;
    private int mColumnWidth;
    /**
     * 头尾item布局
     */
    private final List<View> mHeaderItems = new ArrayList<>();
    private final List<View> mFooterItems = new ArrayList<>();
    /**
     * 头尾布局
     */
    private final List<View> mHeaderViews = new ArrayList<>();
    private final List<View> mFooterViews = new ArrayList<>();
    /**
     * 头尾布局 描述
     */
    private final ArrayList<GridViewFixedViewInfo> mHeaderItemInfo = new ArrayList<>();
    private final ArrayList<GridViewFixedViewInfo> mFooterItemInfo = new ArrayList<>();
    /**
     *
     */
    private final LayoutParams abLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
    /**
     * mAdapter
     */
    private HeaderFooterViewListAdapter mAdapter;
    /**
     * HeaderFooterListener
     */
    private HeaderFooterListener mHeaderFooterListener;

    public HeaderFooterGridView(Context context) {
        this(context, null);
    }

    public HeaderFooterGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderFooterGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHeaderFooterListener = new HeaderFooterListener();
        super.setOnItemClickListener(mHeaderFooterListener);
        super.setOnItemLongClickListener(mHeaderFooterListener);
        super.setOnItemSelectedListener(mHeaderFooterListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int availableSpace = getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
// AUTO_FIT模式，需要以行间距及列宽来计算。
        if (mRequestedNumColumns == AUTO_FIT) {
            if (mRequestedColumnWidth == 0) {
                mNumColumns = 2;
            } else {
                mNumColumns = (availableSpace + mRequestedHorizontalSpace) / (mRequestedColumnWidth + mRequestedHorizontalSpace);
            }
        } else {
            mNumColumns = mRequestedNumColumns;
        }
//延伸模式
        int mStretchMode = getStretchMode();
//列间距不变，列宽不变
        if (mStretchMode == NO_STRETCH) {
            mHorizontalSpace = mRequestedHorizontalSpace;
            mColumns = mRequestedNumColumns;
        } else {
            int spaceLeftOver = availableSpace - (mNumColumns * mRequestedColumnWidth) - ((mNumColumns - 1) * mRequestedHorizontalSpace);
            switch (mStretchMode) {
//列间距不变，列宽改变
                case STRETCH_COLUMN_WIDTH:
                    mHorizontalSpace = mRequestedHorizontalSpace;
                    mColumnWidth = mRequestedColumnWidth + spaceLeftOver / mNumColumns;
                    break;
//列间距改变，列宽不变
                case STRETCH_SPACING:
                    mColumnWidth = mRequestedColumnWidth;
                    if (mNumColumns > 1) {
                        mHorizontalSpace = mRequestedHorizontalSpace + spaceLeftOver / (mNumColumns - 1);
                    } else {
                        mHorizontalSpace = mRequestedHorizontalSpace + spaceLeftOver;
                    }
                    break;
//列间距改变，列宽不变
                case STRETCH_SPACING_UNIFORM:
                    mColumnWidth = mRequestedColumnWidth;
                    if (mNumColumns > 1) {
                        mHorizontalSpace = mRequestedHorizontalSpace + spaceLeftOver / (mNumColumns + 1);
                    } else {
                        mHorizontalSpace = mRequestedHorizontalSpace + spaceLeftOver;
                    }
                    break;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = getSize(heightMeasureSpec);
//        LogUtils.e(String.valueOf(heightSize));
//        int childMeasureSize = 0;
//        if (heightMode == MeasureSpec.AT_MOST) {
//            for (View footer : mFooterViews) {
//                    childMeasureSize+= footer.getHeight();
//            }
//            LogUtils.e(String.valueOf(childMeasureSize));
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.AT_MOST);
//            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//        }
//完成计算，为达到高精确度，进行一下高版本数据确认：
        mNumColumns = getNumColumnsCompat();
        mVerticalSpace = getVerticalSpacingCompat();
        mHorizontalSpace = getHorizontalSpacingCompat();
        mColumnWidth = getColumnWidthCompat();
        mRequestedHorizontalSpace = getRequestedHorizontalSpacingCompat();
        mRequestedColumnWidth = getRequestedColumnWidthCompat();
        if (mAdapter != null && mAdapter.getNumColumns() != mNumColumns)
            mAdapter.setNumColumns(mNumColumns);
        abLayoutParams.width = getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 进行头尾View的长宽控制，View创建及Adapter更改才会调用
     */
    @Override
    protected void layoutChildren() {
        for (View header : mHeaderViews) {
            ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = getWidth() - getPaddingLeft() - getPaddingRight();
                header.setLayoutParams(layoutParams);
            } else {
                header.setLayoutParams(abLayoutParams);
            }
        }

        for (View footer : mFooterViews) {
            ViewGroup.LayoutParams layoutParams = footer.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = getWidth() - getPaddingLeft() - getPaddingRight();
                footer.setLayoutParams(layoutParams);
            } else {
                footer.setLayoutParams(abLayoutParams);
            }
        }
        super.layoutChildren();
    }

    /**
     * 设置头尾View的偏移，只重新绘制将要显示的
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mAdapter != null) {
            int startHeaderIndex, endHeaderIndex, startFooterIndex, endFooterIndex;
//头尾View均有显示
            if (mAdapter.getItemCount() == 0) {
                if (mHeaderViews.size() > 0) {
                    startHeaderIndex = mAdapter.positionToHeaderViewIndex(getFirstVisiblePosition());
                    endHeaderIndex = mAdapter.positionToHeaderViewIndex(getLastVisiblePosition());
                    endHeaderIndex = endHeaderIndex == -1 ? mHeaderViews.size() - 1 : endHeaderIndex;
                    translateView(startHeaderIndex, endHeaderIndex, mHeaderViews);
                }
                if (mFooterViews.size() > 0) {
                    startFooterIndex = mAdapter.positionToFooterViewIndex(getFirstVisiblePosition());
                    startFooterIndex = startFooterIndex == -1 ? 0 : startFooterIndex;
                    endFooterIndex = mAdapter.positionToFooterViewIndex(getLastVisiblePosition());
                    translateView(startFooterIndex, endFooterIndex, mFooterViews);
                }
//头尾View均有显示
            } else if (getFirstVisiblePosition() < mAdapter.getFirstItemPosition() && getLastVisiblePosition() > mAdapter.getLastItemPosition()) {
                if (mHeaderViews.size() > 0) {
                    startHeaderIndex = mAdapter.positionToHeaderViewIndex(getFirstVisiblePosition());
                    endHeaderIndex = mAdapter.positionToHeaderViewIndex(getLastVisiblePosition());
                    endHeaderIndex = endHeaderIndex == -1 ? mHeaderViews.size() - 1 : endHeaderIndex;
                    translateView(startHeaderIndex, endHeaderIndex, mHeaderViews);
                }
                if (mFooterViews.size() > 0) {
                    startFooterIndex = mAdapter.positionToFooterViewIndex(getFirstVisiblePosition());
                    startFooterIndex = startFooterIndex == -1 ? 0 : startFooterIndex;
                    endFooterIndex = mAdapter.positionToFooterViewIndex(getLastVisiblePosition());
                    translateView(startFooterIndex, endFooterIndex, mFooterViews);
                }
//头View有显示
            } else if (getFirstVisiblePosition() < mAdapter.getFirstItemPosition()) {
                if (mHeaderViews.size() > 0) {
                    startHeaderIndex = mAdapter.positionToHeaderViewIndex(getFirstVisiblePosition());
                    endHeaderIndex = mAdapter.positionToHeaderViewIndex(getLastVisiblePosition());
                    endHeaderIndex = endHeaderIndex == -1 ? mHeaderViews.size() - 1 : endHeaderIndex;
                    translateView(startHeaderIndex, endHeaderIndex, mHeaderViews);
                }
//尾View有显示
            } else if (getLastVisiblePosition() > mAdapter.getLastItemPosition()) {
                if (mFooterViews.size() > 0) {
                    startFooterIndex = mAdapter.positionToFooterViewIndex(getFirstVisiblePosition());
                    startFooterIndex = startFooterIndex == -1 ? 0 : startFooterIndex;
                    endFooterIndex = mAdapter.positionToFooterViewIndex(getLastVisiblePosition());
                    translateView(startFooterIndex, endFooterIndex, mFooterViews);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    private void translateView(int startIndex, int endIndex, List<View> listViews) {
        for (int i = startIndex; i <= endIndex && i < listViews.size() && i >= 0; i++) {
            View header = listViews.get(i);
            header.layout(getPaddingLeft(), header.getTop(), getWidth() - getPaddingRight(), header.getTop() + header.getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mAdapter = new HeaderFooterViewListAdapter(mHeaderItemInfo, mFooterItemInfo, adapter, mHeaderViews, mFooterViews, mNumColumns);
        super.setAdapter(mAdapter);
    }

    @Override
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setVerticalSpacing(int verticalSpacing) {
        mVerticalSpace = verticalSpacing;
        super.setVerticalSpacing(verticalSpacing);
    }

    public int getVerticalSpacingCompat() {
        if (Build.VERSION.SDK_INT >= 16)
            return getVerticalSpacing();
        else
            return mVerticalSpace;
    }

    @Override
    public void setHorizontalSpacing(int horizontalSpacing) {
        mRequestedHorizontalSpace = horizontalSpacing;
        super.setHorizontalSpacing(horizontalSpacing);
    }

    public int getHorizontalSpacingCompat() {
        if (Build.VERSION.SDK_INT >= 16)
            return getHorizontalSpacing();
        else
            return mHorizontalSpace;
    }

    public int getRequestedHorizontalSpacingCompat() {
        if (Build.VERSION.SDK_INT >= 16)
            return getRequestedHorizontalSpacing();
        else
            return mRequestedHorizontalSpace;
    }

    @Override
    public void setColumnWidth(int columnWidth) {
        mRequestedColumnWidth = columnWidth;
        super.setColumnWidth(columnWidth);
    }

    public int getColumnWidthCompat() {
        if (Build.VERSION.SDK_INT >= 16)
            return getColumnWidth();
        else
            return mColumnWidth;
    }

    public int getRequestedColumnWidthCompat() {
        if (Build.VERSION.SDK_INT >= 16)
            return getRequestedColumnWidth();
        else
            return mRequestedColumnWidth;
    }

    @Override
    public void setNumColumns(int numColumns) {
        mRequestedNumColumns = numColumns;
        super.setNumColumns(numColumns);
    }

    public int getNumColumnsCompat() {
        if (Build.VERSION.SDK_INT >= 11)
            return getNumColumns();
        else
            return mNumColumns;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mHeaderFooterListener.setOnItemClickListener(listener, mAdapter);
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mHeaderFooterListener.setOnItemLongClickListener(listener, mAdapter);
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mHeaderFooterListener.setOnItemSelectedListener(listener, mAdapter);
    }

    @Override
    public void setItemChecked(int position, boolean value) {
        super.setItemChecked(position + (mAdapter == null ? 0 : mAdapter.getHeaderViewsCount()), value);
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        return super.performItemClick(view, position + (mAdapter == null ? 0 : mAdapter.getHeaderViewsCount()), id);
    }

    @SuppressWarnings("unused")
    public void addHeaderItem(View v) {
        addHeaderItem(v, null, false);
    }

    public void addHeaderItem(View v, Object data, boolean isSelectable) {
        if (mHeaderItems.contains(v)) return;
        mHeaderItems.add(0, v);
        final GridViewFixedViewInfo info = new GridViewFixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        mHeaderItemInfo.add(0, info);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public int getHeaderItemsCount() {
        return mHeaderItems.size();
    }

    @SuppressWarnings("unused")
    public boolean removeHeaderItem(View v) {
        return mHeaderItems.remove(v) && mAdapter != null && mAdapter.removeHeaderItem(v);
    }

    @SuppressWarnings("unused")
    public void addFooterItem(View v) {
        addFooterItem(v, null, false);
    }


    public void addFooterItem(View v, Object data, boolean isSelectable) {
        if (mFooterItems.contains(v)) return;
        mFooterItems.add(v);
        final GridViewFixedViewInfo info = new GridViewFixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        mFooterItemInfo.add(info);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public int getFooterItemsCount() {
        return mFooterItems.size();
    }

    @SuppressWarnings("unused")
    public boolean removeFooterItem(View v) {
        return mFooterItems.remove(v) && mAdapter != null && mAdapter.removeFooterItem(v);
    }


    @SuppressWarnings("unused")
    public void addHeaderView(View v) {
        if (mHeaderViews.contains(v)) return;
        mHeaderViews.add(0, v);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    @SuppressWarnings("unused")
    public int getHeaderEmptyViewsCount() {
        return mAdapter == null ? 0 : mAdapter.getHeaderViewsCount();
    }

    @SuppressWarnings("unused")
    public boolean removeHeaderView(View v) {
        boolean finished = mHeaderViews.remove(v);
        if (finished && mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        return finished;
    }


    @SuppressWarnings("unused")
    public void addFooterView(View v) {
        if (mFooterViews.contains(v)) return;
        mFooterViews.add(v);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    @SuppressWarnings("unused")
    public int getFooterEmptyViewsCount() {
        return mAdapter == null ? 0 : mAdapter.getFooterViewsCount();
    }

    @SuppressWarnings("unused")
    public boolean removeFooterView(View v) {
        boolean finished = mFooterViews.remove(v);
        if (finished && mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        return finished;
    }

    @SuppressWarnings("unused")
    public int getFirstWrappedAdapterItemPosition() {
        if (mAdapter != null)
            return mAdapter.getFirstWrappedAdapterItemPosition();
        else
            return AdapterView.INVALID_POSITION;
    }

    @SuppressWarnings("unused")
    public int getLastWrappedAdapterItemPosition() {
        if (mAdapter != null)
            return mAdapter.getFirstWrappedAdapterItemPosition()
                    + mAdapter.getItemCount() - 1;
        else
            return AdapterView.INVALID_POSITION;
    }

    @SuppressWarnings("unused")
    public HeaderFooterViewListAdapter.PositionType getPositionType(int position) {
        if (mAdapter != null && position >= 0 && position < getCount()) {
            return mAdapter.getPositionType(position);
        } else {
            return null;
        }
    }
}
