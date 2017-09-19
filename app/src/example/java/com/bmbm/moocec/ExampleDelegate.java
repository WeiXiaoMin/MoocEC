package com.bmbm.moocec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bmbm.latte_core.delegates.LatteDelegate;

/**
 * 测试
 * Created by wxm on 2017/9/19.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object getLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
