package com.liverkick.elven.activity;

import android.app.ActionBar;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.liverkick.elven.R;
import com.liverkick.elven.adapter.BooksAdapter;
import com.liverkick.elven.database.AppDatabase;
import com.liverkick.elven.models.Book;
import com.liverkick.elven.models.Lecture;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Room.db").allowMainThreadQueries().build();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books");

        booksList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new BooksAdapter(this, booksList);
//        db.getBookDao().clearTable();
//        db.getLectureDao().clearTable();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(isOnline()){
            GetDataFirebase(GetCurDataDB());
        }else{

            GetDataDB();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void goToD(View view){
        Intent Intent = new Intent(view.getContext(), DownloadActivity.class);
        view.getContext().startActivity(Intent);
    }

    public void GetDataFirebase(final List<Book> curDbBooks) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Book>> t = new GenericTypeIndicator<List<Book>>() {
                };
                List<Book> FbBooks = dataSnapshot.getValue(t);
                booksList.clear();
                for (int i=0; i< FbBooks.size(); i++){
                    for(int j=0; j< curDbBooks.size(); j++){
                        if(curDbBooks.get(j).id == FbBooks.get(i).id){
                            FbBooks.get(i).isDownload = true;
                        }
                    }
                }
                booksList.addAll(FbBooks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public List<Book> GetCurDataDB(){
        List<Book> dbBooks = new ArrayList<>();
        dbBooks.addAll(db.getBookDao().getAllPBooks());
        return dbBooks;
    }

    public void GetDataDB(){
        booksList.clear();
        booksList.addAll(db.getBookDao().getAllPBooks());
        adapter.notifyDataSetChanged();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void saveInDB(final int id) {
        booksList.get(id).isDownload = true;
        try {
            db.getBookDao().insert(booksList.get(id));
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DatabaseReference lRef = database.getReference("lectures");
            lRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<List<Lecture>> t = new GenericTypeIndicator<List<Lecture>>() {
                    };
                    List<Lecture> lectures = dataSnapshot.getValue(t);
                    for (int j = 0; j < lectures.size(); j++) {
                        if (lectures.get(j).bookId == booksList.get(id).id) {
                            db.getLectureDao().insert(lectures.get(j));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void prepareBooks() {
        db.getBookDao().clearTable();
        Book a = new Book(1, "T rue Romance", "13");
        db.getBookDao().insert(a);

        a = new Book(2, "Xscpae", "8");
        db.getBookDao().insert(a);

        a = new Book(3, "Maroon 5", "11");
        db.getBookDao().insert(a);

        a = new Book(4, "Born to Die", "12");
        db.getBookDao().insert(a);

        a = new Book(5, "Honeymoon", "14");
        db.getBookDao().insert(a);

        a = new Book(6, "I Need a Doctor", "1");
        db.getBookDao().insert(a);

        a = new Book(7, "Loud", "11");
        db.getBookDao().insert(a);

        a = new Book(8, "Legend", "14");
        db.getBookDao().insert(a);

        a = new Book(9, "Hello", "11");
        db.getBookDao().insert(a);

        a = new Book(10, "Greatest Hits", "17");
        db.getBookDao().insert(a);

        booksList.addAll(db.getBookDao().getAllPBooks());

        adapter.notifyDataSetChanged();
    }
}
