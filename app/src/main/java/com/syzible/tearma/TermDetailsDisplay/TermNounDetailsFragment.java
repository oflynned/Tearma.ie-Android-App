package com.syzible.tearma.TermDetailsDisplay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.Mutations;
import com.syzible.tearma.R;

public class TermNounDetailsFragment extends Fragment implements TermDetailsView {

    private TermDetailsPresenter presenter;
    private Definition definition;

    private TextView title, subtitle, domains;
    private TextView ns, np, gs, gp;
    private TextView examples;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_details_noun, container, false);

        title = view.findViewById(R.id.term_title);
        subtitle = view.findViewById(R.id.term_subtitle);
        domains = view.findViewById(R.id.term_domains);

        ns = view.findViewById(R.id.ns_noun_value);
        np = view.findViewById(R.id.np_noun_value);
        gs = view.findViewById(R.id.gs_noun_value);
        gp = view.findViewById(R.id.gp_noun_value);

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
    public void setMutations(Mutations mutations) {
        ns.setText(mutations.getMutation(Mutations.POS.root));
        np.setText(mutations.getMutation(Mutations.POS.nomPlu));
        gs.setText(mutations.getMutation(Mutations.POS.genSing));
        gp.setText(mutations.getMutation(Mutations.POS.genPlu));
    }

    @Override
    public void setExamples(Mutations mutations) {

    }

    @Override
    public TextView getNs() {
        return ns;
    }

    @Override
    public TextView getNp() {
        return np;
    }

    @Override
    public TextView getGs() {
        return gs;
    }

    @Override
    public TextView getGp() {
        return gp;
    }
}
