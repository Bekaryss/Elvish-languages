package com.liverkick.elven.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.liverkick.elven.R;
import com.liverkick.elven.adapter.SimpleDividerItemDecoration;
import com.liverkick.elven.adapter.WordsAdapter;
import com.liverkick.elven.models.Word;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private WordsAdapter mAdapter;
    private EditText searchBox;
    public List<Word> dictionaryWords;
    public List<Word> filteredList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("words");
        dictionaryWords = new ArrayList<Word>();
        filteredList = new ArrayList<Word>();

        searchBox = (EditText)findViewById(R.id.search_box);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.item_list);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        assert recyclerView != null;

        mAdapter = new WordsAdapter(filteredList, this);
        recyclerView.setAdapter(mAdapter);

        // search suggestions using the edittext widget
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        GetDataFirebase();
    }

    public void GetDataFirebase() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Word>> t = new GenericTypeIndicator<List<Word>>() {
                };
                List<Word> DbWords = dataSnapshot.getValue(t);
                dictionaryWords.clear();
                dictionaryWords = DbWords;
                filteredList.addAll(dictionaryWords);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private List<Word> getListItemData(){
        List<Word> listViewItems = new ArrayList<Word>();
        listViewItems.add(new Word("Apple", "Apple", "Apple", 1));
        listViewItems.add(new Word("Orange", "Orange", "Orange", 1));
        listViewItems.add(new Word("Orange", "Banana", "Banana", 1));
        listViewItems.add(new Word("Orange", "Grape", "Grape", 1));
        listViewItems.add(new Word("Orange", "Mango", "Mango", 1));

        return listViewItems;
    }
}
