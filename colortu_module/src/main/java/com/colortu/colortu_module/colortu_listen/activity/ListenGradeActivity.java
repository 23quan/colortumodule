package com.colortu.colortu_module.colortu_listen.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_listen.adapter.ListenGradeAdapter;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenGradeViewModel;
import com.colortu.colortu_module.databinding.ActivityListenGradeBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenGradeActivity
 * @describe :听写年级界面
 */
@Route(path = BaseConstant.LISTEN_GRADE)
public class ListenGradeActivity extends BaseActivity<ListenGradeViewModel, ActivityListenGradeBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //1.第一次进来，2.课目列表进来
    private int type;
    //年级列表适配器
    private ListenGradeAdapter listenGradeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_grade;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.gradeParentview);

        type = bundle.getInt("type");

        //年级列表适配器实例化
        listenGradeAdapter = new ListenGradeAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.gradeList.setLayoutManager(gridLayoutManager);
        binding.gradeList.setAdapter(listenGradeAdapter);

        /**
         * 选择年级监听
         */
        listenGradeAdapter.setOnClickChooseGradeListener(new ListenGradeAdapter.OnClickChooseGradeListener() {
            @Override
            public void OnClickChooseGrade(int id) {
                onChooseGeade(id);
            }
        });

        /**
         * 年级列表数据监听
         */
        viewModel.integerLiveData.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                //年级列表数据刷新
                listenGradeAdapter.clear();
                if (EmptyUtils.listIsEmpty(integers)) {
                    listenGradeAdapter.addAll(integers);
                }
                listenGradeAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onChooseGeade(int id) {
        //是否第一次
        if (!GetBeanDate.getIsFirst()) {
            GetBeanDate.putIsFirst(true);
        }
        GetBeanDate.putChooseGrade(id);
        switch (type) {
            case 1://第一次进来
                BaseUIKit.startActivity(UIKitName.LISTEN_GRADE, UIKitName.LISTEN_SUBJECT,
                        BaseConstant.LISTEN_SUBJECT, BaseUIKit.OTHER, null);
                break;
            case 2://课目列表进来
                setResult(BaseConstant.REQUEDT_LISTENCHOOSEGRADE);
                break;
        }
        finish();
    }
}
