package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.receiver.BlueToothReceiver;
import com.colortu.colortu_module.colortu_base.core.receiver.VolumeReceiver;
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
        //注册蓝牙广播
        BlueToothReceiver.onRegisterBlueTooth(this);
        //注册媒体音量广播
        VolumeReceiver.onRegisterVolume(this);

        position = bundle.getInt("position");
        viewModel.classname.set(bundle.getString("classname"));
        viewModel.examid.set(bundle.getInt("examid"));
        viewModel.audiourl.set(bundle.getString("audiourl"));
        //NotificationUtil.setContext(this);

        freecount = GetBeanDate.getFreeExamCount();
        isVip = GetBeanDate.getIsBookVIP();

        //锁显示
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

        //播放和暂停监听刷新icon
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销蓝牙广播
        BlueToothReceiver.unRegisterBlueTooth(this);
        //注销媒体音量广播
        VolumeReceiver.unRegisterVolume(this);
        //销毁资源
        viewModel.onDispose();
    }
}
