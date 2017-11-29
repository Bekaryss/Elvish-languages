package com.liverkick.elven.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.liverkick.elven.models.Question;

import java.util.List;

/**
 * Created by bekarys on 21.11.17.
 */
@Dao
public interface QuestionDao {
    @Insert
    void insert(Question question);

    @Delete
    void delete(Question question);

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();

    @Query("DELETE FROM questions")
    void clearTable();
}
