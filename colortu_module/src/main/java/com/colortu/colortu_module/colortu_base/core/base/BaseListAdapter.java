package com.colortu.colortu_module.colortu_base.core.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseListAdapter
 * @describe :ListAdapter父类
 */
public abstract class BaseListAdapter<T, BH extends BaseListAdapter.Hodler> extends BaseAdapter {
    private List<T> mList = new ArrayList<>();

    public abstract int getLayoutId();

    public abstract BH creatHolder(View view);

    public abstract void bindItemView(BH bh, int position);

    public abstract class Hodler {
        public View itemView;

        public Hodler(View itemView) {
            this.itemView = itemView;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BH bh;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), getLayoutId(), null);
            bh = creatHolder(convertView);
            convertView.setTag(bh);
        } else {
            bh = (BH) convertView.getTag();
        }
        bindItemView(bh, position);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
