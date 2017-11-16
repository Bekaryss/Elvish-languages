package com.liverkick.elven.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bekarys on 13.11.17.
 */

@Entity(tableName = "lectures")
public class Lecture implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "body")
    public String body;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "bookId")
    public int bookId;

    @Ignore
    public Book book;

    public Lecture(){}

    public Lecture(int _id, String _title, String _body, int _bookId){
        this.id = _id;
        this.title = _title;
        this.body = _body;
        this.bookId = _bookId;
    }

    protected Lecture(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
//        date = in.readString();
//        bookId = in.readInt();
//        book = in.readParcelable(Book.class.getClassLoader());
    }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
//        parcel.writeString(date);
//        parcel.writeInt(bookId);
//        parcel.writeParcelable(book, i);
    }
}
