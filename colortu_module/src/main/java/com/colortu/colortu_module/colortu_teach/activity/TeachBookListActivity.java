package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.sidebarutils.ListUtil;
import com.colortu.colortu_module.colortu_base.utils.sidebarutils.SideBar;
import com.colortu.colortu_module.colortu_base.utils.sidebarutils.TeachBookSortBean;
import com.colortu.colortu_module.colortu_teach.adapter.TeachBookAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachBookBean;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachBookListViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachBooklistBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachBookListActivity
 * @describe :选择教辅系列下教辅界面
 */
@Route(path = BaseConstant.TEACH_BOOKLIST)
public class TeachBookListActivity extends BaseActivity<TeachBookListViewModel, ActivityTeachBooklistBinding> implements SideBar.OnTouchAssortListener {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //1英语听力 2语文数学
    private int type;
    //教辅系列下教辅列表适配器
    private TeachBookAdapter teachBookAdapter;
    //分类教辅列表数据
    private List<TeachBookSortBean> teachBookSortBeanList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_booklist;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.booklistParentview);
        //判断是小兔作业or作业助手
        if (ChannelUtil.isSJTC()) {
            binding.booklistHelp.setVisibility(View.INVISIBLE);
        }
        BaseApplication.getInstance().addActivity(this);

        type = bundle.getInt("type");
        binding.booklistSidebar.setOnTouchAssortListener(this);

        viewModel.type.set(type);
        if (type == 2) {//设置标题
            binding.booklistTitle.setText(getResources().getString(R.string.chinese_math));
        }

        //教辅系列下教辅适配器实例化
        teachBookAdapter = new TeachBookAdapter(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.booklistList.setLayoutManager(linearLayoutManager2);
        binding.booklistList.setAdapter(teachBookAdapter);

        //教辅系列下教辅列表数据监听
        viewModel.teachBookBeanLiveData.observe(this, new Observer<List<TeachBookBean.DataBean.ListBeanX>>() {
            @Override
            public void onChanged(List<TeachBookBean.DataBean.ListBeanX> listBeanXES) {
                //教辅系列下教辅列表数据刷新
                teachBookSortBeanList.clear();
                if (EmptyUtils.listIsEmpty(listBeanXES)) {
                    for (int i = 0; i < listBeanXES.size(); i++) {
                        TeachBookSortBean teachBookSortBean = new TeachBookSortBean(listBeanXES.get(i).getTitle()
                                , listBeanXES.get(i).getId(), TeachBookSortBean.TYPE_DATA);
                        teachBookSortBeanList.add(teachBookSortBean);
                    }
                    ListUtil.sortList(teachBookSortBeanList);
                    teachBookAdapter.setTeachBookSortBeanList(teachBookSortBeanList);
                }
                teachBookAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onTouchAssortListener(String s) {
        int select = getSelectIndex(s);
        if (select != -1) {
            binding.booklistList.scrollToPosition(select);
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) binding.booklistList.getLayoutManager();
            mLayoutManager.scrollToPositionWithOffset(select, 0);
        }
    }

    private int getSelectIndex(String s) {
        for (int i = 0; i < teachBookSortBeanList.size(); i++) {
            String name = teachBookSortBeanList.get(i).getItem_en();
            if (name.equals(s)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().finishActivity(TeachBookListActivity.class);
    }
}
