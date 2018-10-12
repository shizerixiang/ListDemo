package com.example.shize.listdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shize.listdemo.R;

import java.util.List;

/**
 * RecyclerView的自定义适配器
 * Created by shize on 2017/2/21.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<String> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   父类容器
     * @param viewType item类型
     * @return 创建的ViewHolder
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.permission_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    /**
     * 绑定ViewHolder
     *
     * @param holder   已创建的ViewHolder
     * @param position item位置
     */
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.tvPath.setText(mData.get(position));

        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public void addData(int position, String text) {
        mData.add(position, text);
        notifyItemInserted(position);
    }

    public void deleteData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvPath;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvPath = (TextView) itemView.findViewById(R.id.id_permission_item_path);
        }
    }

    /**
     * item点击回调接口
     */
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}