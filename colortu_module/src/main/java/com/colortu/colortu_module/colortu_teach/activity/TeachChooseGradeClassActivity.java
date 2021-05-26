package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_teach.adapter.TeachChooseGradeClassAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachChooseGradeBean;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachChooseGradeClassViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachChoosegradeclassBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachChooseGradeClassActivity
 * @describe :选择教辅年级界面
 */
@Route(path = BaseConstant.TEACH_CHOOSEGRADECLASS)
public class TeachChooseGradeClassActivity extends BaseActivity<TeachChooseGradeClassViewModel, ActivityTeachChoosegradeclassBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //教辅id
    private int id;
    //教辅名字
    private String teachbookame;
    //我的教辅列表数据
    private TeachMainBean.DataBean dataBean;
    //我的教辅列表json数据
    private String dataBeanjson;
    //选择教辅年级适配器
    private TeachChooseGradeClassAdapter teachChooseGradeClassAdapter;
    //已选择教辅年级数据
    private List<TeachChooseGradeBean.DataBean.ListBean> listBeanList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_choosegradeclass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.choosegradeParentview);

        id = bundle.getInt("id");
        teachbookame = bundle.getString("teachbookame");

        viewModel.id.set(id);
        viewModel.teachbookame.set(teachbookame);

        //选择教辅年级适配器实例化
        teachChooseGradeClassAdapter = new TeachChooseGradeClassAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.choosegrdeclassGradelist.setLayoutManager(linearLayoutManager);
        binding.choosegrdeclassGradelist.setAdapter(teachChooseGradeClassAdapter);

        //获取我的教辅列表数据
        dataBeanjson = GetBeanDate.getMineTeachBook();
        if (EmptyUtils.stringIsEmpty(dataBeanjson)) {
            dataBean = JSONObject.parseObject(dataBeanjson, TeachMainBean.DataBean.class);
            viewModel.dataBean.set(dataBean);
            teachChooseGradeClassAdapter.setDataBean(dataBean);
        }

        /**
         * 选择item监听
         */
        teachChooseGradeClassAdapter.setOnClickChooseGeadeListener(new TeachChooseGradeClassAdapter.OnClickChooseGeadeListener() {
            @Override
            public void OnClickChooseGeade(int position, TeachChooseGradeBean.DataBean.ListBean listBean) {
                viewModel.teachChooseGradeBeanLiveData.getValue().get(position).setIschoose(!listBean.isIschoose());
                teachChooseGradeClassAdapter.notifyItemChanged(position);

                listBeanList.clear();
                boolean ischoose = false;
                for (TeachChooseGradeBean.DataBean.ListBean bean : teachChooseGradeClassAdapter.getList()) {
                    if (bean.isIschoose()) {//判断是否有选择
                        listBeanList.add(bean);
                        viewModel.listBean.set(listBeanList);
                        ischoose = true;
                    }
                }

                if (ischoose) {//true显示选择
                    binding.choosegrdeclassChooseview.setVisibility(View.VISIBLE);
                } else {//false隐藏选择
                    binding.choosegrdeclassChooseview.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 选择教辅年级数据监听
         */
        viewModel.teachChooseGradeBeanLiveData.observe(this, new Observer<List<TeachChooseGradeBean.DataBean.ListBean>>() {
            @Override
            public void onChanged(List<TeachChooseGradeBean.DataBean.ListBean> listBeans) {
                //选择教辅年级数据刷新
                teachChooseGradeClassAdapter.addAll(listBeans);
                teachChooseGradeClassAdapter.notifyDataSetChanged();
            }
        });
    }
}
