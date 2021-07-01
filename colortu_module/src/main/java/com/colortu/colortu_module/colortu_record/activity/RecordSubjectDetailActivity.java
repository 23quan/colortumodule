package com.colortu.colortu_module.colortu_record.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.service.AudioFocusService;
import com.colortu.colortu_module.colortu_base.dialog.DialogWhether;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_record.adapter.RecordSubjectDetailAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordSubjectDetailViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordSubjectdetailBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordSubjectDetailActivity
 * @describe :录入科目详情界面
 */
@Route(path = BaseConstant.RECORD_SUBJECTDETAIL)
public class RecordSubjectDetailActivity extends BaseActivity<RecordSubjectDetailViewModel, ActivityRecordSubjectdetailBinding>
        implements DialogWhether.OnWhetherListener, AudioFocusService.OnAudioFocusListener {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //科目名字
    private String subjectname;
    //科目id
    private int subjectId;
    //是否弹窗
    public DialogWhether dialogWhether;
    //前一个item的position
    private int itemposition;
    //录入科目详情列表适配器
    private RecordSubjectDetailAdapter recordSubjectDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_subjectdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.subjectdetailParentview);
        AudioFocusService.setOnAudioFocusListener(this);

        subjectname = bundle.getString("subjectname");
        subjectId = bundle.getInt("subjectId");

        viewModel.titlename.set(subjectname);
        viewModel.subjectId.set(subjectId);

        //录入科目详情列表适配器实例化
        recordSubjectDetailAdapter = new RecordSubjectDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.subjectdetailList.setLayoutManager(linearLayoutManager);
        binding.subjectdetailList.setAdapter(recordSubjectDetailAdapter);

        /**
         * 录入科目详情列表item事件监听
         */
        recordSubjectDetailAdapter.setOnClickSubjectDetailListener(new RecordSubjectDetailAdapter.OnClickSubjectDetailListener() {
            @Override
            public void OnClickPlay(int position, boolean isplay, String audiourl) {
                //刷新前一个item的icon
                viewModel.recordSubjectDetailBeanLiveData.getValue().get(itemposition).setIsplay(false);
                recordSubjectDetailAdapter.notifyItemChanged(itemposition);

                //播放
                if (isplay) {
                    viewModel.recordSubjectDetailBeanLiveData.getValue().get(position).setIsplay(false);
                    viewModel.audioPlayer.onStop();
                } else {
                    viewModel.recordSubjectDetailBeanLiveData.getValue().get(position).setIsplay(true);
                    viewModel.audioPlayer.onPlay(audiourl);
                }
                recordSubjectDetailAdapter.notifyItemChanged(position);

                itemposition = position;
            }

            @Override
            public void OnClickDelete(int id) {
                //删除
                viewModel.classId.set(id);

                dialogWhether = new DialogWhether(RecordSubjectDetailActivity.this);
                dialogWhether.setOnWhetherListener(RecordSubjectDetailActivity.this);
                dialogWhether.setContent(getResources().getString(R.string.delete_work));
                dialogWhether.show();
            }
        });

        /**
         * 录入科目详情列表数据监听
         */
        viewModel.recordSubjectDetailBeanLiveData.observe(this, new Observer<List<RecordSubjectDetailBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordSubjectDetailBean.DataBean.RecordsBean> recordsBeans) {
                //录入科目详情列表数据刷新
                recordSubjectDetailAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    recordSubjectDetailAdapter.addAll(recordsBeans);
                }
                recordSubjectDetailAdapter.notifyDataSetChanged();
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
                    recordSubjectDetailAdapter.notifyItemChanged(itemposition);
                }
            }
        });

        /**
         * 编辑和完成转换监听
         */
        viewModel.switchLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.subjectdetailInput.setVisibility(View.INVISIBLE);
                } else {
                    binding.subjectdetailInput.setVisibility(View.VISIBLE);
                }
                //刷新数据
                recordSubjectDetailAdapter.setDelete(aBoolean);
                recordSubjectDetailAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 弹窗否监听
     */
    @Override
    public void onNo() {
        dialogWhether.cancel();
    }

    /**
     * 弹窗是监听
     */
    @Override
    public void onYes() {
        //删除
        viewModel.deleteHomeWorkRunnable();
        dialogWhether.cancel();
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (viewModel.audioPlayer.isPlay()) {
            //暂停当前播放
            viewModel.audioPlayer.onStop();
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

    }
}
