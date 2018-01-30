package com.syzible.tearma.TermResultDisplay.ResultsView;

import com.syzible.tearma.Common.Mvp;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.SearchLang;

import java.util.List;

public interface ResultDisplayView extends Mvp.IView {
    void showCards(List<Definition> definitions);
}
