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

public class TermOtherDetailsFragment extends Fragment
        implements TermDetailsView, SearchView.OnQueryTextListener {

    private TermDetailsPresenter presenter;
    private Definition definition;

    private TextView title, subtitle, domains;
    private TextView signpost, type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_details_other, container, false);

        title = view.findViewById(R.id.term_title);
        subtitle = view.findViewById(R.id.term_subtitle);
        domains = view.findViewById(R.id.term_domains);

        signpost = view.findViewById(R.id.noun_signpost);
        type = view.findViewById(R.id.noun_type);

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
        setSignpost(details.getSignpost());
    }

    private void setSignpost(String signpost) {
        this.signpost.setText(signpost);

        if (signpost.equals("-1"))
            this.signpost.setVisibility(View.GONE);
    }

    @Override
    public void setMutations(Mutations mutations) {

    }

    @Override
    public void setExamples(Details details, Mutations mutations) {

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
