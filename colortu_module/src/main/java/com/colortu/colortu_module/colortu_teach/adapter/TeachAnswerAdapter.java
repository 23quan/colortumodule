package com.colortu.colortu_module.colortu_teach.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachAnswerItemBean;
import com.colortu.colortu_module.colortu_base.bean.TeachTopicAnswerBean;
import com.colortu.colortu_module.colortu_base.bean.TeachTopicItemBean;
import com.colortu.colortu_module.databinding.AdapterTeachAnswerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : TeachAnswerAdapter
 * @describe :原题和答案列表适配器
 */
public class TeachAnswerAdapter extends BaseRecyclerAdapter<TeachTopicAnswerBean.DataBean.QuestionBean> {
    //上下文
    private Context context;
    //原题列表适配器
    private TeachTopicItemAdapter teachTopicItemAdapter;
    //答案列表适配器
    private TeachAnswerItemAdapter teachAnswerItemAdapter;
    //原题数据
    private List<TeachTopicItemBean> teachTopicItemBeanList = new ArrayList<>();
    //答案数据
    private List<TeachAnswerItemBean> teachAnswerItemBeanList = new ArrayList<>();

    public TeachAnswerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_answer;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachTopicAnswerBean.DataBean.QuestionBean item, int position) {
        final AdapterTeachAnswerBinding adapterTeachAnswerBinding = (AdapterTeachAnswerBinding) binding;
        //序号
        adapterTeachAnswerBinding.answerSerialnumber.setText(position + 1 + "、");
        //题目
        adapterTeachAnswerBinding.answerTitle.setText(item.getTitle());

        adapterTeachAnswerBinding.answerTopiclist.setVisibility(View.GONE);
        adapterTeachAnswerBinding.answerAnswerlist.setVisibility(View.GONE);

        //初始化原题数据
        adapterTeachAnswerBinding.answerLooktipic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterTeachAnswerBinding.answerLooktipictip.getText().toString().trim().equals(context.getResources().getString(R.string.check_topic))) {//显示原题
                    adapterTeachAnswerBinding.answerLooktipictip.setText(context.getResources().getString(R.string.putaway_topic));
                    adapterTeachAnswerBinding.answerLooktipicicon.setImageResource(R.mipmap.icon_teach_up);

                    teachTopicItemBeanList.clear();
                    teachTopicItemBeanList = setTopicData(item);

                    if (teachTopicItemBeanList != null) {
                        if (teachTopicItemBeanList.size() > 0) {

                            adapterTeachAnswerBinding.answerTopiclist.setVisibility(View.VISIBLE);
                            adapterTeachAnswerBinding.answerTopicomit.setVisibility(View.GONE);

                            //原题列表适配器实例化
                            teachTopicItemAdapter = new TeachTopicItemAdapter(context);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                            adapterTeachAnswerBinding.answerTopiclist.setLayoutManager(linearLayoutManager);
                            adapterTeachAnswerBinding.answerTopiclist.setNestedScrollingEnabled(false);
                            adapterTeachAnswerBinding.answerTopiclist.setAdapter(teachTopicItemAdapter);

                            //原题数据刷新
                            teachTopicItemAdapter.clear();
                            teachTopicItemAdapter.addAll(teachTopicItemBeanList);
                            teachTopicItemAdapter.notifyDataSetChanged();

                        } else {
                            adapterTeachAnswerBinding.answerTopicomit.setVisibility(View.VISIBLE);
                        }
                    } else {
                        adapterTeachAnswerBinding.answerTopicomit.setVisibility(View.VISIBLE);
                    }

                } else {//隐藏原题
                    adapterTeachAnswerBinding.answerTopicomit.setVisibility(View.GONE);
                    adapterTeachAnswerBinding.answerTopiclist.setVisibility(View.GONE);
                    adapterTeachAnswerBinding.answerLooktipictip.setText(context.getResources().getString(R.string.check_topic));
                    adapterTeachAnswerBinding.answerLooktipicicon.setImageResource(R.mipmap.icon_teach_down);
                }
            }
        });

        //初始化答案数据
        teachAnswerItemBeanList.clear();
        teachAnswerItemBeanList = setAnswerData(item);

        if (teachAnswerItemBeanList != null) {
            if (teachAnswerItemBeanList.size() > 0) {
                adapterTeachAnswerBinding.answerAnsweromit.setVisibility(View.GONE);
                adapterTeachAnswerBinding.answerAnswerlist.setVisibility(View.VISIBLE);

                //答案列表适配器实例化
                teachAnswerItemAdapter = new TeachAnswerItemAdapter(context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                adapterTeachAnswerBinding.answerAnswerlist.setLayoutManager(linearLayoutManager);
                adapterTeachAnswerBinding.answerAnswerlist.setNestedScrollingEnabled(false);
                adapterTeachAnswerBinding.answerAnswerlist.setAdapter(teachAnswerItemAdapter);

                //答案数据刷新
                teachAnswerItemAdapter.clear();
                teachAnswerItemAdapter.addAll(teachAnswerItemBeanList);
                teachAnswerItemAdapter.notifyDataSetChanged();
            } else {
                adapterTeachAnswerBinding.answerAnsweromit.setVisibility(View.VISIBLE);
            }
        } else {
            adapterTeachAnswerBinding.answerAnsweromit.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化原题数据
     *
     * @param questionBean
     */
    public List<TeachTopicItemBean> setTopicData(TeachTopicAnswerBean.DataBean.QuestionBean questionBean) {
        List<TeachTopicItemBean> list = new ArrayList<>();//原题数据

        if (questionBean.getContent() != null) {//一级 content
            if (questionBean.getContent().size() > 0) {
                for (TeachTopicAnswerBean.DataBean.QuestionBean.ContentBean contentBean : questionBean.getContent()) {
                    TeachTopicItemBean teachTopicItemBean = new TeachTopicItemBean();
                    teachTopicItemBean.setType(contentBean.getType());
                    teachTopicItemBean.setContent(contentBean.getContent());
                    list.add(teachTopicItemBean);
                }
            }
        }

        if (questionBean.getSubList() != null) {//一级 sublist
            if (questionBean.getSubList().size() > 0) {
                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX subListBeanXX : questionBean.getSubList()) {
                    if (subListBeanXX != null) {

                        if (subListBeanXX.getContent() != null) {
                            if (subListBeanXX.getContent().size() > 0) {
                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.ContentBeanX contentBeanX : subListBeanXX.getContent()) {
                                    TeachTopicItemBean teachTopicItemBean = new TeachTopicItemBean();
                                    teachTopicItemBean.setType(contentBeanX.getType());
                                    teachTopicItemBean.setContent(contentBeanX.getContent());
                                    list.add(teachTopicItemBean);
                                }
                            }
                        }

                        if (subListBeanXX.getSubList() != null) {//二级 sublist
                            if (subListBeanXX.getSubList().size() > 0) {
                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX subListBeanX : subListBeanXX.getSubList()) {
                                    if (subListBeanX != null) {

                                        if (subListBeanX.getContent() != null) {
                                            if (subListBeanX.getContent().size() > 0) {
                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.ContentBeanXX contentBeanXX : subListBeanX.getContent()) {
                                                    TeachTopicItemBean teachTopicItemBean = new TeachTopicItemBean();
                                                    teachTopicItemBean.setType(contentBeanXX.getType());
                                                    teachTopicItemBean.setContent(contentBeanXX.getContent());
                                                    list.add(teachTopicItemBean);
                                                }
                                            }
                                        }

                                        if (subListBeanX.getSubList() != null) {//三级 sublist
                                            if (subListBeanX.getSubList().size() > 0) {
                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.SubListBean subListBean : subListBeanX.getSubList()) {
                                                    if (subListBean != null) {
                                                        if (subListBean.getContent() != null) {
                                                            if (subListBean.getContent().size() > 0) {
                                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.SubListBean.ContentBeanXXX contentBeanXXX : subListBean.getContent()) {
                                                                    TeachTopicItemBean teachTopicItemBean = new TeachTopicItemBean();
                                                                    teachTopicItemBean.setType(contentBeanXXX.getType());
                                                                    teachTopicItemBean.setContent(contentBeanXXX.getContent());
                                                                    list.add(teachTopicItemBean);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }


    /**
     * 初始化答案数据
     *
     * @param questionBean
     */
    public List<TeachAnswerItemBean> setAnswerData(TeachTopicAnswerBean.DataBean.QuestionBean questionBean) {
        List<TeachAnswerItemBean> list = new ArrayList<>();//答案数据

        if (questionBean.getAnswer() != null) {//一级 answer
            if (questionBean.getAnswer().size() > 0) {
                for (TeachTopicAnswerBean.DataBean.QuestionBean.AnswerBean answerBean : questionBean.getAnswer()) {
                    TeachAnswerItemBean teachAnswerItemBean = new TeachAnswerItemBean();
                    teachAnswerItemBean.setType(answerBean.getType());
                    teachAnswerItemBean.setContent(answerBean.getContent());
                    list.add(teachAnswerItemBean);
                }
            }
        }

        if (questionBean.getSubList() != null) {//一级 sublist
            if (questionBean.getSubList().size() > 0) {
                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX subListBeanXX : questionBean.getSubList()) {
                    if (subListBeanXX != null) {

                        if (subListBeanXX.getAnswer() != null) {
                            if (subListBeanXX.getAnswer().size() > 0) {
                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.AnswerBeanX answerBeanX : subListBeanXX.getAnswer()) {
                                    TeachAnswerItemBean teachAnswerItemBean = new TeachAnswerItemBean();
                                    teachAnswerItemBean.setType(answerBeanX.getType());
                                    teachAnswerItemBean.setContent(answerBeanX.getContent());
                                    list.add(teachAnswerItemBean);
                                }
                            }
                        }

                        if (subListBeanXX.getSubList() != null) {//二级 sublist
                            if (subListBeanXX.getSubList().size() > 0) {
                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX subListBeanX : subListBeanXX.getSubList()) {
                                    if (subListBeanX != null) {

                                        if (subListBeanX.getAnswer() != null) {
                                            if (subListBeanX.getAnswer().size() > 0) {
                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.AnswerBeanXX answerBeanXX : subListBeanX.getAnswer()) {
                                                    TeachAnswerItemBean teachAnswerItemBean = new TeachAnswerItemBean();
                                                    teachAnswerItemBean.setType(answerBeanXX.getType());
                                                    teachAnswerItemBean.setContent(answerBeanXX.getContent());
                                                    list.add(teachAnswerItemBean);
                                                }
                                            }
                                        }

                                        if (subListBeanX.getSubList() != null) {//三级 sublist
                                            if (subListBeanX.getSubList().size() > 0) {
                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.SubListBean subListBean : subListBeanX.getSubList()) {
                                                    if (subListBean != null) {
                                                        if (subListBean.getAnswer() != null) {
                                                            if (subListBean.getAnswer().size() > 0) {
                                                                for (TeachTopicAnswerBean.DataBean.QuestionBean.SubListBeanXX.SubListBeanX.SubListBean.AnswerBeanXXX answerBeanXXX : subListBean.getAnswer()) {
                                                                    TeachAnswerItemBean teachAnswerItemBean = new TeachAnswerItemBean();
                                                                    teachAnswerItemBean.setType(answerBeanXXX.getType());
                                                                    teachAnswerItemBean.setContent(answerBeanXXX.getContent());
                                                                    list.add(teachAnswerItemBean);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }
}
