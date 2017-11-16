package com.liverkick.elven.activity;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liverkick.elven.R;
import com.liverkick.elven.adapter.BooksAdapter;
import com.liverkick.elven.database.AppDatabase;
import com.liverkick.elven.models.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<Book> booksList;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Room.db").allowMainThreadQueries().build();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        booksList = new ArrayList<>();
        booksList = db.getBookDao().getAllPBooks();
        adapter = new BooksAdapter(this, booksList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if(booksList.size() == 0){
            prepareBooks();
        }

    }

    private void prepareBooks() {
        Book a = new Book(1, "T rue Romance", "13");
        db.getBookDao().insertAll(a);

        a = new Book(2, "Xscpae", "8");
        db.getBookDao().insertAll(a);

        a = new Book(3, "Maroon 5", "11");
        db.getBookDao().insertAll(a);

        a = new Book(4, "Born to Die", "12");
        db.getBookDao().insertAll(a);

        a = new Book(5, "Honeymoon", "14");
        db.getBookDao().insertAll(a);

        a = new Book(6, "I Need a Doctor", "1");
        db.getBookDao().insertAll(a);

        a = new Book(7, "Loud", "11");
        db.getBookDao().insertAll(a);

        a = new Book(8, "Legend", "14");
        db.getBookDao().insertAll(a);

        a = new Book(9, "Hello", "11");
        db.getBookDao().insertAll(a);

        a = new Book(10, "Greatest Hits", "17");
        db.getBookDao().insertAll(a);

        booksList = db.getBookDao().getAllPBooks();

        adapter.notifyDataSetChanged();
    }
}
