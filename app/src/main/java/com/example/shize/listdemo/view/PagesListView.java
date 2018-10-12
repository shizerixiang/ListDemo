package com.example.shize.listdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.shize.listdemo.R;

/**
 * 自定义分页ListView
 * Created by shize on 2017/2/20.
 */

public class PagesListView extends ListView implements AbsListView.OnScrollListener {

    private View footer;
    // 最后一个可见item
    private int lastVisibleItem;
    // item总数
    private int totalItemCount;
    // 是否正在加载
    private boolean isLoading;
    private OnLoadData onLoadData;

    public PagesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PagesListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PagesListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 加载页脚
     *
     * @param context 上下文
     */
    public void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.list_footer, null);
        footer.findViewById(R.id.id_paging_load_layout).setVisibility(View.GONE);
        isLoading = false;
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 空闲状态同时滚动到最后一个item
        if (scrollState == SCROLL_STATE_IDLE && totalItemCount == lastVisibleItem) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.id_paging_load_layout).setVisibility(View.VISIBLE);
                // 加载更多
                onLoadData.onLoad();
            }
        }
    }

    /**
     * 暴露回调接口
     * @param onLoadData 回调接口
     */
    public void setOnLoadData(OnLoadData onLoadData) {
        this.onLoadData = onLoadData;
    }

    /**
     * 加载更多回调接口
     */
    public interface OnLoadData{
        void onLoad();
    }
}
