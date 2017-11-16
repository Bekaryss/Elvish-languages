package com.liverkick.elven.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.liverkick.elven.models.Book;

import java.util.List;

/**
 * Created by bekarys on 15.11.17.
 */
@Dao
public interface BookDao {
    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllPBooks();
}
