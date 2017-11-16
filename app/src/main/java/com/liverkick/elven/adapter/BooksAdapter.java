package com.liverkick.elven.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liverkick.elven.R;
import com.liverkick.elven.activity.LecturesActivity;
import com.liverkick.elven.models.Book;

import java.util.List;

/**
 * Created by bekarys on 13.11.17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private Context mContext;
    private List<Book> booksList;

    public BooksAdapter(Context _mContext, List<Book> _booksList) {
        this.mContext = _mContext;
        this.booksList = _booksList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new ViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPosition(position);
        holder.title.setText(booksList.get(position).getTitle());
        holder.description.setText(booksList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title, description;
        int position;

        public ViewHolder(View view, final Context event) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(event, LecturesActivity.class);
                    intent.putExtra("book-item", (Parcelable) booksList.get(position));
                    event.startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
