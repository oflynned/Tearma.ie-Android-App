package com.syzible.tearma.TermDetailsDisplay;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.Details;
import com.syzible.tearma.Common.Objects.Mutations;

public interface TermDetailsView extends Mvp.IView {
    void setTitle(String title);

    void setSubtitle(String subtitle);

    void setDomains(String... domains);

    void setDetails(Details details);

    void setMutations(Mutations mutations);

    void setExamples(Details details, Mutations mutations);
}
