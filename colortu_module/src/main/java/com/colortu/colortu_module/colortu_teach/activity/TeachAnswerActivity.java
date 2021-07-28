package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_teach.adapter.TeachAnswerAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachTopicAnswerBean;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachAnswerViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachAnswerBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachAnswerActivity
 * @describe :原题答案界面
 */
@Route(path = BaseConstant.TEACH_ANSWER)
public class TeachAnswerActivity extends BaseActivity<TeachAnswerViewModel, ActivityTeachAnswerBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //课名
    private String classname;
    //课id
    private int examid;
    //原题答案列表适配器
    private TeachAnswerAdapter teachAnswerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_answer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.answerParentview);

        classname = bundle.getString("classname");
        examid = bundle.getInt("examid");

        viewModel.classname.set(classname);
        viewModel.examid.set(examid);

        //原题答案列表适配器实例化
        teachAnswerAdapter = new TeachAnswerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.answerList.setNestedScrollingEnabled(false);
        binding.answerList.setLayoutManager(linearLayoutManager);
        binding.answerList.setAdapter(teachAnswerAdapter);

        //原题答案列表数据监听
        viewModel.teachTopicAnswerBeanLiveData.observe(this, new Observer<List<TeachTopicAnswerBean.DataBean.QuestionBean>>() {
            @Override
            public void onChanged(List<TeachTopicAnswerBean.DataBean.QuestionBean> questionBeans) {
                //原题答案列表数据刷新
                teachAnswerAdapter.clear();
                teachAnswerAdapter.addAll(questionBeans);
                teachAnswerAdapter.notifyDataSetChanged();
            }
        });
    }
}
