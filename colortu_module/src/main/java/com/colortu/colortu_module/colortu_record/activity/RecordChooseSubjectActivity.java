package com.colortu.colortu_module.colortu_record.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_record.adapter.RecordChooseSubjectAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseSubjectBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordChooseSubjectViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordChoosesubjectBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordChooseSubjectActivity
 * @describe :选择科目界面
 */
@Route(path = BaseConstant.RECORD_CHOOSESUBJECT)
public class RecordChooseSubjectActivity extends BaseActivity<RecordChooseSubjectViewModel, ActivityRecordChoosesubjectBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //录音翻译文本
    private String content;
    //录音url
    private String audioUrl;
    //录音时长
    private String audioTime;
    //选择科目列表适配器
    private RecordChooseSubjectAdapter recordChooseSubjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_choosesubject;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.choosesubjectParentview);

        content = bundle.getString("content");
        audioUrl = bundle.getString("audioUrl");
        audioTime = bundle.getString("audioTime");

        viewModel.translate.set(content);
        viewModel.audiourl.set(audioUrl);
        viewModel.duration.set(audioTime);

        //选择科目列表适配器实例化
        recordChooseSubjectAdapter = new RecordChooseSubjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.choosesubjectList.setLayoutManager(linearLayoutManager);
        binding.choosesubjectList.setAdapter(recordChooseSubjectAdapter);

        /**
         * 选择科目列表事件监听
         */
        recordChooseSubjectAdapter.setOnClickSelectListener(new RecordChooseSubjectAdapter.OnClickSelectListener() {
            @Override
            public void OnClickSelect(String subjectname, int id) {//选择科目
                viewModel.subjectname.set(subjectname);
                viewModel.subjectId.set(id);
                viewModel.addHomeWorkRunnable();
            }
        });

        /**
         * 选择科目列表数据监听
         */
        viewModel.recordChooseSubjectLiveData.observe(this, new Observer<List<RecordChooseSubjectBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordChooseSubjectBean.DataBean.RecordsBean> recordsBeans) {
                //选择科目列表数据监听刷新
                recordChooseSubjectAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    recordChooseSubjectAdapter.addAll(recordsBeans);
                }
                recordChooseSubjectAdapter.notifyDataSetChanged();
            }
        });
    }
}
