package com.liverkick.elven.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.liverkick.elven.models.Book;
import com.liverkick.elven.models.Lecture;
import com.liverkick.elven.models.Question;

/**
 * Created by bekarys on 15.11.17.
 */

@Database(entities = {Book.class, Lecture.class, Question.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao getBookDao();
    public abstract LectureDao getLectureDao();
    public abstract QuestionDao getQuestionDao();
}
