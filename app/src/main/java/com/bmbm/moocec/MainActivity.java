package com.bmbm.moocec;

import com.bmbm.latte_core.activitys.ProxyActivity;
import com.bmbm.latte_core.delegates.LatteDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate getRootDelegate() {
        return new ExampleDelegate();
    }
}
