package com.syzible.tearma.TermDetailsDisplay;

import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.Domains;
import com.syzible.tearma.Common.Objects.Mutations;
import com.syzible.tearma.Common.Objects.SearchLang;

public class TermDetailsPresenterImpl implements TermDetailsPresenter {

    private TermDetailsView termDetailsView;

    @Override
    public void attach(TermDetailsView termDetailsView) {
        this.termDetailsView = termDetailsView;
    }

    @Override
    public void detach() {
        this.termDetailsView = null;
    }

    @Override
    public void manageDefinition(Definition definition) {
        if (termDetailsView != null) {
            String title = definition.getMutations().getMutation(Mutations.POS.root);
            String subtitle = definition.getSearchMutations().getMutation(Mutations.POS.root);
            Mutations mutations = getWordForms(definition);

            termDetailsView.setTitle(getFormattedTitle(title));
            termDetailsView.setSubtitle(getFormattedTitle(subtitle));

            termDetailsView.setDomains(getDomains(definition.getDomains()));
            termDetailsView.setDetails(definition.getDetails());
            termDetailsView.setMutations(mutations);
        }
    }

    private Mutations getWordForms(Definition definition) {
        return definition.getLang().getSearchLang() == SearchLang.Languages.ga ?
                definition.getSearchMutations() : definition.getMutations();
    }

    private static String getFormattedTitle(String content) {
        String[] data = content.split(" ");
        StringBuilder output = new StringBuilder();

        for (String item : data) {
            String[] hyphenatedItems = item.split("-");
            for (int j = 0; j < hyphenatedItems.length; j++) {
                output.append(hyphenatedItems[j].substring(0, 1).toUpperCase())
                        .append(hyphenatedItems[j].substring(1).toLowerCase());

                if (j < hyphenatedItems.length - 1)
                    output.append("-");
            }

            output.append(" ");
        }

        return output.toString();
    }

    private static String[] getDomains(Domains domains) {
        String[] output = new String[domains.getDomains().size()];

        for (int i = 0; i < domains.getDomains().size(); i++) {
            output[i] = domains.getDomains().get(i).getEnDomain();
        }

        return output;
    }
}
