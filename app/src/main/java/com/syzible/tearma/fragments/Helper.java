package com.syzible.tearma.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.syzible.tearma.R;

/**
 * Created by ed on 30/11/2016
 */

public class Helper {
    public static void setFragment(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();
    }
}
