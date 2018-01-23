package com.syzible.tearma.Common;

import android.content.Context;

public interface Mvp {

    interface Presenter<View> {
        void attach(View view);

        void detach();
    }

    interface View {
        Context getContext();
    }
}
