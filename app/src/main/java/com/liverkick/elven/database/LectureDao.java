package com.liverkick.elven.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.liverkick.elven.models.Lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bekarys on 15.11.17.
 */

@Dao
public interface LectureDao {
    @Insert
    void inserAll(Lecture...lectures);

    @Delete
    void delete(Lecture lecture);

    @Query("SELECT * FROM lectures")
    List<Lecture> getAllLectures();
}
