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
import com.colortu.colortu_module.colortu_listen.adapter.ListenVersionAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenVersionBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenVersionViewModel;
import com.colortu.colortu_module.databinding.ActivityListenVersionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenVersionActivity
 * @describe :听写课本版本界面
 */
@Route(path = BaseConstant.LISTEN_VERSION)
public class ListenVersionActivity extends BaseActivity<ListenVersionViewModel, ActivityListenVersionBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //1.未选择进来,2.英语课列表进来
    private int type;
    //版本列表数据适配器
    private ListenVersionAdapter listenVersionAdapter;
    //版本列表数据
    private List<ListenVersionBean.DataBean.RecordsBean> recordsBeanList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_version;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.versionParentview);

        type = bundle.getInt("type");

        //版本列表数据适配器实例化
        listenVersionAdapter = new ListenVersionAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.versionList.setLayoutManager(linearLayoutManager);
        binding.versionList.setAdapter(listenVersionAdapter);

        //版本item点击监听
        listenVersionAdapter.setOnClickChooseVersionListener(new ListenVersionAdapter.OnClickChooseVersionListener() {
            @Override
            public void OnClickChooseVersion(int id) {
                viewModel.isrelease.set(true);
                BaseApplication.onStopTipVoice();
                GetBeanDate.putChooseVersion(id);
                if (type == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 2);
                    bundle.putInt("publisherid", GetBeanDate.getChooseVersion());
                    BaseUIKit.startActivity(UIKitName.LISTEN_VERSION, UIKitName.LISTEN_CLASS,
                            BaseConstant.LISTEN_CLASS, BaseUIKit.OTHER, bundle);
                } else {
                    setResult(BaseConstant.REQUEDT_LISTENCHOOSEVERSION);
                }
                finish();
            }
        });

        //版本数据刷新监听
        viewModel.listenVersionBeanLiveData.observe(this, new Observer<List<ListenVersionBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<ListenVersionBean.DataBean.RecordsBean> recordsBeans) {
                //版本数据刷新
                onRefreshVersion();
            }
        });

        //跳转换年级
        binding.versionGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 3);
                BaseUIKit.startActivity(UIKitName.LISTEN_VERSION, UIKitName.LISTEN_GRADE, BaseConstant.LISTEN_GRADE,
                        BaseUIKit.OTHER, bundle, ListenVersionActivity.this, BaseConstant.REQUEDT_LISTENCHOOSEGRADE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_LISTENCHOOSEGRADE) {
            onRefreshVersion();
        }
    }

    /**
     * 版本数据列表刷新
     */
    public void onRefreshVersion() {
        recordsBeanList.clear();
        listenVersionAdapter.clear();
        if (EmptyUtils.listIsEmpty(viewModel.listenVersionBeanLiveData.getValue())) {
            int grade = GetBeanDate.getChooseGrade();
            for (int i = 1; i < viewModel.listenVersionBeanLiveData.getValue().size(); i++) {
                if (grade >= Integer.parseInt(viewModel.listenVersionBeanLiveData.getValue().get(i).getMinGrade())) {
                    ListenVersionBean.DataBean.RecordsBean recordsBean = viewModel.listenVersionBeanLiveData.getValue().get(i);
                    recordsBeanList.add(recordsBean);
                }
            }
            listenVersionAdapter.addAll(recordsBeanList);
        }
        listenVersionAdapter.notifyDataSetChanged();
    }
}
