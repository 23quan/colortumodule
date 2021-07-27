package com.colortu.colortu_module.colortu_record.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.dialog.DialogAffirm;
import com.colortu.colortu_module.colortu_record.adapter.RecordKeyBoardAdapter;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordInputCommandViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordInputcommandBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/1
 * @module : RecordInputCommandActivity
 * @describe :录入口令界面
 */
@Route(path = BaseConstant.RECORD_INPUTCOMMAND)
public class RecordInputCommandActivity extends BaseActivity<RecordInputCommandViewModel, ActivityRecordInputcommandBinding> implements DialogAffirm.OnAffirmListener {
    //口令
    private String commands;
    //确认弹窗
    private DialogAffirm dialogAffirm;
    //数字键盘列表适配器
    private RecordKeyBoardAdapter recordKeyBoardAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_inputcommand;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.inputcommandParentview);

        //数字键盘列表适配器实例化
        recordKeyBoardAdapter = new RecordKeyBoardAdapter();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(RecordInputCommandActivity.this, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//控制行数
            @Override
            public int getSpanSize(int position) {
                //第一行占两位置，其余占一个位置
                return position != 0 ? 1 : 2;
            }
        });
        binding.inputcommandKeyboardlist.setLayoutManager(gridLayoutManager);
        binding.inputcommandKeyboardlist.setAdapter(recordKeyBoardAdapter);

        //数字键盘列表item时间监听
        recordKeyBoardAdapter.setOnClickKeyBoardListener(new RecordKeyBoardAdapter.OnClickKeyBoardListener() {
            @Override
            public void OnClickKeyBoard(String key) {//数字按钮
                int lens = (viewModel.command.get() + key).length();
                if (lens < 9) {
                    commands = viewModel.command.get() + key;
                    viewModel.command.set(commands);
                }
            }

            @Override
            public void OnClickSure() {//确定按钮
                viewModel.copyHomeWorkRunnable();
            }
        });

        //数字键盘列表数据监听
        viewModel.keyboardLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                //数字键盘列表数据刷新
                recordKeyBoardAdapter.clear();
                recordKeyBoardAdapter.addAll(strings);
                recordKeyBoardAdapter.notifyDataSetChanged();
            }
        });

        //监听判断口令是否正确
        viewModel.isright.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    commands = "";
                    viewModel.command.set(commands);

                    dialogAffirm = new DialogAffirm(RecordInputCommandActivity.this);
                    dialogAffirm.setOnAffirmListener(RecordInputCommandActivity.this);
                    dialogAffirm.title.set(viewModel.commanderrortip.get());
                    dialogAffirm.show();
                }
            }
        });
    }

    /**
     * 确认弹窗
     */
    @Override
    public void onAffirm() {
        dialogAffirm.dismiss();
    }
}