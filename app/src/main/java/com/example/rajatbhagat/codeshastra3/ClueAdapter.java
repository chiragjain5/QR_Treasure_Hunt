package com.example.rajatbhagat.codeshastra3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rajatbhagat on 26/1/17.
 */

public class ClueAdapter extends RecyclerView.Adapter<ClueAdapter.ClueViewHolder> {

    private List<Clue> clueList;

    public ClueAdapter(List<Clue> clueList) {
        this.clueList = clueList;
    }

    @Override
    public ClueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_clue_details, parent, false);

        return new ClueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClueViewHolder holder, int position) {
        Clue clue = clueList.get(position);
        holder.clueDetailTextView.setText(clue.getClueDetails());
    }

    @Override
    public int getItemCount() {
        return clueList.size();
    }

    public class ClueViewHolder extends RecyclerView.ViewHolder {

        private TextView clueDetailTextView;

        public ClueViewHolder(View itemView) {
            super(itemView);

            clueDetailTextView = (TextView) itemView.findViewById(R.id.text_view_card_view_clue_text);
        }
    }
}
