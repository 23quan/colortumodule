package com.colortu.colortu_module.colortu_record.viewmodel;

import android.text.format.DateFormat;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.QuantizeUtils;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseDateBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordChooseDateViewModel
 * @describe :选择日期界面ViewModel
 */
public class RecordChooseDateViewModel extends BaseActivityViewModel<BaseRequest> {
    private RecordChooseDateBean recordChooseDateBean1, recordChooseDateBean2;
    //日期list(今年)
    private List<String> datelist1 = new ArrayList<>();
    //日期list（去年）
    private List<String> datelist2 = new ArrayList<>();
    private List<RecordChooseDateBean> recordChooseDateBeanList = new ArrayList<>();
    //选择日期列表数据
    public MutableLiveData<List<RecordChooseDateBean>> recordChooseDateLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        setDate();
    }

    /**
     * 计算日期
     */
    private void setDate() {
        //获取当前日期
        int year = Integer.valueOf(String.valueOf(DateFormat.format("yyyy", Calendar.getInstance(Locale.CHINA))));
        int month = Integer.valueOf(String.valueOf(DateFormat.format("MM", Calendar.getInstance(Locale.CHINA))));

        //今年日期数据
        datelist1.clear();
        recordChooseDateBean1 = new RecordChooseDateBean();
        for (int j = 1; j < month + 1; j++) {
            String jm = QuantizeUtils.numberToChinese(j);
            datelist1.add(jm);
        }
        recordChooseDateBean1.setYear(String.valueOf(year));
        recordChooseDateBean1.setMonths(datelist1);
        recordChooseDateBeanList.add(recordChooseDateBean1);

        //去年日期数据
        if (month != 12) {
            datelist2.clear();
            recordChooseDateBean2 = new RecordChooseDateBean();
            for (int i = month + 1; i < 13; i++) {
                String im = QuantizeUtils.numberToChinese(i);
                datelist2.add(im);
            }
            recordChooseDateBean2.setYear(String.valueOf(year - 1));
            recordChooseDateBean2.setMonths(datelist2);
            recordChooseDateBeanList.add(recordChooseDateBean2);
        }

        recordChooseDateLiveData.setValue(recordChooseDateBeanList);
    }
}
