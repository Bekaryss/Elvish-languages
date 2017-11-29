package com.liverkick.elven.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.liverkick.elven.R;
import com.liverkick.elven.activity.BooksActivity;
import com.liverkick.elven.activity.DownloadActivity;
import com.liverkick.elven.activity.LecturesActivity;
import com.liverkick.elven.helper.DownloadHelper;
import com.liverkick.elven.models.Book;

import java.util.List;

/**
 * Created by bekarys on 26.11.17.
 */

public class BooksDownAdapter extends RecyclerView.Adapter<BooksDownAdapter.ViewHolder> {
    private Context mContext;
    private List<Book> booksList;

    public BooksDownAdapter(Context _mContext, List<Book> _booksList) {
        this.mContext = _mContext;
        this.booksList = _booksList;
    }

    @Override
    public BooksDownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_get, parent, false);
        return new BooksDownAdapter.ViewHolder(itemView, mContext);
    }


    @Override
    public void onBindViewHolder(BooksDownAdapter.ViewHolder holder, int position) {
        holder.setPosition(position);
        holder.title.setText(booksList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public Button Download;
        int position;

        public ViewHolder(View view, final Context event) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            Download = (Button) view.findViewById(R.id.download);
            Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DownloadHelper(mContext, booksList.get(position).getUrl());
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
