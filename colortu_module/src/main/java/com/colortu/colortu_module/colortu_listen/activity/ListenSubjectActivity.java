package com.colortu.colortu_module.colortu_listen.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_listen.adapter.ListenSubjectAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenSubjectBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenSubjectViewModel;
import com.colortu.colortu_module.databinding.ActivityListenSubjectBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenSubjectActivity
 * @describe :听写课目界面
 */
@Route(path = BaseConstant.LISTEN_SUBJECT)
public class ListenSubjectActivity extends BaseActivity<ListenSubjectViewModel, ActivityListenSubjectBinding> {
    //科目列表适配器
    private ListenSubjectAdapter listenSubjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_subject;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.listensubjectParentview);

        //科目列表适配器实例化
        listenSubjectAdapter = new ListenSubjectAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.listensubjectList.setLayoutManager(linearLayoutManager);
        binding.listensubjectList.setAdapter(listenSubjectAdapter);

        /**
         * 科目点击监听
         */
        listenSubjectAdapter.setOnClickChooseSubjectListener(new ListenSubjectAdapter.OnClickChooseSubjectListener() {
            @Override
            public void OnClickChooseSubject(int id) {
                sortSubject(id);
            }
        });

        /**
         * 科目数据监听
         */
        viewModel.listenSubjectBeanLiveData.observe(this, new Observer<List<ListenSubjectBean>>() {
            @Override
            public void onChanged(List<ListenSubjectBean> listenSubjectBeans) {
                //科目数据刷新
                listenSubjectAdapter.clear();
                listenSubjectAdapter.addAll(listenSubjectBeans);
                listenSubjectAdapter.notifyDataSetChanged();
            }
        });
    }

    public void sortSubject(int id) {
        Bundle bundle = new Bundle();
        switch (id) {
            case 1://语文
                bundle.putInt("type", 1);
                bundle.putInt("publisherid", 1);
                BaseUIKit.startActivity(UIKitName.LISTEN_SUBJECT, UIKitName.LISTEN_CLASS,
                        BaseConstant.LISTEN_CLASS, BaseUIKit.OTHER, bundle);
                break;
            case 2://英语
                if (GetBeanDate.getChooseVersion() != 0) {//跳转课列表界面
                    bundle.putInt("type", 2);
                    bundle.putInt("publisherid", GetBeanDate.getChooseVersion());
                    BaseUIKit.startActivity(UIKitName.LISTEN_SUBJECT, UIKitName.LISTEN_CLASS,
                            BaseConstant.LISTEN_CLASS, BaseUIKit.OTHER, bundle);
                } else {//跳转选版本界面
                    bundle.putInt("type", 1);
                    BaseUIKit.startActivity(UIKitName.LISTEN_SUBJECT, UIKitName.LISTEN_VERSION, BaseConstant.LISTEN_VERSION,
                            BaseUIKit.OTHER, bundle, ListenSubjectActivity.this, BaseConstant.REQUEDT_LISTENCHOOSEVERSION);
                }
                break;
        }
    }
}
