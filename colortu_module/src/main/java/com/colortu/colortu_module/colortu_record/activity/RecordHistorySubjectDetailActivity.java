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
import com.colortu.colortu_module.colortu_record.adapter.RecordHistorySubjectDetailAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordHistorySubjectDetailViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordHistorysubjectdetailBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectDetailActivity
 * @describe :历史科目详情界面
 */
@Route(path = BaseConstant.RECORD_HISTORYSUBJECTDETAIL)
public class RecordHistorySubjectDetailActivity extends BaseActivity<RecordHistorySubjectDetailViewModel, ActivityRecordHistorysubjectdetailBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //科目名
    private String subjectname;
    //科目id
    private int subjectId;
    //日期
    private String date;
    //前一个item的position
    private int itemposition;
    //历史科目详情列表适配器
    private RecordHistorySubjectDetailAdapter recordHistorySubjectDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_historysubjectdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.historysubjectdetailParentview);

        subjectname = bundle.getString("subjectname");
        subjectId = bundle.getInt("subjectId");
        date = bundle.getString("date");

        viewModel.subjectname.set(subjectname);
        viewModel.subjectId.set(subjectId);
        viewModel.date.set(date);

        //历史科目详情列表适配器实例化
        recordHistorySubjectDetailAdapter = new RecordHistorySubjectDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.historysubjectdetailList.setLayoutManager(linearLayoutManager);
        binding.historysubjectdetailList.setAdapter(recordHistorySubjectDetailAdapter);

        /**
         * 历史科目详情列表item事件监听
         */
        recordHistorySubjectDetailAdapter.setOnClickHistorySubjectDetailListener(new RecordHistorySubjectDetailAdapter.OnClickHistorySubjectDetailListener() {
            @Override
            public void OnClickPlay(int position, boolean isplay, String audiourl) {
                //前一个item刷新icon
                viewModel.recordSubjectDetailBeanLiveData.getValue().get(itemposition).setIsplay(false);
                recordHistorySubjectDetailAdapter.notifyItemChanged(itemposition);

                //播放
                if (isplay) {
                    viewModel.recordSubjectDetailBeanLiveData.getValue().get(position).setIsplay(false);
                    viewModel.audioPlayer.onStop();
                } else {
                    viewModel.recordSubjectDetailBeanLiveData.getValue().get(position).setIsplay(true);
                    viewModel.audioPlayer.onPlay(audiourl);
                }
                recordHistorySubjectDetailAdapter.notifyItemChanged(position);

                itemposition = position;
            }
        });

        /**
         * 历史科目详情列表数据监听
         */
        viewModel.recordSubjectDetailBeanLiveData.observe(this, new Observer<List<RecordSubjectDetailBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordSubjectDetailBean.DataBean.RecordsBean> recordsBeans) {
                //历史科目详情列表数据刷新
                recordHistorySubjectDetailAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    recordHistorySubjectDetailAdapter.addAll(recordsBeans);
                }
                recordHistorySubjectDetailAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 监听是否播放完成
         */
        viewModel.isPlayLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    //刷新当前item的icon
                    viewModel.recordSubjectDetailBeanLiveData.getValue().get(itemposition).setIsplay(false);
                    recordHistorySubjectDetailAdapter.notifyItemChanged(itemposition);
                }
            }
        });
    }
}
