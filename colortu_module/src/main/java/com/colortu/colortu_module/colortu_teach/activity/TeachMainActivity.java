package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.dialog.DialogWhether;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_teach.adapter.TeachMainAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachMainViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachMainBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachMainActivity
 * @describe :我的教辅界面
 */
@Route(path = BaseConstant.TEACH_MAIN)
public class TeachMainActivity extends BaseActivity<TeachMainViewModel, ActivityTeachMainBinding> implements DialogWhether.OnWhetherListener {
    //是否弹窗
    public DialogWhether dialogWhether;
    //我的教辅列表适配器
    private TeachMainAdapter teachMainAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.teachmainParentview);

        //我的教辅列表适配器实例化
        teachMainAdapter = new TeachMainAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.teachmainList.setLayoutManager(linearLayoutManager);
        binding.teachmainList.setAdapter(teachMainAdapter);

        //item事件监听
        teachMainAdapter.setOnClickSubjectDetailListener(new TeachMainAdapter.OnClickSubjectDetailListener() {
            @Override
            public void OnClickDelete(int ids) {
                //删除
                viewModel.ids.set(ids);

                dialogWhether = new DialogWhether(TeachMainActivity.this);
                dialogWhether.setOnWhetherListener(TeachMainActivity.this);
                dialogWhether.setContent(BaseApplication.getContext().getString(R.string.delete_teaching));
                dialogWhether.show();
            }

            @Override
            public void OnClickItem(TeachMainBean.DataBean.RecordsBean recordsBean, String grade) {
                if (!viewModel.booleanLiveData.getValue()) {
                    //跳转课列表
                    Bundle bundle = new Bundle();
                    bundle.putString("grade", grade);
                    bundle.putInt("bookId", recordsBean.getBookId());
                    BaseUIKit.startActivity(UIKitName.TEACH_MAIN, UIKitName.TEACH_GRADECLASS,
                            BaseConstant.TEACH_GRADECLASS, BaseUIKit.OTHER, bundle);
                }
            }
        });

        //是否显示删除按钮监听
        viewModel.booleanLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.teachmainChooseteachbook.setVisibility(View.INVISIBLE);
                } else {
                    binding.teachmainChooseteachbook.setVisibility(View.VISIBLE);
                }
                teachMainAdapter.setDelete(aBoolean);
                teachMainAdapter.notifyDataSetChanged();
            }
        });

        //我的教辅列表数据监听
        viewModel.teachMainBeanLiveData.observe(this, new Observer<List<TeachMainBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<TeachMainBean.DataBean.RecordsBean> recordsBeans) {
                //我的教辅列表数据刷新
                teachMainAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    teachMainAdapter.addAll(recordsBeans);
                }
                teachMainAdapter.notifyDataSetChanged();
            }
        });

        //判断我的教辅是否为空
        viewModel.isEmpty.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.edit.set(BaseApplication.getInstance().getString(R.string.edit));
                    viewModel.booleanLiveData.setValue(false);
                    binding.teachmainChooseteachbook.setVisibility(View.INVISIBLE);
                    binding.teachmainEdit.setVisibility(View.INVISIBLE);
                    binding.teachmainList.setVisibility(View.GONE);
                    binding.teachmainMinenone.setVisibility(View.VISIBLE);
                } else {
                    binding.teachmainMinenone.setVisibility(View.GONE);
                    if (!viewModel.booleanLiveData.getValue()) {
                        binding.teachmainChooseteachbook.setVisibility(View.VISIBLE);
                    }
                    binding.teachmainEdit.setVisibility(View.VISIBLE);
                    binding.teachmainList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 弹窗否监听
     */
    @Override
    public void onNo() {
        dialogWhether.dismiss();
    }

    /**
     * 弹窗是监听
     */
    @Override
    public void onYes() {
        //删除
        viewModel.deleteTeachBookRunnable();
        dialogWhether.dismiss();
    }
}
