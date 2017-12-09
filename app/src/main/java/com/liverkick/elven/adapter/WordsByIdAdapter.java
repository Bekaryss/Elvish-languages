package com.liverkick.elven.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liverkick.elven.R;
import com.liverkick.elven.models.Word;

import java.util.List;

/**
 * Created by bekarys on 09.12.17.
 */

public class WordsByIdAdapter extends RecyclerView.Adapter<WordsByIdAdapter.ViewHolder> {

    private Context mContext;
    private List<Word> wordList;

    public WordsByIdAdapter(Context _mContext, List<Word> _wordList) {
        this.mContext = _mContext;
        this.wordList = _wordList;
    }

    @Override
    public WordsByIdAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card, parent, false);
        return new WordsByIdAdapter.ViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(WordsByIdAdapter.ViewHolder holder, int position) {
        holder.setPosition(position);
        holder.worden.setText(wordList.get(position).getWorden());
        holder.wordru.setText(wordList.get(position).getWordru());
        holder.wordel.setText(wordList.get(position).getWordel());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView worden, wordru, wordel;
        int position;

        public ViewHolder(View view, final Context event) {
            super(view);
            worden = (TextView) view.findViewById(R.id.worden);
            wordru = (TextView) view.findViewById(R.id.wordru);
            wordel = (TextView) view.findViewById(R.id.wordel);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
