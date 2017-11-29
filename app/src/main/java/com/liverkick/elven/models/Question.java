package com.liverkick.elven.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by bekarys on 21.11.17.
 */
@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int ID;
    @ColumnInfo(name = "question")
    private String QUESTION;
    @ColumnInfo(name = "opta")
    private String OPTA;
    @ColumnInfo(name = "optb")
    private String OPTB;
    @ColumnInfo(name = "optc")
    private String OPTC;
    @ColumnInfo(name = "answer")
    private String ANSWER;

    public Question() {}

    public Question(String qUESTION, String oPTA, String oPTB, String oPTC, String aNSWER) {
        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;
        ANSWER = aNSWER;
    }

    public int getID() {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public String getOPTA() {
        return OPTA;
    }

    public String getOPTB() {
        return OPTB;
    }

    public String getOPTC() {
        return OPTC;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setID(int id) {
        ID = id;
    }

    public void setQUESTION(String qUESTION) {
        QUESTION = qUESTION;
    }

    public void setOPTA(String oPTA) {
        OPTA = oPTA;
    }

    public void setOPTB(String oPTB) {
        OPTB = oPTB;
    }

    public void setOPTC(String oPTC) {
        OPTC = oPTC;
    }

    public void setANSWER(String aNSWER) {
        ANSWER = aNSWER;
    }
}
