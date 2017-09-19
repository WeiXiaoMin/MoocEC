package com.bmbm.latte_core.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.bmbm.latte_core.R;
import com.bmbm.latte_core.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 使用Fragmentation框架功能的Activity基类
 * Created by wxm on 2017/9/18.
 */

public abstract class ProxyActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        // wxm:设置Activity的容器
        setContentView(container);
        if(savedInstanceState == null) {
            // wxm:加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
            loadRootFragment(R.id.delegate_container, getRootDelegate());
        }
    }

    /**
     * 设置根Delegate
     * @return LatteDelegate
     */
    public abstract LatteDelegate getRootDelegate();
}
