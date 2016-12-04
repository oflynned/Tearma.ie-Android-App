package com.syzible.tearma.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Menu;

/**
 * Created by ed on 29/11/2016
 */

public class Icons {
    public static void setIconColour(Menu menu, int item, int colour) {
        Drawable drawable = menu.findItem(item).getIcon();
        if(drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(colour, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
