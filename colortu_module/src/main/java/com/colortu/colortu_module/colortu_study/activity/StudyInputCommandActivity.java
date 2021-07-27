package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.adapter.StudyKeyBoardAdapter;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyInputCommandViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyInputcommandBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyInputCommandActivity
 * @describe :输入自习室口令界面
 */
@Route(path = BaseConstant.STUDY_INPUTCOMMAND)
public class StudyInputCommandActivity extends BaseActivity<StudyInputCommandViewModel, ActivityStudyInputcommandBinding> {
    //口令
    private String commands;
    //数字键盘列表适配器
    private StudyKeyBoardAdapter studyKeyBoardAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_inputcommand;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.inputcommandParentview);

        //数字键盘列表适配器实例化
        studyKeyBoardAdapter = new StudyKeyBoardAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {//控制行数
            @Override
            public int getSpanSize(int position) {
                //第10行占两位置，其余占一个位置
                return position != 10 ? 1 : 2;
            }
        });
        binding.inputcommandList.setLayoutManager(gridLayoutManager);
        binding.inputcommandList.setAdapter(studyKeyBoardAdapter);

        //数字键盘列表item时间监听
        studyKeyBoardAdapter.setOnClickKeyBoardListener(new StudyKeyBoardAdapter.OnClickKeyBoardListener() {
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
                if (EmptyUtils.stringIsEmpty(viewModel.command.get())) {
                    viewModel.getStudyCommand(viewModel.command.get());
                } else {
                    TipToast.tipToastShort(getResources().getString(R.string.studyroom_commandnone));
                }
            }
        });

        //数字键盘列表数据监听
        viewModel.keyboardLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                //数字键盘列表数据刷新
                studyKeyBoardAdapter.clear();
                studyKeyBoardAdapter.addAll(strings);
                studyKeyBoardAdapter.notifyDataSetChanged();
            }
        });
    }
}
