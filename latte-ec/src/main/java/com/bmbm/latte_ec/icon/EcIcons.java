package com.bmbm.latte_ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by wxm on 2017/9/18.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue66a'),
    icon_ali_pay('\ue67c');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
