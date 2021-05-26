package com.colortu.colortu_module.colortu_base.core.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseRecyclerAdapter
 * @describe :RecyclerviewAdapter父类
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ReHodler> {
    private List<T> mList = new ArrayList<>();

    public abstract int getLayoutId();

    public void initBindingField(ViewGroup parent, ViewDataBinding binding) {
    }

    public abstract void bindView(ViewDataBinding binding, T item, int position);

    public static class ReHodler extends RecyclerView.ViewHolder {
        public ReHodler(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ReHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        initBindingField(parent, binding);
        return new ReHodler(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ReHodler reHodler, int position) {
        bindView(DataBindingUtil.bind(reHodler.itemView), getItem(position), position);
    }

    public T getItem(int position) {
        return mList.get(position);
    }


    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void add(T item) {
        if (item != null) {
            mList.add(item);
        }
    }

    public void addAll(List<T> list) {
        if (list != null)
            mList.addAll(list);
    }

    public void remove(T item) {
        mList.remove(item);
    }

    public void clear() {
        mList.clear();
    }
}
