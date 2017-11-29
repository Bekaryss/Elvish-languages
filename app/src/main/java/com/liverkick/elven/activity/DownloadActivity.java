package com.liverkick.elven.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.liverkick.elven.R;
import com.liverkick.elven.adapter.BooksDownAdapter;
import com.liverkick.elven.models.Book;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BooksDownAdapter adapter;
    private List<Book> booksList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books");

        booksList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new BooksDownAdapter(this, booksList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        GetDataFirebase();

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void GetDataFirebase() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Book>> t = new GenericTypeIndicator<List<Book>>() {
                };
                booksList.clear();
                booksList.addAll(dataSnapshot.getValue(t));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
