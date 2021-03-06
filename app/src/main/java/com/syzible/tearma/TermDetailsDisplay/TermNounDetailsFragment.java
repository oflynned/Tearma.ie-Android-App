package com.syzible.tearma.TermDetailsDisplay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.syzible.tearma.Common.LanguageUtils;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.Details;
import com.syzible.tearma.Common.Objects.Mutations;
import com.syzible.tearma.R;

public class TermNounDetailsFragment extends Fragment
        implements TermDetailsView, SearchView.OnQueryTextListener {

    private TermDetailsPresenter presenter;
    private Definition definition;

    private TextView title, subtitle, domains;
    private TextView ns, np, gs, gp;
    private TextView exampleNs, exampleNp, exampleGs, exampleGp;
    private TextView signpost, type, declension, gender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_details_noun, container, false);

        title = view.findViewById(R.id.term_title);
        subtitle = view.findViewById(R.id.term_subtitle);
        domains = view.findViewById(R.id.term_domains);

        signpost = view.findViewById(R.id.noun_signpost);
        type = view.findViewById(R.id.noun_type);
        declension = view.findViewById(R.id.noun_declension);
        gender = view.findViewById(R.id.noun_gender);

        ns = view.findViewById(R.id.ns_noun_value);
        np = view.findViewById(R.id.np_noun_value);
        gs = view.findViewById(R.id.gs_noun_value);
        gp = view.findViewById(R.id.gp_noun_value);

        exampleNs = view.findViewById(R.id.noun_mutation_ns);
        exampleNp = view.findViewById(R.id.noun_mutation_np);
        exampleGs = view.findViewById(R.id.noun_mutation_gs);
        exampleGp = view.findViewById(R.id.noun_mutation_gp);

        return view;
    }

    @Override
    public void onResume() {
        if (presenter == null)
            presenter = new TermDetailsPresenterImpl();

        presenter.attach(this);
        presenter.manageDefinition(definition);
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.detach();
        super.onPause();
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }

    @Override
    public void setDomains(String... domains) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < domains.length; i++) {
            builder.append(domains[i]);
            if (i < domains.length - 1)
                builder.append(", ");
        }

        this.domains.setText(builder.toString());
    }

    @Override
    public void setDetails(Details details) {
        type.setText(details.getSearchType());
        gender.setText(details.getGender());

        setSignpost(details.getSignpost());
        setDeclension(details.getDeclension());
    }

    private void setSignpost(String signpost) {
        this.signpost.setText(signpost);

        if (signpost.equals("-1"))
            this.signpost.setVisibility(View.GONE);
    }

    private void setDeclension(String declension) {
        String data = "declension " + declension;
        this.declension.setText(data);

        if (declension.equals("-1"))
            this.declension.setVisibility(View.GONE);
    }

    @Override
    public void setMutations(Mutations mutations) {
        String nomSing = mutations.getMutation(Mutations.POS.root);
        String nomPlu = mutations.getMutation(Mutations.POS.nomPlu);
        String genSing = mutations.getMutation(Mutations.POS.genSing);
        String genPlu = mutations.getMutation(Mutations.POS.genPlu);

        ns.setText(nomSing);
        np.setText(nomPlu.equals("undefined") ? "N/A" : nomPlu);
        gs.setText(genSing.equals("undefined") ? "N/A" : genSing);
        gp.setText(genPlu.equals("undefined") ? "N/A" : genPlu);
    }

    @Override
    public void setExamples(Details details, Mutations mutations) {
        String ns = mutations.getMutation(Mutations.POS.root);
        String np = mutations.getMutation(Mutations.POS.nomPlu);

        String genSingForm = mutations.getMutation(Mutations.POS.genSing);
        String gs = "méid " + (details.getGender().equals("feminine") ?
                "na " + genSingForm : "an " + LanguageUtils.lenite(genSingForm, true));
        String gp = "méid na " + LanguageUtils.eclipse(mutations.getMutation(Mutations.POS.genPlu));

        exampleNs.setText(ns);
        if (ns.equals("undefined"))
            exampleNs.setVisibility(View.GONE);

        exampleNp.setText(np);
        if (np.equals("undefined"))
            exampleNp.setVisibility(View.GONE);

        exampleGs.setText(gs);
        if (gs.equals("undefined"))
            exampleGs.setVisibility(View.GONE);

        exampleGp.setText(gp);
        if (gp.equals("undefined"))
            exampleGp.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
