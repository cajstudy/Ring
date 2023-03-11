package com.example.ring.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ring.R;
import com.example.ring.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<GoodsBean> listData;
    private ArrayFilter mFilter;
    private ArrayList<GoodsBean> mDatas;
    private final Object mLock = new Object();

    // A copy of the original mObjects array, initialized from and then used instead as soon as
    // the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
    //对象数组的备份，当调用ArrayFilter的时候初始化和使用。此时，对象数组只包含已经过滤的数据。
    private ArrayList<GoodsBean> mOriginalValues;

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    public GoodsAdapter(Context context,ArrayList<GoodsBean> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }
    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return listData.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewholder = null;
        if(view == null){
            LayoutInflater inflate=LayoutInflater.from(context);
            view=inflate.inflate(R.layout.list_item,null);//此处layout是listview item的布局,我的命名不规范！！！
            viewholder = new ViewHolder();
            viewholder.tv_title= (TextView)view.findViewById(R.id.tv_name);
            viewholder.image=(ImageView)view.findViewById(R.id.image);
            viewholder.tv_content = (TextView)view.findViewById(R.id.tv_ms);
            viewholder.tv_us = (TextView)view.findViewById(R.id.tv_us);
            view.setTag(viewholder);
        }
        viewholder = (ViewHolder)view.getTag();
        GoodsBean model = listData.get(pos);

        viewholder.tv_title.setText(model.getGoodsname());
        viewholder.tv_content.setText(model.getGoodsms());
        viewholder.tv_us.setText(model.getGoodsus());
        viewholder.image.setImageResource(model.getImage());
        return view;
    }

    class ViewHolder {
        private ImageView image;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_us;
    }
    /**
     * 过滤数据的类
     */
    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     * <p/>
     * 一个带有首字母约束的数组过滤器，每一项不是以该首字母开头的都会被移除该list。
     */
    private class ArrayFilter extends Filter {
        //执行刷选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();//过滤的结果
            //原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mDatas);
                }
            }
            //当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<GoodsBean> list;
                synchronized (mLock) {//同步复制一个原始备份数据
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
            } else {
                String prefixString = prefix.toString().toLowerCase();//转化为小写

                ArrayList<GoodsBean> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<GoodsBean> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final GoodsBean value = values.get(i);//从List<User>中拿到User对象
//          final String valueText = value.toString().toLowerCase();
                    final String valueText = value.getGoodsname();//User对象的name属性作为过滤的参数
                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString) || valueText.contains(prefixString)) {//第一个字符是否匹配
                        newValues.add(value);//将这个item加入到数组对象中
                    } else {//处理首字符是空格
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;//此时的results就是过滤后的List<User>数组
                results.count = newValues.size();
            }
            return results;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            mDatas = (ArrayList<GoodsBean>) results.values;//此时，Adapter数据源就是过滤后的Results
            if (results.count > 0) {
                notifyDataSetChanged();//这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
            } else {
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated

                 容器中的数据变化 ----> notifyDataSetChanged
                 */
                notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
            }
        }
    }

}