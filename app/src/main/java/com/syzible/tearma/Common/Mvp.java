package com.syzible.tearma.Common;

import android.content.Context;
import android.view.View;

public interface Mvp {

    interface IPresenter<View> {
        void attach(View view);

        void detach();
    }

    interface IView {
        View getView();
        Context getContext();
    }
}
