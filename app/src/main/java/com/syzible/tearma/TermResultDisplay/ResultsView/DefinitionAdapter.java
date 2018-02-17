package com.syzible.tearma.TermResultDisplay.ResultsView;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.syzible.tearma.Common.Objects.Definition;
import com.syzible.tearma.Common.Objects.Mutations;
import com.syzible.tearma.MainActivity;
import com.syzible.tearma.R;
import com.syzible.tearma.TermDetailsDisplay.TermNounDetailsFragment;
import com.syzible.tearma.TermDetailsDisplay.TermOtherDetailsFragment;
import com.syzible.tearma.TermDetailsDisplay.TermVerbDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ed on 30/10/2016
 */
public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.ViewHolder> {
    private List<Definition> definitions = new ArrayList<>();

    public DefinitionAdapter(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_card, parent, false);
        return new ViewHolder(view);
    }

    private void formatCard(final ViewHolder holder, final Definition definition) {
        holder.term.setText(definition.getMutations().getMutation(Mutations.POS.root));
        holder.searchTerm.setText(definition.getDetails().getSearchTerm());
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definition.changeFavourite();
                YoYo.with(Techniques.RubberBand).duration(700).playOn(holder.favourite);

                holder.favourite.setImageResource(definition.isFavourite() ?
                        R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp);
            }
        });
        holder.favourite.setVisibility(View.GONE);

        if (!definition.getDetails().getSignpost().equals("-1"))
            holder.signpost.setText(definition.getDetails().getSignpost());
        else
            holder.signpost.setVisibility(View.GONE);

        if (definition.getDomains().getDomains().size() > 0) {
            StringBuilder domainResults = new StringBuilder();
            for (int i = 0; i < definition.getDomains().getDomains().size(); i++) {
                domainResults.append(definition.getDomains().getDomains().get(i).getEnDomain());

                if (!(i == definition.getDomains().getDomains().size() - 1))
                    domainResults.append("\n");
            }

            holder.domains.setText(domainResults.toString().toLowerCase());
        } else {
            holder.domains.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Definition definition = definitions.get(position);
        formatCard(holder, definition);

        YoYo.with(position % 2 == 0 ? Techniques.BounceInLeft : Techniques.BounceInRight)
                .duration(700)
                .playOn(holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) holder.itemView.getContext()).getFragmentManager();
                String type = definition.getDetails().getSearchType();

                switch (type) {
                    case "verb":
                        TermVerbDetailsFragment termVerbDetailsFragment = new TermVerbDetailsFragment();
                        termVerbDetailsFragment.setDefinition(definition);
                        MainActivity.setFragmentBackstack(manager, termVerbDetailsFragment);
                        break;
                    case "noun":
                        TermNounDetailsFragment termNounDetailsFragment = new TermNounDetailsFragment();
                        termNounDetailsFragment.setDefinition(definition);
                        MainActivity.setFragmentBackstack(manager, termNounDetailsFragment);
                        break;
                    default:
                        TermOtherDetailsFragment termOtherDetailsFragment = new TermOtherDetailsFragment();
                        termOtherDetailsFragment.setDefinition(definition);
                        MainActivity.setFragmentBackstack(manager, termOtherDetailsFragment);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView term, searchTerm, signpost, domains;
        ImageView favourite;

        ViewHolder(View itemView) {
            super(itemView);

            term = itemView.findViewById(R.id.term);
            searchTerm = itemView.findViewById(R.id.searchTerm);
            signpost = itemView.findViewById(R.id.signpost);
            domains = itemView.findViewById(R.id.domains);
            favourite = itemView.findViewById(R.id.favourite_marker);
        }
    }
}

