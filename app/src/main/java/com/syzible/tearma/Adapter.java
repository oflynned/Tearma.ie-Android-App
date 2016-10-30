package com.syzible.tearma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syzible.tearma.Objects.Definition;

import java.util.ArrayList;

/**
 * Created by ed on 30/10/2016
 */

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Definition> definitions = new ArrayList<>();
    private Definition definition;

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
        this.definition = definitions.get(position);
        holder.searchDeclension.setText(definition.getSearchDeclension().equals("null") ? "" : definition.getSearchDeclension());
        holder.searchType.setText(definition.getSearchType());
        holder.searchGender.setText(definition.getSearchGender());

        holder.au.setText("NS: " + definition.getSearchMutations().get("root") + ",");
        holder.gu.setText("GS: " + definition.getSearchMutations().get("genSing") + ",");
        holder.ai.setText("NP: " + definition.getSearchMutations().get("nomPlu") + ",");
        holder.gi.setText("GP: " + definition.getSearchMutations().get("genPlu"));

        holder.searchTerm.setText(definition.getMutations().get("root"));
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public void setData(Definition definition) {
        this.definition = definition;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchTerm, searchType, searchDeclension, searchGender;
        TextView au, ai, gu, gi;
        TextView translation;

        ViewHolder(View itemView) {
            super(itemView);

            searchTerm = (TextView) itemView.findViewById(R.id.searchTerm);
            searchType = (TextView) itemView.findViewById(R.id.searchType);
            searchDeclension = (TextView) itemView.findViewById(R.id.searchDeclension);
            searchGender = (TextView) itemView.findViewById(R.id.searchGender);

            au = (TextView) itemView.findViewById(R.id.search_au);
            gu = (TextView) itemView.findViewById(R.id.search_gu);
            ai = (TextView) itemView.findViewById(R.id.search_ai);
            gi = (TextView) itemView.findViewById(R.id.search_gi);

            translation = (TextView) itemView.findViewById(R.id.mutation_translation);
        }
    }
}

