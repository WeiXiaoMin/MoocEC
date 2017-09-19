package com.bmbm.latte_core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmbm.latte_core.activitys.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment基类，实现Fragmentation功能
 * Created by wxm on 2017/9/18.
 */

abstract class BaseDelegate extends SupportFragment {
    
    private Unbinder mUnbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((int) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout() 方法返回类型必须是int或View");
        }

        // wxm:使用ButterKnife框架
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    /**
     * 设置Fragment的根布局
     *
     * @return 返回 int 或 View 类型
     */
    public abstract Object getLayout();

    /**
     * onCreateView 方法中，ButterKnife.bind 执行后
     *
     * @param savedInstanceState budle
     * @param rootView           Fragment 根布局
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }
}
