package com.liverkick.elven.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.liverkick.elven.R;
import com.liverkick.elven.activity.DictionaryActivity;
import com.liverkick.elven.models.Word;

import java.util.List;


/**
 * Created by bekarys on 08.12.17.
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> implements Filterable {
    private List<Word> mValues;
    private CustomFilter mFilter;
    private Context mContext;

    public WordsAdapter(List<Word> items, Context context) {
        this.mValues = items;
        this.mFilter = new CustomFilter(WordsAdapter.this);
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.wordEn.setText(String.valueOf(mValues.get(position).getWorden()));
        holder.wordRu.setText(String.valueOf(mValues.get(position).getWordru()));
        holder.wordEl.setText(String.valueOf(mValues.get(position).getWordel()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView wordEn, wordRu, wordEl;
        public Word mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            wordEn = (TextView) view.findViewById(R.id.worden);
            wordRu = (TextView) view.findViewById(R.id.wordru);
            wordEl = (TextView) view.findViewById(R.id.wordel);
        }
    }
    public class CustomFilter extends Filter {
        private WordsAdapter mAdapter;
        private CustomFilter(WordsAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ((DictionaryActivity)mContext).filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                ((DictionaryActivity)mContext).filteredList.addAll(((DictionaryActivity)mContext).dictionaryWords);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Word mWords : ((DictionaryActivity)mContext).dictionaryWords) {
                    if (mWords.getWorden().toLowerCase().startsWith(filterPattern)) {
                        ((DictionaryActivity)mContext).filteredList.add(mWords);
                    }
                }
            }
            System.out.println("Count Number " + ((DictionaryActivity)mContext).filteredList.size());
            results.values = ((DictionaryActivity)mContext).filteredList;
            results.count = ((DictionaryActivity)mContext).filteredList.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            System.out.println("Count Number 2 " + ((List<Word>) results.values).size());
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
