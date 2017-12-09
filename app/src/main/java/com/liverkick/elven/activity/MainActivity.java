package com.liverkick.elven.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liverkick.elven.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToH(View view){
        Intent Intent = new Intent(view.getContext(), BooksActivity.class);
         view.getContext().startActivity(Intent);
    }
    public void goToD(View view){
        Intent Intent = new Intent(view.getContext(), DictionaryActivity.class);
        view.getContext().startActivity(Intent);
    }
    public void goToQ(View view){
        Intent Intent = new Intent(view.getContext(), QuizActivity.class);
        view.getContext().startActivity(Intent);
    }


}
