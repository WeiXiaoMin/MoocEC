package com.bmbm.moocec;

import android.app.Application;

import com.bmbm.latte_core.app.Latte;
import com.bmbm.latte_ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * 自定义Application，便于初始化一些配置
 * Created by wxm on 2017/9/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
//                .withApiHost()
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .cofigure();
    }
}
