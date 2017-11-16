package com.liverkick.elven.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.liverkick.elven.R;
import com.liverkick.elven.adapter.LectureAdapter;
import com.liverkick.elven.models.Lecture;

public class LectureActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public Lecture currentLecture;
    private LectureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        View v = getWindow().getDecorView().getRootView();
        Intent i = getIntent();
        currentLecture = (Lecture) i.getParcelableExtra("lecture-item");

        TextView title = (TextView)findViewById(R.id.title);
        WebView body = (WebView)findViewById(R.id.body);
        title.setText(currentLecture.getTitle());
        body.loadDataWithBaseURL(null, currentLecture.getBody(),"text/html", "UTF-8", null);
    }
}
