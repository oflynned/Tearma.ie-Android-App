package com.syzible.tearma.TermDetailsDisplay;

import android.widget.TextView;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.Domains;
import com.syzible.tearma.Common.Objects.Mutations;

public interface TermDetailsView extends Mvp.IView {
    void setTitle(String title);

    void setSubtitle(String subtitle);

    void setDomains(String... domains);

    void setMutations(Mutations mutations);

    void setExamples(Mutations mutations);

    TextView getNs();

    TextView getNp();

    TextView getGs();

    TextView getGp();
}
