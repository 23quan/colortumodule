package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_qrcode.adapter.QrcodeAccountAdapter;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeAccountViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeAccountBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/6/2
 * @module : QrcodeAccountActivity
 * @describe :账号切换界面
 */
@Route(path = BaseConstant.QRCODE_ACCOUNT)
public class QrcodeAccountActivity extends BaseActivity<QrcodeAccountViewModel, ActivityQrcodeAccountBinding> {
    //跳转至页面的类名
    @Autowired
    public String toPage;
    //跳转至页面的路由
    @Autowired
    public String toPageRoute;
    //销毁后跳转界面路由
    @Autowired
    public String afterPageRoute;
    //1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
    @Autowired
    public int type;
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //uuid账号列表适配器
    private QrcodeAccountAdapter qrcodeAccountAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_account;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.accountParentview);

        //uuid账号列表适配器实例化
        qrcodeAccountAdapter = new QrcodeAccountAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.accountList.setLayoutManager(linearLayoutManager);
        binding.accountList.setAdapter(qrcodeAccountAdapter);

        /**
         * 选择账号监听
         */
        qrcodeAccountAdapter.setOnClickChooseAccountListener(new QrcodeAccountAdapter.OnClickChooseAccountListener() {
            @Override
            public void OnClickChooseAccount(QrcodeUserInfoBean.DataBean.UserListBean userListBean) {
                //存储uuid
                GetBeanDate.putUserUuid(userListBean.getUuid());
                onJumpOther();
            }
        });

        /**
         * 账号列表数据监听
         */
        viewModel.userListLiveData.observe(this, new Observer<List<QrcodeUserInfoBean.DataBean.UserListBean>>() {
            @Override
            public void onChanged(List<QrcodeUserInfoBean.DataBean.UserListBean> userListBeans) {
                qrcodeAccountAdapter.clear();
                qrcodeAccountAdapter.addAll(userListBeans);
                qrcodeAccountAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 跳转其他界面
     */
    public void onJumpOther() {
        switch (type) {
            case BaseUIKit.HAVE://跳转(a界面跳转b界面,b界面销毁跳转c界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_ACCOUNT, "", afterPageRoute, BaseUIKit.HAVE, bundle);
                break;
            case BaseUIKit.OTHER://跳转(a界面跳转b界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_ACCOUNT, toPage, toPageRoute, BaseUIKit.OTHER, bundle);
                break;
        }
        finish();
    }
}
