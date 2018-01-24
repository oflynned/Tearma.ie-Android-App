package com.syzible.tearma.TermDetailsDisplay;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.Definition;

public interface TermDetailsPresenter extends Mvp.IPresenter<TermDetailsView> {
    void manageDefinition(Definition definition);
}
