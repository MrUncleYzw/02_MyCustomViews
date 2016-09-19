package com.shiqkuangsan.mycustomviews.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.photoview.PhotoView;

import org.xutils.x;

import java.util.List;

/**
 * Created by shiqkuangsan on 2016/9/pic3.
 */

/**
 * 图片查看界面的GridView适配器
 */
public class PicGridAdapter extends BaseAdapter {

    private List<Drawable> picsList;
    private Context context;

    public PicGridAdapter(List<Drawable> picsList, Context context) {
        this.picsList = picsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return picsList == null ? 0 : picsList.size();
//        return 1;
    }

    @Override
    public Object getItem(int position) {
        return picsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_picgrid, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        // 注意图片错位bug
        x.image().bind(holder.iv_pic,"http://c.hiphotos.baidu.com/zhidao/pic/item/e1fe9925bc315c609652acc78ab1cb13485477d7.jpg");
        return view;
    }

    private class ViewHolder {
        private PhotoView iv_pic;

        public ViewHolder(View view) {
            iv_pic = (PhotoView) view.findViewById(R.id.iv_griditem);
        }
    }
}
