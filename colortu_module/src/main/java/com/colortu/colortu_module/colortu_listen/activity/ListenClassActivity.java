package com.colortu.colortu_module.colortu_listen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_listen.adapter.ListenClassAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenClassViewModel;
import com.colortu.colortu_module.databinding.ActivityListenClassBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenClassActivity
 * @describe :听写课界面
 */
@Route(path = BaseConstant.LISTEN_CLASS)
public class ListenClassActivity extends BaseActivity<ListenClassViewModel, ActivityListenClassBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //1.语文,2.英语
    private int type;
    //版本id
    private int publisherid;
    //课列表适配器
    private ListenClassAdapter listenClassAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_class;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.classParentview);

        type = bundle.getInt("type");
        publisherid = bundle.getInt("publisherid");
        viewModel.subjectid.set(type);
        viewModel.gid.set(GetBeanDate.getChooseGrade());
        viewModel.publisherid.set(publisherid);

        if (type == 1) {
            //语文、选择年级
            setnametip();
            viewModel.clicktip.set(getResources().getString(R.string.choose_grade2));
        } else {
            //英语、选择教材
            setnametip();
            viewModel.clicktip.set(getResources().getString(R.string.choose_version));
        }

        //免费课数
        int freecount = GetBeanDate.getFreeExamCount();
        //是否会员
        int isVip = GetBeanDate.getIsBookVIP();

        //课列表适配器实例化
        listenClassAdapter = new ListenClassAdapter(freecount, isVip, type, publisherid);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.classList.setLayoutManager(linearLayoutManager);
        binding.classList.setAdapter(listenClassAdapter);

        //课列表数据监听
        viewModel.listenClassBeanLiveData.observe(this, new Observer<List<ListenClassBean.DataBean.PoetryVOSBean>>() {
            @Override
            public void onChanged(List<ListenClassBean.DataBean.PoetryVOSBean> poetryVOSBeans) {
                //课列表数据刷新
                listenClassAdapter.clear();
                if (EmptyUtils.listIsEmpty(poetryVOSBeans)) {
                    listenClassAdapter.addAll(poetryVOSBeans);
                }
                listenClassAdapter.notifyDataSetChanged();
            }
        });

        //换年级/换版本
        binding.classClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (type == 1) {//换年级
                    bundle.putInt("type", 2);
                    BaseUIKit.startActivity(UIKitName.LISTEN_CLASS, UIKitName.LISTEN_GRADE, BaseConstant.LISTEN_GRADE,
                            BaseUIKit.OTHER, bundle, ListenClassActivity.this, BaseConstant.REQUEDT_LISTENCHOOSEGRADE);
                } else {//换版本
                    bundle.putInt("type", 2);
                    BaseUIKit.startActivity(UIKitName.LISTEN_CLASS, UIKitName.LISTEN_VERSION, BaseConstant.LISTEN_VERSION,
                            BaseUIKit.OTHER, bundle, ListenClassActivity.this, BaseConstant.REQUEDT_LISTENCHOOSEVERSION);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_LISTENCHOOSEGRADE) {
            viewModel.gid.set(GetBeanDate.getChooseGrade());
            onRefreshClass();
        } else if (resultCode == BaseConstant.REQUEDT_LISTENCHOOSEVERSION) {
            viewModel.gid.set(GetBeanDate.getChooseGrade());
            viewModel.publisherid.set(GetBeanDate.getChooseVersion());
            onRefreshClass();
        }
    }

    /**
     * 刷新课目数据
     */
    public void onRefreshClass() {
        setnametip();
        listenClassAdapter.clear();
        listenClassAdapter.notifyDataSetChanged();
        viewModel.getClassWordsRunnable();
    }

    /**
     * 标题设置
     */
    private void setnametip() {
        String grade = "";
        switch (GetBeanDate.getChooseGrade()) {
            case 1:
                grade = getResources().getString(R.string.grade1);
                break;
            case 2:
                grade = getResources().getString(R.string.grade2);
                break;
            case 3:
                grade = getResources().getString(R.string.grade3);
                break;
            case 4:
                grade = getResources().getString(R.string.grade4);
                break;
            case 5:
                grade = getResources().getString(R.string.grade5);
                break;
            case 6:
                grade = getResources().getString(R.string.grade6);
                break;
        }
        String version = "";
        if (type == 2) {
            switch (GetBeanDate.getChooseVersion()) {
                case 2:
                    version = getResources().getString(R.string.version1);
                    break;
                case 3:
                    version = getResources().getString(R.string.version2);
                    break;
                case 4:
                    version = getResources().getString(R.string.version3);
                    break;
                case 5:
                    version = getResources().getString(R.string.version4);
                    break;
                case 6:
                    version = getResources().getString(R.string.version5);
                    break;
                case 7:
                    version = getResources().getString(R.string.version6);
                    break;
                case 8:
                    version = getResources().getString(R.string.version7);
                    break;
                case 9:
                    version = getResources().getString(R.string.version8);
                    break;
                case 10:
                    version = getResources().getString(R.string.version9);
                    break;
                case 11:
                    version = getResources().getString(R.string.version10);
                    break;
                case 12:
                    version = getResources().getString(R.string.version11);
                    break;
                case 13:
                    version = getResources().getString(R.string.version12);
                    break;
                case 14:
                    version = getResources().getString(R.string.version13);
                    break;
                case 15:
                    version = getResources().getString(R.string.version14);
                    break;
                case 16:
                    version = getResources().getString(R.string.version15);
                    break;
                case 17:
                    version = getResources().getString(R.string.version16);
                    break;
                case 18:
                    version = getResources().getString(R.string.version17);
                    break;
                case 19:
                    version = getResources().getString(R.string.version18);
                    break;
            }
        }
        viewModel.nametip.set(grade + version);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止播放，释放资源
        BaseApplication.onStopTipVoice();
    }
}
