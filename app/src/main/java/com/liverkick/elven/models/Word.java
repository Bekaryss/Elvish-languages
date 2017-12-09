package com.liverkick.elven.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by bekarys on 08.12.17.
 */
public class Word {
    private int id;
    private String worden;
    private String wordru;
    private String wordel;
    private int lectureid;

    public Word() {}

    public Word(String _worden, String _wordru, String _wordel, int _lectureid) {
        this.worden = _worden;
        this.wordru = _wordru;
        this.wordel = _wordel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorden() {
        return worden;
    }

    public void setWorden(String worden) {
        this.worden = worden;
    }

    public String getWordru() {
        return wordru;
    }

    public void setWordru(String wordru) {
        this.wordru = wordru;
    }

    public String getWordel() {
        return wordel;
    }

    public void setWordel(String wordel) {
        this.wordel = wordel;
    }

    public int getLectureid() {
        return lectureid;
    }

    public void setLectureid(int lectureid) {
        this.lectureid = lectureid;
    }
}
