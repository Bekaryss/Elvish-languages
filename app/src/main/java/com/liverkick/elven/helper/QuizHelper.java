package com.liverkick.elven.helper;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.liverkick.elven.database.AppDatabase;
import com.liverkick.elven.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bekarys on 21.11.17.
 */

public class QuizHelper {
    Context context;
    AppDatabase db;
    List<Question> questions;

    public QuizHelper(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Room.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        questions = new ArrayList<>();
        addQuestion();
    }

    private void addQuestion() {
        Question q1 = new Question("5+2 = ?", "7", "8", "6", "7");
        this.questions.add(q1);
        Question q2 = new Question("2+18 = ?", "18", "19", "20", "20");
        this.questions.add(q2);
        Question q3 = new Question("10-3 = ?", "6", "7", "8", "7");
        this.questions.add(q3);
        Question q4 = new Question("5+7 = ?", "12", "13", "14", "12");
        this.questions.add(q4);
        Question q5 = new Question("3-1 = ?", "1", "3", "2", "2");
        this.questions.add(q5);
        Question q6 = new Question("0+1 = ?", "1", "0", "10", "1");
        this.questions.add(q6);
        Question q7 = new Question("9-9 = ?", "0", "9", "1", "0");
        this.questions.add(q7);
        Question q8 = new Question("3+6 = ?", "8", "7", "9", "9");
        this.questions.add(q8);
        Question q9 = new Question("1+5 = ?", "6", "7", "5", "6");
        this.questions.add(q9);
        Question q10 = new Question("7-5 = ?", "3", "2", "6", "2");
        this.questions.add(q10);
        Question q11 = new Question("7-2 = ?", "7", "6", "5", "5");
        this.questions.add(q11);
        Question q12 = new Question("3+5 = ?", "8", "7", "5", "8");
        this.questions.add(q12);
        Question q13 = new Question("0+6 = ?", "7", "6", "5", "6");
        this.questions.add(q13);
        Question q14 = new Question("12-10 = ?", "1", "2", "3", "2");
        this.questions.add(q14);
        Question q15 = new Question("12+2 = ?", "14", "15", "16", "14");
        this.questions.add(q15);
        Question q16 = new Question("2-1 = ?", "2", "1", "0", "1");
        this.questions.add(q16);
        Question q17 = new Question("6-6 = ?", "6", "12", "0", "0");
        this.questions.add(q17);
        Question q18 = new Question("5-1 = ?", "4", "3", "2", "4");
        this.questions.add(q18);
        Question q19 = new Question("4+2 = ?", "6", "7", "5", "6");
        this.questions.add(q19);
        Question q20 = new Question("5+1 = ?", "6", "7", "5", "6");
        this.questions.add(q20);
        Question q21 = new Question("5-4 = ?", "5", "4", "1", "1");
        this.questions.add(q21);
        for (int i = 0; i < questions.size(); i++) {
            db.getQuestionDao().insert(questions.get(i));
        }

    }

    public List<Question> getAllQuestions() {
        return db.getQuestionDao().getAllQuestions();
    }
}
