package com.colortu.colortu_module.colortu_study.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.dialog.DialogAffirm;
import com.colortu.colortu_module.colortu_base.dialog.DialogWhether;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_study.adapter.StudyDetailAdapter;
import com.colortu.colortu_module.colortu_base.bean.StudyDetailBean;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyDetailViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyDetailBinding;

import java.util.HashMap;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/9
 * @module : StudyDetailActivity
 * @describe :自习列表详情界面
 */
@Route(path = BaseConstant.STUDY_DETAIL)
public class StudyDetailActivity extends BaseActivity<StudyDetailViewModel, ActivityStudyDetailBinding>
        implements DialogWhether.OnWhetherListener, DialogAffirm.OnAffirmListener, AudioFocusUtils.OnAudioFocusListener {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //标签id
    private String signid;
    //录音url
    private String audiourl = "";
    //false入座自习,true结束自习
    private boolean study = false;
    //是否选择弹窗
    private DialogWhether dialogWhether;
    //确定弹窗
    private DialogAffirm dialogAffirm;
    //小时
    private int hours;
    //分
    private int minutes;
    //秒
    private int seconds;
    //是否中断重新进入自习室
    private boolean againRoom;
    //倒计时
    private CountDownTimer countDownTimer;
    //个人签名播放控制
    private boolean mineplay = false;
    //控制计时
    private boolean firstTime = false;
    //hashmap
    private HashMap hashMap;
    //前一个item的position
    private int itemposition;
    //自习列表详情列表适配器
    private StudyDetailAdapter studyDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.detailParentview);
        AudioFocusUtils.setOnAudioFocusListener(this);

        viewModel.roomid.set(bundle.getInt("id"));
        viewModel.channel.set(bundle.getString("channel"));
        GetBeanDate.putStudyRoomId(viewModel.roomid.get());
        GetBeanDate.putStudyRoomChannel(viewModel.channel.get());

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver, filter);

        initData();
        viewModel.initStudyDetail();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 开始自习/结束自习
         */
        binding.detailStudyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.isfirst == 2) {
                    TipToast.tipToastShort(getResources().getString(R.string.data_loading));
                } else {
                    againRoom = false;
                    if (study) {//结束
                        dialogWhether = new DialogWhether(StudyDetailActivity.this);
                        dialogWhether.setOnWhetherListener(StudyDetailActivity.this);
                        dialogWhether.setContent(getResources().getString(R.string.study_finishmessage));
                        dialogWhether.show();
                    } else {
                        if (viewModel.isfirst == 0) {//第一次入座
                            Bundle bundle = new Bundle();
                            bundle.putInt("isfirst", 0);
                            BaseUIKit.startActivity(UIKitName.STUDY_DETAIL, UIKitName.STUDY_SIGN, BaseConstant.STUDY_SIGN,
                                    BaseUIKit.OTHER, bundle, StudyDetailActivity.this, BaseConstant.REQUEDT_STUDYSIGN);
                        } else if (viewModel.isfirst == 1) {//不是第一次
                            hashMap = new HashMap<>();
                            //自习室id
                            hashMap.put("studyRoomId", viewModel.roomid.get());
                            //用户uuid
                            hashMap.put("uuid", GetBeanDate.getUserUuid());
                            viewModel.getStudyStart(hashMap);
                        }
                    }
                }
            }
        });

        /**
         * 是否入座/结束自习室监听
         */
        viewModel.isStudyLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                hours = 0;
                minutes = 0;
                seconds = 0;
                if (aBoolean) {
                    study = true;
                    viewModel.studytipicon.set(R.mipmap.icon_study_finish);
                    viewModel.studytip.set(getResources().getString(R.string.study_finish));
                    binding.detailStudyview.setBackground(getResources().getDrawable(R.drawable.base_gray8_bg));
                } else {
                    study = false;
                    viewModel.studytipicon.set(R.mipmap.icon_study_start);
                    viewModel.studytip.set(getResources().getString(R.string.study_start));
                    binding.detailStudyview.setBackground(getResources().getDrawable(R.drawable.base_blue7_bg));
                }
            }
        });

        /**
         * 自习室详情数据监听
         */
        viewModel.studyRoomBeanLiveData.observe(this, new Observer<StudyDetailBean.DataBean.StudyRoomBean>() {
            @Override
            public void onChanged(final StudyDetailBean.DataBean.StudyRoomBean studyRoomBean) {
                viewModel.studytitle.set(studyRoomBean.getName());
                viewModel.studynum.set(Math.abs(studyRoomBean.getCurrentNumOfPeople()) + "/" + studyRoomBean.getCurrentMaxNumOfPeople());
                //0官方自习室,1个人自习室
                if (studyRoomBean.getChannel().equals("0")) {
                    binding.detailRecommend.setVisibility(View.GONE);
                    binding.detailInvite.setVisibility(View.GONE);
                } else {
                    binding.detailInvite.setVisibility(View.VISIBLE);
                    if (GetBeanDate.getUserUuid().equals(studyRoomBean.getUuid())) {
                        binding.detailRecommend.setVisibility(View.VISIBLE);
                    } else {
                        binding.detailRecommend.setVisibility(View.GONE);
                    }
                }

                //开始倒计时
                if (!firstTime) {
                    if (EmptyUtils.stringIsEmpty(studyRoomBean.getExpireTime())) {
                        long countdowntime = (Tools.dateDiff2(studyRoomBean.getExpireTime()) * 1000);
                        roomCountDown(countdowntime, true);
                        firstTime = true;
                    } else {
                        long countdowntime = 24 * 60 * 60 * 1000;
                        roomCountDown(countdowntime, false);
                        firstTime = true;
                    }
                }
            }
        });

        /**
         * 邀请同学
         */
        binding.detailInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = "口令:";
                if (EmptyUtils.objectIsEmpty(viewModel.studyRoomBeanLiveData.getValue())) {
                    command = command + viewModel.studyRoomBeanLiveData.getValue().getPassword();
                }
                dialogAffirm = new DialogAffirm(StudyDetailActivity.this);
                dialogAffirm.setOnAffirmListener(StudyDetailActivity.this);
                dialogAffirm.setContent(command + "\n" + getResources().getString(R.string.studyroom_commandmessage));
                dialogAffirm.show();
            }
        });

        /**
         * 自习广场个人详情列表数据监听
         */
        viewModel.topUserBeanLiveData.observe(this, new Observer<StudyDetailBean.DataBean.TopUserBean>() {
            @Override
            public void onChanged(StudyDetailBean.DataBean.TopUserBean topUserBean) {
                if (EmptyUtils.objectIsEmpty(topUserBean)) {
                    study = true;
                    viewModel.studytipicon.set(R.mipmap.icon_study_finish);
                    viewModel.studytip.set(getResources().getString(R.string.study_finish));
                    binding.detailStudyview.setBackground(getResources().getDrawable(R.drawable.base_gray8_bg));
                    setTopUserBean(topUserBean);
                } else {
                    binding.detailMinesign.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 点赞
         */
        binding.detailMinelikeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getStudyLike(GetBeanDate.getUserUuid(), GetBeanDate.getUserUuid(), viewModel.roomid.get());
            }
        });

        /**
         * 播放自己个性语音签名
         */
        binding.detailMineplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                viewModel.typeplay.set(true);
                //刷新当前item的icon
                if (EmptyUtils.listIsEmpty(viewModel.plazaDetailLiveData.getValue())) {
                    if (viewModel.plazaDetailLiveData.getValue().get(itemposition).isIsplay()) {
                        viewModel.audioPlayer.onStop();
                        viewModel.plazaDetailLiveData.getValue().get(itemposition).setIsplay(false);
                        studyDetailAdapter.notifyItemChanged(itemposition);
                    }
                }
                //播放
                if (mineplay) {
                    mineplay = false;
                    viewModel.audioPlayer.onStop();
                    Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_play_stop).into(binding.detailMineplay);
                } else {
                    if (EmptyUtils.stringIsEmpty(viewModel.topUserBeanLiveData.getValue().getUserRecordURL())) {
                        //抢占音频焦点
                        AudioFocusUtils.initAudioFocus(StudyDetailActivity.this);
                        mineplay = true;
                        viewModel.audioPlayer.onPlay(Tools.stringIndexOf(viewModel.topUserBeanLiveData.getValue().getUserRecordURL(), BaseConstant.HomeWorkAudioUrl));
                        Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_play_start).into(binding.detailMineplay);
                    }
                }
            }
        });

        /**
         * 更改个人签名数据
         */
        binding.detailMinesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewModel.audioPlayer.isPlay()){
                    viewModel.audioPlayer.onStop();
                }
                Bundle bundle = new Bundle();
                bundle.putInt("isfirst", viewModel.isfirst);
                BaseUIKit.startActivity(UIKitName.STUDY_DETAIL, UIKitName.STUDY_SIGN, BaseConstant.STUDY_SIGN,
                        BaseUIKit.OTHER, bundle, StudyDetailActivity.this, BaseConstant.REQUEDT_STUDYSIGN2);
            }
        });

        //自习广场列表详情列表适配器实例化
        studyDetailAdapter = new StudyDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.detailList.setLayoutManager(linearLayoutManager);
        binding.detailList.setAdapter(studyDetailAdapter);

        /**
         * 点赞
         */
        studyDetailAdapter.setOnClickLikeListener(new StudyDetailAdapter.OnClickLikeListener() {
            @Override
            public void OnClickLike(String uuid) {
                viewModel.getStudyLike(GetBeanDate.getUserUuid(), uuid, viewModel.roomid.get());
            }
        });

        /**
         * 播放个性签名语音
         */
        studyDetailAdapter.setOnClickPlayListener(new StudyDetailAdapter.OnClickPlayListener() {
            @Override
            public void OnClickPlay(int position, boolean isplay, String audiourl) {
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                viewModel.typeplay.set(false);
                //暂停个人签名播放
                if (mineplay) {
                    mineplay = false;
                    viewModel.audioPlayer.onStop();
                    Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_play_stop).into(binding.detailMineplay);
                }
                //前一个item刷新icon
                if (itemposition != position) {
                    if (viewModel.audioPlayer.isPlay()) {
                        viewModel.audioPlayer.onStop();
                        viewModel.plazaDetailLiveData.getValue().get(itemposition).setIsplay(false);
                        studyDetailAdapter.notifyItemChanged(itemposition);
                    }
                }
                //播放
                if (isplay) {
                    viewModel.plazaDetailLiveData.getValue().get(position).setIsplay(false);
                    viewModel.audioPlayer.onStop();
                } else {
                    if (EmptyUtils.stringIsEmpty(audiourl)) {
                        //抢占音频焦点
                        AudioFocusUtils.initAudioFocus(StudyDetailActivity.this);
                        viewModel.plazaDetailLiveData.getValue().get(position).setIsplay(true);
                        viewModel.audioPlayer.onPlay(audiourl);
                    }
                }
                studyDetailAdapter.notifyItemChanged(position);

                itemposition = position;
            }
        });

        /**
         * 自习广场列表详情列表数据监听
         */
        viewModel.plazaDetailLiveData.observe(this, new Observer<List<StudyDetailBean.DataBean.UserDetailsBean>>() {
            @Override
            public void onChanged(List<StudyDetailBean.DataBean.UserDetailsBean> userDetailsBeans) {
                //自习广场列表详情列表数据刷新
                studyDetailAdapter.clear();
                if (EmptyUtils.listIsEmpty(userDetailsBeans)) {
                    studyDetailAdapter.addAll(userDetailsBeans);
                }
                studyDetailAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 监听是否播放完成
         */
        viewModel.isPlayLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (viewModel.typeplay.get()) {
                        mineplay = false;
                        Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_play_stop).into(binding.detailMineplay);
                    } else {
                        //刷新当前item的icon
                        viewModel.plazaDetailLiveData.getValue().get(itemposition).setIsplay(false);
                        studyDetailAdapter.notifyItemChanged(itemposition);
                    }
                }
            }
        });
    }

    /**
     * 自习室时间倒计时
     *
     * @param time
     */
    private void roomCountDown(long time, final boolean permanent) {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                studyDetailAdapter.notifyDataSetChanged();
                if (study) {
                    if (againRoom) {
                        if (viewModel.topUserBeanLiveData.getValue() != null) {
                            //加入计时
                            String times = Tools.dateDiff3(viewModel.topUserBeanLiveData.getValue().getLastJoinTime());
                            binding.detailMinetime.setText(times);
                        }
                    } else {
                        String perTimes = "";
                        if (seconds == 60) {
                            seconds = 0;
                            if (minutes == 60) {
                                minutes = 1;
                                hours++;
                            } else {
                                minutes++;
                            }
                        }
                        seconds++;

                        if (hours > 0) {
                            perTimes = hours + "小时";
                        }

                        if (minutes > 0) {
                            perTimes = perTimes + minutes + "分钟";
                        }

                        if (seconds > 0) {
                            perTimes = perTimes + seconds + "秒";
                        }
                        binding.detailMinetime.setText(perTimes);
                    }
                }
            }

            @Override
            public void onFinish() {
                if (permanent) {
                    TipToast.tipToastShort(getResources().getString(R.string.studyroom_timeinvalid));
                    viewModel.getStudyClose(viewModel.roomid.get());
                }
            }
        }.start();
    }

    /**
     * 设置个人个性签名数据
     *
     * @param topUserBean
     */
    private void setTopUserBean(StudyDetailBean.DataBean.TopUserBean topUserBean) {
        binding.detailMinesign.setVisibility(View.VISIBLE);
        //头像
        if (EmptyUtils.stringIsEmpty(topUserBean.getAvatar())) {
            Glide.with(StudyDetailActivity.this).load(topUserBean.getAvatar()).into(binding.detailMineheadimg);
        } else {
            Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_work_logo1).into(binding.detailMineheadimg);
        }
        //昵称
        if (EmptyUtils.stringIsEmpty(topUserBean.getNickName())) {
            binding.detailMinename.setText(topUserBean.getNickName());
        } else {
            binding.detailMinename.setText(getResources().getString(R.string.not_login2));
        }
        //加入计时
        String times = Tools.dateDiff3(topUserBean.getLastJoinTime());
        binding.detailMinetime.setText(times);
        //设置状态
        binding.detailMinestatetip.setText(topUserBean.getDescribe());
        Glide.with(StudyDetailActivity.this).load(BaseConstant.HomeWorkImgUrl + topUserBean.getStatusImageUrl()).into(binding.detailMinestateicon);
        //点赞图标显示
        if (topUserBean.getIsClikeLike() == 1) {//已点赞
            Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_study_like).into(binding.detailMinelikeicon);
        } else {//未点赞
            Glide.with(StudyDetailActivity.this).load(R.mipmap.icon_study_unlike).into(binding.detailMinelikeicon);
        }
        //点赞数
        binding.detailMinelikenum.setText(String.valueOf(topUserBean.getUserLikeNum()));
    }

    /**
     * 数据回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_STUDYSIGN) {
            if (data != null) {
                signid = data.getStringExtra("id") + "";
                audiourl = data.getStringExtra("audiourl") + "";

                hashMap = new HashMap<>();
                //自习室id
                hashMap.put("studyRoomId", viewModel.roomid.get());
                //用户uuid
                hashMap.put("uuid", GetBeanDate.getUserUuid());
                //个性状态id
                hashMap.put("userStatusId", signid);
                //语音url
                hashMap.put("userRecordURL", audiourl);
                viewModel.getStudyStart(hashMap);
            }
        } else if (requestCode == BaseConstant.REQUEDT_STUDYSIGN2) {
            if (data != null) {
                signid = data.getStringExtra("id") + "";
                audiourl = data.getStringExtra("audiourl") + "";

                hashMap = new HashMap<>();
                //topuserid
                hashMap.put("id", viewModel.topUserBeanLiveData.getValue().getId());
                //用户uuid
                hashMap.put("uuid", GetBeanDate.getUserUuid());
                //个性状态id
                hashMap.put("userStatusId", signid);
                //语音url
                hashMap.put("userRecordURL", audiourl);
                viewModel.getStudyUploadSign(hashMap);
            }
        }
    }

    /**
     * 弹窗否监听
     */
    @Override
    public void onNo() {
        dialogWhether.dismiss();
    }

    /**
     * 弹窗是监听
     */
    @Override
    public void onYes() {
        //确认结束自习室
        viewModel.getStudyStop(viewModel.roomid.get(), GetBeanDate.getUserUuid());
        dialogWhether.dismiss();
    }

    /**
     * 确认口令提示
     */
    @Override
    public void onAffirm() {
        dialogAffirm.dismiss();
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (viewModel.audioPlayer != null) {
            if (viewModel.audioPlayer.isPlay()) {
                viewModel.audioPlayer.onStop();
            }
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

    }

    /**
     * 监听系统发送当前时间广播
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //每分钟监听一次,亮屏也会监听
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                viewModel.getStudyDetail(viewModel.roomid.get(), GetBeanDate.getUserUuid());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //亮屏为自己退出自习室
        if (BaseApplication.isScreenOn) {
            if (study) {
                GetBeanDate.putIsExitRoom(false);
                viewModel.getStudyStop(viewModel.roomid.get(), GetBeanDate.getUserUuid());
            }
        }
        //倒计时取消
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        //注销广播监听
        unregisterReceiver(broadcastReceiver);
    }
}
