package com.bmbm.latte_core.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.bmbm.latte_core.R;
import com.bmbm.latte_core.delegates.LatteDelegate;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 使用Fragmentation框架功能的Activity基类
 * Created by wxm on 2017/9/18.
 */

public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DELEGATE.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        // wxm:设置Activity的容器
        setContentView(container);
        if(savedInstanceState == null) {
            // wxm:加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
            DELEGATE.loadRootFragment(R.id.delegate_container, getRootDelegate());
        }
    }

    /**
     * 设置根Delegate
     * @return LatteDelegate
     */
    public abstract LatteDelegate getRootDelegate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DELEGATE.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DELEGATE.onBackPressed();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 实现 ISupportActivity 接口
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }

}
