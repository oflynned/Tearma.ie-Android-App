package com.syzible.tearma.Deprecated.UI;

import android.view.View;

import com.syzible.tearma.Common.Objects.Definition;

/**
 * Created by ed on 13/12/2016
 */

public abstract class Card implements View.OnClickListener {
    private Definition definition;

    public Card(Definition definition) {
        this.definition = definition;
    }

    public abstract void onClick();

    public Definition getDefinition() {
        return definition;
    }
}
