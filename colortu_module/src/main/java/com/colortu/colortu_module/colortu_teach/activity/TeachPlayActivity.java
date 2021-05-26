package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachPlayViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachPlayBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachPlayActivity
 * @describe :听力播放界面
 */
@Route(path = BaseConstant.TEACH_PLAY)
public class TeachPlayActivity extends BaseActivity<TeachPlayViewModel, ActivityTeachPlayBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //课名
    private String classname;
    //课id
    private int examid;
    //语音url
    private String audiourl;
    //item的下标
    private int position;
    //免费课数
    private int freecount;
    //0 不是会员 1是会员
    private int isVip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.playParentview);

        classname = bundle.getString("classname");
        examid = bundle.getInt("examid");
        audiourl = bundle.getString("audiourl");
        position = bundle.getInt("position");

        viewModel.classname.set(classname);
        viewModel.examid.set(examid);
        viewModel.audiourl.set(BaseConstant.HomeWorkImgUrl + audiourl);

        freecount = GetBeanDate.getFreeExamCount();
        isVip = GetBeanDate.getIsBookVIP();

        /**
         * 锁显示
         */
        if (isVip == 1) {
            viewModel.isVip.set(true);
            binding.playLock.setVisibility(View.GONE);
        } else {
            if (position < freecount) {
                viewModel.isVip.set(true);
                binding.playLock.setVisibility(View.GONE);
            } else {
                viewModel.isVip.set(false);
                binding.playLock.setVisibility(View.VISIBLE);
            }
        }

        /**
         * 播放和暂停监听刷新icon
         */
        viewModel.isPlayLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.playStart.setImageResource(R.mipmap.icon_play_start);
                } else {
                    binding.playStart.setImageResource(R.mipmap.icon_play_stop);
                }
            }
        });
    }
}
