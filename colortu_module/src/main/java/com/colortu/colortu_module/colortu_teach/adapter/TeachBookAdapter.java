package com.colortu.colortu_module.colortu_teach.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.sidebarutils.TeachBookSortBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachBookAdapter
 * @describe :教辅系列下教辅列表适配器
 */
public class TeachBookAdapter extends RecyclerView.Adapter {
    //上下文
    private Context context;
    //分类教辅列表数据
    private List<TeachBookSortBean> teachBookSortBeanList = new ArrayList<>();

    public void setTeachBookSortBeanList(List<TeachBookSortBean> teachBookSortBeanList) {
        this.teachBookSortBeanList = teachBookSortBeanList;
    }

    public TeachBookAdapter(Context context) {
        this.context = context;
    }

    //给item一个唯一标识
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    //如果是多种item，那么在这里判断是哪一种item
    @Override
    public int getItemViewType(int position) {
        if (teachBookSortBeanList.get(position).getItem_type() == TeachBookSortBean.TYPE_CHARACTER) {
            return TeachBookSortBean.TYPE_CHARACTER;
        } else if (teachBookSortBeanList.get(position).getItem_type() == TeachBookSortBean.TYPE_DATA) {
            return TeachBookSortBean.TYPE_DATA;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TeachBookSortBean.TYPE_CHARACTER) {
            View sortview = LayoutInflater.from(context).inflate(R.layout.adapter_teach_booksorttext, parent, false);
            SortViewHolder sortViewHolder = new SortViewHolder(sortview);
            return sortViewHolder;
        } else {
            View dataview = LayoutInflater.from(context).inflate(R.layout.adapter_teach_booklist, parent, false);
            DataViewHolder dataViewHolder = new DataViewHolder(dataview);
            return dataViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SortViewHolder) {
            SortViewHolder sortViewHolder = (SortViewHolder) holder;
            sortViewHolder.teachbookSorttext.setText(teachBookSortBeanList.get(position).getItem_en());
        } else if (holder instanceof DataViewHolder) {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.teachbookName.setText(teachBookSortBeanList.get(position).getTitle());
            dataViewHolder.teachbookName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //选择选择教辅年级界面
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", teachBookSortBeanList.get(position).getId());//教辅id
                    bundle.putString("teachbookame", teachBookSortBeanList.get(position).getTitle());//教辅名字
                    BaseUIKit.startActivity(UIKitName.TEACH_BOOKLIST, UIKitName.TEACH_CHOOSEGRADECLASS,
                            BaseConstant.TEACH_CHOOSEGRADECLASS, BaseUIKit.OTHER, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return teachBookSortBeanList != null ? teachBookSortBeanList.size() : 0;
    }

    //拼音item
    public class SortViewHolder extends RecyclerView.ViewHolder {
        private TextView teachbookSorttext;

        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            teachbookSorttext = (TextView) itemView.findViewById(R.id.book_sorttext);
        }
    }

    //教辅item
    public class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView teachbookName;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            teachbookName = (TextView) itemView.findViewById(R.id.book_name);
        }
    }
}
