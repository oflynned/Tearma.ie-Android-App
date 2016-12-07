package com.syzible.tearma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syzible.tearma.objects.Definition;
import com.syzible.tearma.objects.Mutations;
import com.syzible.tearma.objects.SearchLang;

import java.util.ArrayList;

/**
 * Created by ed on 30/10/2016
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Definition> definitions = new ArrayList<>();

    public Adapter(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_card, parent, false);
        return new ViewHolder(view);
    }

    private void formatEnCard(final ViewHolder holder, final Definition definition) {
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Save definition")
                        .setMessage("Do you want to save \"" + definition.getMutations().getMutation(Mutations.POS.root) + "\" to a list for use later?")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new DbHelper(view.getContext())
                                        .purge()
                                        .storeDomains(definition, 0)
                                        .printAllTables();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });*/

        holder.term.setText(definition.getMutations().getMutation(Mutations.POS.root));
        holder.searchTerm.setText(definition.getDetails().getSearchTerm());

        if (!definition.getDetails().getSearchType().equals("-1")) {
            String attributes = definition.getDetails().getSearchType();

            if (definition.getDetails().getSearchType().equals("noun")) {
                if (!definition.getDetails().getDeclension().equals("-1"))
                    attributes += ", declension " + definition.getDetails().getDeclension();

                if (!definition.getDetails().getGender().equals("-1") ||
                        definition.getDetails().getGender().equals(""))
                    attributes += ", " + definition.getDetails().getGender();
            }

            holder.attributes.setText(attributes);
        } else holder.attributes.setVisibility(View.GONE);

        if (!definition.getDetails().getSignpost().equals("-1"))
            holder.signpost.setText(definition.getDetails().getSignpost());
        else holder.signpost.setVisibility(View.GONE);

        if (definition.getMutations().getMutations().size() > 1) {
            String mutations = "";
            if (definition.getDetails().getSearchType().equals("noun")) {
                if (definition.getMutations().hasMutation(Mutations.POS.genSing))
                    mutations += "gs: " + definition.getMutations().getMutation(Mutations.POS.genSing) + " ";
                if (definition.getMutations().hasMutation(Mutations.POS.nomPlu))
                    mutations += "np: " + definition.getMutations().getMutation(Mutations.POS.nomPlu) + " ";
                if (definition.getMutations().hasMutation(Mutations.POS.genPlu))
                    mutations += "gp: " + definition.getMutations().getMutation(Mutations.POS.genPlu);
            } else if (definition.getDetails().getSearchType().equals("verb")) {
                if (definition.getMutations().hasMutation(Mutations.POS.gerund))
                    mutations += "gerund: " + definition.getMutations().getMutation(Mutations.POS.gerund) + " ";
                if (definition.getMutations().hasMutation(Mutations.POS.participle))
                    mutations += "participle: " + definition.getMutations().getMutation(Mutations.POS.participle);
            }
            holder.mutations.setText(mutations);
        } else holder.mutations.setVisibility(View.GONE);

        if (definition.getDomains().getDomains().size() > 0) {
            String domainResults = "";
            for (int i = 0; i < definition.getDomains().getDomains().size(); i++) {
                domainResults += definition.getDomains().getDomains().get(i).getEnDomain() + "\n";
                domainResults += definition.getDomains().getDomains().get(i).getGaDomain();

                if (!(i == definition.getDomains().getDomains().size() - 1)) domainResults += "\n";
            }

            holder.domains.setText(domainResults);
        } else holder.domains.setVisibility(View.GONE);
    }

    private void formatGaCard(final ViewHolder holder, final Definition definition) {
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Save definition")
                        .setMessage("Do you want to save \"" + definition.getMutations().getMutation(Mutations.POS.root) + "\" to a list for use later?")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });*/

        holder.term.setText(definition.getMutations().getMutation(Mutations.POS.root));
        holder.searchTerm.setText(definition.getDetails().getSearchTerm());

        if (!definition.getDetails().getSearchType().equals("-1")) {
            String attributes = definition.getDetails().getSearchType();

            if (definition.getDetails().getSearchType().equals("noun")) {
                if (!definition.getDetails().getDeclension().equals("-1"))
                    attributes += ", declension " + definition.getDetails().getDeclension();

                if (!definition.getDetails().getGender().equals("-1"))
                    attributes += ", " + definition.getDetails().getGender();
            }

            holder.attributes.setText(attributes);
        } else
            holder.attributes.setVisibility(View.GONE);

        if (!definition.getDetails().getSignpost().equals("-1"))
            holder.signpost.setText(definition.getDetails().getSignpost());
        else
            holder.signpost.setVisibility(View.GONE);

        if (definition.getSearchMutations().getMutations().size() > 1) {
            String mutations = "";
            if (definition.getDetails().getSearchType().equals("noun")) {
                if (definition.getSearchMutations().hasMutation(Mutations.POS.genSing))
                    mutations += "gs: " + definition.getSearchMutations().getMutation(Mutations.POS.genSing) + " ";
                if (definition.getSearchMutations().hasMutation(Mutations.POS.nomPlu))
                    mutations += "np: " + definition.getSearchMutations().getMutation(Mutations.POS.nomPlu) + " ";
                if (definition.getSearchMutations().hasMutation(Mutations.POS.genPlu))
                    mutations += "gp: " + definition.getSearchMutations().getMutation(Mutations.POS.genPlu);
            } else if (definition.getDetails().getSearchType().equals("verb")) {
                if (definition.getSearchMutations().hasMutation(Mutations.POS.gerund))
                    mutations += "gerund: " + definition.getSearchMutations().getMutation(Mutations.POS.gerund) + " ";
                if (definition.getSearchMutations().hasMutation(Mutations.POS.participle))
                    mutations += "participle: " + definition.getSearchMutations().getMutation(Mutations.POS.participle);
            }
            holder.mutations.setText(mutations);
        } else {
            holder.mutations.setVisibility(View.GONE);
        }

        if (definition.getDomains().getDomains().size() > 0) {
            String domainResults = "";
            for (int i = 0; i < definition.getDomains().getDomains().size(); i++) {
                domainResults += definition.getDomains().getDomains().get(i).getEnDomain() + "\n";
                domainResults += definition.getDomains().getDomains().get(i).getGaDomain();

                if (!(i == definition.getDomains().getDomains().size() - 1)) domainResults += "\n";
            }
            holder.domains.setText(domainResults);
        } else {
            holder.domains.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Definition definition = definitions.get(position);

        if (definition.getLangValue().equals(SearchLang.Languages.en.name())) {
            formatEnCard(holder, definition);
        } else if (definition.getLangValue().equals(SearchLang.Languages.ga.name())) {
            formatGaCard(holder, definition);
        }
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

