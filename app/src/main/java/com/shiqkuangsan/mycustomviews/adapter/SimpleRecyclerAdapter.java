package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;

/**
 * Created by shiqkuangsan on 2016/10/13.
 *
 * @author shiqkuangsan
 * @summary 自定义RecyclerView的适配器, 需要继承自RecyclerView下的Adapter, 泛型ViewHolder也要继承自RecyclerView下的ViewHolder
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleRecyclerViewHolder> {

    private OnItemClickListener listener;
    private Context context;

    public SimpleRecyclerAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    // 绘制View
    @Override
    public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recycler_main_test, null);
        // 注意这里返回的ViewHolder
        return new SimpleRecyclerViewHolder(view);
    }

    // 设置数据
    @Override
    public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
        final int mPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(mPosition);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null)
                    listener.onLongClick(mPosition);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 40;
    }

    class SimpleRecyclerViewHolder extends RecyclerView.ViewHolder {
        final ImageView iv_card_img;
        final TextView tv_card_text;
        View itemView;

        SimpleRecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_card_img = (ImageView) itemView.findViewById(R.id.iv_recycler_cardimg);
            tv_card_text = (TextView) itemView.findViewById(R.id.tv_recycler_cardtext);
        }
    }
}
