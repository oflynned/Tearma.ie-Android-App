package com.syzible.tearma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syzible.tearma.Objects.Definition;
import com.syzible.tearma.Objects.Details;
import com.syzible.tearma.Objects.Mutations;

import java.util.ArrayList;

/**
 * Created by ed on 30/10/2016
 */

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Definition> definitions = new ArrayList<>();

    Adapter() {
    }

    Adapter(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Definition definition = definitions.get(position);

        holder.term.setText(definition.getMutations().getMutation(Mutations.POS.root));
        holder.searchTerm.setText(definition.getDetails().getSearchTerm());

        String attributes = "";
        if (!definition.getDetails().getSearchType().equals("-1"))
            attributes += definition.getDetails().getSearchType();

        if(definition.getDetails().getSearchType().equals("noun")) {
            if (!definition.getDetails().getDeclension().equals("-1"))
                attributes += ", declension " + definition.getDetails().getDeclension();

            if (!definition.getDetails().getGender().equals("-1"))
                attributes += ", " + definition.getDetails().getGender();
        }

        holder.attributes.setText(attributes);

        if (!definition.getDetails().getSignpost().equals("-1"))
            holder.signpost.setText(definition.getDetails().getSignpost());

        String mutations = "";
        if (definition.getDetails().getSearchType().equals("noun")) {
            if (definition.getMutations().hasMutation(Mutations.POS.genSing))
                mutations += "gs: " + definition.getMutations().getMutation(Mutations.POS.genSing);
            if (definition.getMutations().hasMutation(Mutations.POS.nomPlu))
                mutations += ", np: " + definition.getMutations().getMutation(Mutations.POS.nomPlu);
            if (definition.getMutations().hasMutation(Mutations.POS.genPlu))
                mutations += ", gp: " + definition.getMutations().getMutation(Mutations.POS.genPlu);
        } else if(definition.getDetails().getSearchType().equals("verb")) {
            if(definition.getMutations().hasMutation(Mutations.POS.gerund))
                mutations += "gerund: " + definition.getMutations().getMutation(Mutations.POS.gerund);
            if(definition.getMutations().hasMutation(Mutations.POS.participle))
                mutations += ", participle: " + definition.getMutations().getMutation(Mutations.POS.participle);
        }
        holder.mutations.setText(mutations);

        String domainResults = "";
        for (int i = 0; i < definition.getDomains().getDomains().size(); i++) {
            domainResults += definition.getDomains().getDomains().get(i).getEnDomain() + "\n";
            domainResults += definition.getDomains().getDomains().get(i).getGaDomain();

            if (!(i == definition.getDomains().getDomains().size() - 1)) domainResults += "\n";
        }

        holder.domains.setText(domainResults);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView term, searchTerm, attributes, signpost;
        TextView mutations, domains;

        ViewHolder(View itemView) {
            super(itemView);

            term = (TextView) itemView.findViewById(R.id.term);
            searchTerm = (TextView) itemView.findViewById(R.id.searchTerm);
            attributes = (TextView) itemView.findViewById(R.id.attributes);
            signpost = (TextView) itemView.findViewById(R.id.signpost);
            mutations = (TextView) itemView.findViewById(R.id.mutations);
            domains = (TextView) itemView.findViewById(R.id.domains);
        }
    }
}

