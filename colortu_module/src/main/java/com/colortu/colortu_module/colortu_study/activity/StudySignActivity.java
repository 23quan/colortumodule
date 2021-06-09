package com.colortu.colortu_module.colortu_study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.adapter.StudySignAdapter;
import com.colortu.colortu_module.colortu_base.bean.StudySignBean;
import com.colortu.colortu_module.colortu_study.viewmodel.StudySignViewModel;
import com.colortu.colortu_module.databinding.ActivityStudySignBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/12
 * @module : StudySignActivity
 * @describe :个性签名界面
 */
@Route(path = BaseConstant.STUDY_SIGN)
public class StudySignActivity extends BaseActivity<StudySignViewModel, ActivityStudySignBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //0表示未加入过自习室,1表示已经加入过自习室
    private int isfirst;
    //状态id
    private String signid = "";
    //录音url
    private String audiourl = "";
    //item下标
    private int index;
    //个性签名列表适配器
    private StudySignAdapter studySignAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_sign;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.signParentview);

        isfirst = bundle.getInt("isfirst");

        //个性签名列表适配器实例化
        studySignAdapter = new StudySignAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        binding.signList.setLayoutManager(gridLayoutManager);
        binding.signList.setAdapter(studySignAdapter);

        /**
         * 跳转录入个性语音界面
         */
        binding.signInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("isfirst", isfirst);
                BaseUIKit.startActivity(UIKitName.STUDY_SIGN, UIKitName.STUDY_INPUT, BaseConstant.STUDY_INPUT,
                        BaseUIKit.OTHER, bundle, StudySignActivity.this, BaseConstant.REQUEDT_STUDYINPUT);
            }
        });

        /**
         * 确定修改
         */
        binding.signSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EmptyUtils.stringIsEmpty(signid) || EmptyUtils.stringIsEmpty(audiourl)) {
                    Intent intent = new Intent();
                    intent.putExtra("id", signid);
                    intent.putExtra("audiourl", audiourl);
                    if (isfirst == 0) {
                        setResult(BaseConstant.REQUEDT_STUDYSIGN, intent);
                    } else {
                        setResult(BaseConstant.REQUEDT_STUDYSIGN2, intent);
                    }
                    finish();
                } else {
                    TipToast.tipToastShort(getResources().getString(R.string.not_choosestate));
                }
            }
        });

        /**
         * 播放暂停监听
         */
        viewModel.isPlay.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.inputplayimg.set(R.mipmap.icon_play_stop);
                } else {
                    viewModel.inputplayimg.set(R.mipmap.icon_play_start);
                }
            }
        });

        /**
         * 个性状态选择
         */
        studySignAdapter.setOnClickStudySignListener(new StudySignAdapter.OnClickStudySignListener() {
            @Override
            public void OnClickStudySign(int position, int id) {
                signid = String.valueOf(id);
                viewModel.studysignLiveData.getValue().get(index).setChoose(false);
                viewModel.studysignLiveData.getValue().get(position).setChoose(true);
                studySignAdapter.notifyDataSetChanged();
                index = position;
                viewModel.sureBgColor.setValue(true);
            }
        });

        /**
         * 个性签名列表数据监听
         */
        viewModel.studysignLiveData.observe(this, new Observer<List<StudySignBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<StudySignBean.DataBean.RecordsBean> recordsBeans) {
                //个性签名列表数据刷新
                studySignAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    studySignAdapter.addAll(recordsBeans);
                }
                studySignAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 确定按钮背景颜色监听
         */
        viewModel.sureBgColor.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.signSure.setBackgroundColor(getResources().getColor(R.color.base_blue9));
                }
            }
        });
    }

    /**
     * 数据回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_STUDYINPUT) {
            if (data != null) {
                audiourl = data.getStringExtra("audiourl");
                viewModel.audiourl.set(audiourl);
                if (EmptyUtils.stringIsEmpty(audiourl)) {
                    viewModel.sureBgColor.setValue(true);
                }
                binding.signInputicon.setVisibility(View.GONE);
                binding.signPlay.setVisibility(View.VISIBLE);
            }
        }
    }
}
