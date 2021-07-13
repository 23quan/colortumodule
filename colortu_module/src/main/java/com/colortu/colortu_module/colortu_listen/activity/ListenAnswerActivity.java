package com.colortu.colortu_module.colortu_listen.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_listen.adapter.ListenAnswerAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenAnswerViewModel;
import com.colortu.colortu_module.databinding.ActivityListenAnswerBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenAnswerActivity
 * @describe :听写答案界面
 */
@Route(path = BaseConstant.LISTEN_ANSWER)
public class ListenAnswerActivity extends BaseActivity<ListenAnswerViewModel, ActivityListenAnswerBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //前一个item的position
    private int itemposition;
    //听写答案列表适配器
    private ListenAnswerAdapter listenAnswerAdapter;
    //单词音频列表数据
    private List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean> wordsBeanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_answer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.answerParentview);

        wordsBeanList = (List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean>) bundle.get("wordsbean");

        //听写答案列表适配器实例化
        listenAnswerAdapter = new ListenAnswerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.answerList.setLayoutManager(linearLayoutManager);
        binding.answerList.setAdapter(listenAnswerAdapter);
        //单词音频列表数据刷新
        listenAnswerAdapter.clear();
        listenAnswerAdapter.addAll(wordsBeanList);
        listenAnswerAdapter.notifyDataSetChanged();

        /**
         * 播放监听
         */
        listenAnswerAdapter.setOnClickAnswerListener(new ListenAnswerAdapter.OnClickAnswerListener() {
            @Override
            public void OnClickAnswer(int position, boolean isplay, String audiourl) {
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                //前一个item刷新icon
                if (itemposition != position) {
                    viewModel.audioPlayer.onStop();
                    wordsBeanList.get(itemposition).setPlaying(false);
                    listenAnswerAdapter.notifyItemChanged(itemposition);
                }

                //播放
                if (isplay) {
                    wordsBeanList.get(position).setPlaying(false);
                    viewModel.audioPlayer.onStop();
                } else {
                    //获取音频焦点
                    AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
                    wordsBeanList.get(position).setPlaying(true);
                    viewModel.audioPlayer.onPlay(BaseConstant.ListenAudioUrl + audiourl);
                }
                listenAnswerAdapter.notifyItemChanged(position);

                itemposition = position;
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
                    wordsBeanList.get(itemposition).setPlaying(false);
                    listenAnswerAdapter.notifyItemChanged(itemposition);
                }
            }
        });
    }
}
