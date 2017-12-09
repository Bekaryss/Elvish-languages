package com.liverkick.elven.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.liverkick.elven.R;
import com.liverkick.elven.adapter.LectureAdapter;
import com.liverkick.elven.adapter.WordsByIdAdapter;
import com.liverkick.elven.models.Lecture;
import com.liverkick.elven.models.Word;

import java.util.ArrayList;
import java.util.List;

public class LectureActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public Lecture currentLecture;
    private LectureAdapter adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public List<Word> wordList;
    private WordsByIdAdapter wordAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("words");

        View v = getWindow().getDecorView().getRootView();
        Intent i = getIntent();
        currentLecture = (Lecture) i.getParcelableExtra("lecture-item");


        wordList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycler_view);
        wordAdapter = new WordsByIdAdapter(this, wordList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(wordAdapter);

        TextView title = (TextView)findViewById(R.id.title);
        TextView date = (TextView)findViewById(R.id.date);
        WebView body = (WebView)findViewById(R.id.body);
        title.setText(currentLecture.getTitle());
        date.setText(currentLecture.getDate());
        body.loadDataWithBaseURL(null, currentLecture.getBody(),"text/html", "UTF-8", null);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("News");

        GetDataFirebase();
    }

    public void GetDataFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Word>> t = new GenericTypeIndicator<List<Word>>() {
                };
                List<Word> newList = dataSnapshot.getValue(t);
                for(int i=0; i<newList.size(); i++){
                    if(newList.get(i).getLectureid() == currentLecture.id){
                        wordList.add(newList.get(i));
                    }
                }
                wordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
