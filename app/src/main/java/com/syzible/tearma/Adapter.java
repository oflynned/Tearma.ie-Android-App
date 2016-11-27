package com.syzible.tearma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syzible.tearma.Objects.Definition;
import com.syzible.tearma.Objects.Mutations;

import java.util.ArrayList;

/**
 * Created by ed on 30/10/2016
 */

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Definition> definitions = new ArrayList<>();

    Adapter() {}
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
        holder.gender.setText(definition.getDetails().getGender());
        holder.declension.setText(definition.getDetails().getDeclension());
        holder.type.setText(definition.getDetails().getSearchType());
        holder.signpost.setText(definition.getDetails().getSignpost());

        String domainResults = "";
        for(int i=0; i<definition.getDomains().getDomains().size(); i++) {
            domainResults += definition.getDomains().getDomains().get(i).getEnDomain() + "\n";
            domainResults += definition.getDomains().getDomains().get(i).getGaDomain();

            if(!(i == definition.getDomains().getDomains().size() - 1)) domainResults += "\n";
        }

        holder.domains.setText(domainResults);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView term, searchTerm, gender, declension, type, signpost;
        TextView mutations, domains;

        ViewHolder(View itemView) {
            super(itemView);

            term = (TextView) itemView.findViewById(R.id.term);
            searchTerm = (TextView) itemView.findViewById(R.id.searchTerm);
            gender = (TextView) itemView.findViewById(R.id.gender);
            declension = (TextView) itemView.findViewById(R.id.declension);
            type = (TextView) itemView.findViewById(R.id.searchType);
            signpost = (TextView) itemView.findViewById(R.id.signpost);
            mutations = (TextView) itemView.findViewById(R.id.mutations);
            domains = (TextView) itemView.findViewById(R.id.domains);
        }
    }
}

