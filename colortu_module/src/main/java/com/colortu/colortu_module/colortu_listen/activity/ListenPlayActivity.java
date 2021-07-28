package com.colortu.colortu_module.colortu_listen.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.receiver.BlueToothUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenPlayViewModel;
import com.colortu.colortu_module.databinding.ActivityListenPlayBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenPlayActivity
 * @describe :听力播放界面
 */
@Route(path = BaseConstant.LISTEN_PLAY)
public class ListenPlayActivity extends BaseActivity<ListenPlayViewModel, ActivityListenPlayBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.playParentview);
        //注册蓝牙广播
        BlueToothUtils.onRegisterBlueTooth(this);

        viewModel.classname.set(bundle.getString("classname"));
        viewModel.subjectid.set(bundle.getInt("subjectid"));
        viewModel.versionid.set(bundle.getInt("versionid"));
        viewModel.listenClassBean.set((List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean>) bundle.getSerializable("wordsbean"));
        NotificationUtil.setContext(this);

        if (viewModel.listenClassBean.get() != null) {
            if (viewModel.listenClassBean.get().size() == 0) {
                TipToast.tipToastShort(getResources().getString(R.string.none_dictation));
                finish();
            }
        }

        //开始播放监听
        viewModel.isPlay.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.playTime.setVisibility(View.VISIBLE);
                    binding.playPlay.setVisibility(View.VISIBLE);
                    binding.playStarting.setVisibility(View.GONE);
                }
            }
        });

        //答案显示监听
        viewModel.isShowAnswer.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.playAnswer.setVisibility(View.VISIBLE);
                } else {
                    binding.playAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销蓝牙广播
        BlueToothUtils.onUnRegisterBlueTooth(this);
        //销毁资源
        viewModel.onDispose();
    }
}
