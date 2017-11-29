package com.liverkick.elven.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.liverkick.elven.R;
import com.liverkick.elven.adapter.LectureAdapter;
import com.liverkick.elven.database.AppDatabase;
import com.liverkick.elven.models.Book;
import com.liverkick.elven.models.Lecture;

import java.util.ArrayList;
import java.util.List;

public class LecturesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Book currentBook;
    private LectureAdapter adapter;
    public List<Lecture> lectureList;
    AppDatabase db;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Room.db").allowMainThreadQueries().build();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("lectures");

        View view = getWindow().getDecorView().getRootView();
        Intent i = getIntent();
        currentBook = (Book) i.getParcelableExtra("book-item");

        lectureList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new LectureAdapter(this, lectureList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(isOnline()){
            GetDataFirebase();
        }else{
            GetDataDB();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void GetDataFirebase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Lecture>> t = new GenericTypeIndicator<List<Lecture>>() {
                };
                List<Lecture> newList = dataSnapshot.getValue(t);
                for (int j=0; j<newList.size(); j++){
                    if(newList.get(j).bookId == currentBook.id){
                        lectureList.add(newList.get(j));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void GetDataDB(){
        List<Lecture> newList = db.getLectureDao().getAllLectures();
        for (int j=0; j<newList.size(); j++){
            if(newList.get(j).bookId == currentBook.id){
                lectureList.add(newList.get(j));
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void prepareLecturess(int id) {
        List<Lecture> newList = new ArrayList<>();
        Lecture a = new Lecture("True Romance", "<p>Арагорн: Здравствуй (дословно \"Добрая встреча!\")<br />Арвен: Здравствуй! Я Арвен.<br />Арагорн: Я Арагорн, человек. Ты эльфийка (эльфийская дева)?<br />Арвен: Да, я эльфийка. Я дочь Элронда.<br />Арагорн: Он владыка Ривенделла?<br />Арвен: Да. Он полуэльф. Ты из Рохиррим?<br />Арагорн: Нет. Я Дунадан.<br />Арвен: Прощай, Арагорн!<br />Арагорн: Прощай, Арвен.</p>\n" +
                "<p><br />1.2 Грамматика.</p>\n" +
                "<p><br />1.2.1 Родительный падеж.</p>\n" +
                "<p><br />Родительный падеж единственного числа часто выражается при помощи частицы en,<br />которую на английский можно примерно перевести как &laquo;of&raquo;. Он используется, если<br />&laquo;обладатель&raquo; чего-либо &ndash; &laquo;общее&raquo; существительное, например:</p>\n" +
                "<p><br />cabed en aras (leap of the deer, скачок оленя)</p>\n" +
                "<p><br />Если обладатель известен, или это имя собственное, или подразумевается<br />неопределенным, то en опускается:</p>\n" +
                "<p><br />aran Gondor (king of Gondor, король Гондора)</p>\n" +
                "<p><br />aras aran (a deer of a king, олень (какого-то) короля)</p>", 1);
        db.getLectureDao().insert(a);

        a = new Lecture("Xscpae", "<div class=\"pc pc4 w0 h0 opened\"><img class=\"bi x0 y5a w1 h10\" alt=\"\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA4sAAAB+CAIAAAACxhMDAAAACXBIWXMAABYlAAAWJQFJUiTwAAACJklEQVR42u3ZIRIAIAhFQfH+d/4Wx2iFsJvIpDewkiwAAJhBnQIAAAAAfNWbHFQBAOgM07ppuu0CAIBRFCoAAAoVAAAUKgAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAChUAABQqAAAoVAAAFCoAAChUAAAUKgAAKFQAABQqAAAoVAAAFCoAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAKBQAQBQqAAAoFABAFCoAACgUAEAUKgAAKBQAQBQqAAAoFABAEChAgCgUAEAQKECAKBQAQBAoQIAoFABAEChAgCgUAEAQKECAKBQAQBAoQIAgEIFAEChAgCAQgUAQKECAIBCBQBAoQIAgEIFAEChAgCAQgUAAIUKAIBCBQAAhQoAgEIFAACFCgCAQgUAAIUKAIBCBQAAhQoAgEIFAACFCgAAChUAAIUKAAAKFQAAhQoAAAoVAACFCgAAChUAAIUKAAAKFQAAFCoAAAoVAAAUKgAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAChUAABQqAAAoVAAAFCoAAChUAAAUKgAAKFQAABQqAAAoVAAAFCoAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAChUAABQqAAAKFQAAFCoAAAoVAAAUKgAAKBQAQBQqAAAoFABAFCoAACgUAEAUKgAAKBQAQAAAADaJbEEAADm1KkvPwAAsxzkVA/mLj9wHQAAAABJRU5ErkJggg==\"><div class=\"t m0 xf h11 y5b ff5 fs5 fc1 sc0 ls1ca ws0\">Урок<span class=\"ls1\"> <span class=\"ls1cb\">первый<span class=\"ls1cc ws2\">. </span></span></span></div><div class=\"t m0 x10 h11 y5c ff5 fs5 fc1 sc0 ls1cd ws0\">Личные<span class=\"ls1\"> <span class=\"ls1ce\">местоимения<span class=\"_ _1\"></span></span> и<span class=\"_ _1\"></span> <span class=\"_ _1\"></span><span class=\"ls143\">родительный</span> <span class=\"_ _1\"></span><span class=\"ls94\">падеж</span>.</span></div><div class=\"t m0 x0 h11 y5d ff5 fs5 fc1 sc0 ls1 ws0\">1.1 <span class=\"ls144\">Текст</span></div><div class=\"t m0 x0 h12 y5e ff7 fs9 fc1 sc0 ls1cf ws0\">tlE<span class=\"ls1\"> <span class=\"_ _2\"></span><span class=\"ls16\">xy^5P<span class=\"ls1\">#5$Б</span></span></span></div><div class=\"t m0 x0 h13 y5f ff8 fs1 fc1 sc0 lsa3 ws0\">7Ex<span class=\"ls1\">#</span>7Y5<span class=\"ls1\"> <span class=\"ff9\">  </span> <span class=\"ff7 ls1d0\">tlE<span class=\"ls1\"> <span class=\"ls7e\">xy^5P</span>#<span class=\"ls1d1\">5RБ</span></span></span></span></div><div class=\"t m0 x0 h14 y60 ff8 fs1 fc1 sc0 ls1 ws0\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span> <span class=\"ls1d2\">7Ey5</span><span class=\"ff7 ws1\">$ <span class=\"ws0\"> <span class=\"_ _2\"></span>  <span class=\"_ _2\"></span><span class=\"lsf1\">tl<span class=\"ls1 ws1\"># </span><span class=\"ls7e\">xy^5P<span class=\"ls1\">#<span class=\"ls1d1\">5RБ</span> t<span class=\"_ _2\"></span><span class=\"ws1\">% <span class=\"_ _0\"></span><span class=\"ls1d2 ws0\">7Ey5<span class=\"ls1d3\">$-</span></span></span></span></span></span></span></span></div><div class=\"t m0 x0 h14 y61 ff8 fs1 fc1 sc0 lsa3 ws0\">7Ex<span class=\"ls1\">#</span>7^5<span class=\"ls1\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span><span class=\"ff7\">  <span class=\"_ _2\"></span>t<span class=\"ws1\">% </span><span class=\"lsa3\">7Ex</span>#<span class=\"lsa3\">7^5<span class=\"ls53 ws1\">- </span></span>t<span class=\"ws1\">% <span class=\"_ _3\"></span></span>2#5<span class=\"_ _2\"></span><span class=\"ls1d3 ws1\">#- <span class=\"ls1d4 ws0\">z`V<span class=\"ls1\"> <span class=\"ls1b3\">j°</span>$<span class=\"ls1d5\">3RБ</span></span></span></span></span></span></div><div class=\"t m0 x0 h14 y62 ff8 fs1 fc1 sc0 ls1 ws0\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span><span class=\"ls1d2\">7Ey5<span class=\"ff7 ls1 ws1\">$ <span class=\"ws0\">  <span class=\"_ _2\"></span>  <span class=\"_ _2\"></span><span class=\"lsf1\">tl<span class=\"ls1d3 ws1\">#- <span class=\"_ _1\"></span></span><span class=\"ls1\">t<span class=\"ws1\">% </span><span class=\"ls1b3\">j°</span>$3R<span class=\"ls53 ws1\">- </span>t<span class=\"ws1\">% <span class=\"_ _3\"></span></span><span class=\"ls1d6\">8j°</span><span class=\"ws1\">$ </span>j$<span class=\"ls1d2\">72P^</span>-</span></span></span></span></span></div><div class=\"t m0 x0 h15 y63 ff8 fs1 fc1 sc0 lsa3 ws0\">7Ex<span class=\"ls1\">#</span>7^5<span class=\"ff7 ls1\"> <span class=\"ff5\">  </span><span class=\"ls1d3\">`V</span> <span class=\"ls1d7\">9~B6</span> t<span class=\"_ _2\"></span>%<span class=\"lsa3\">j2</span>#<span class=\"ls1d8\">7IGБ</span></span></div><div class=\"t m0 x0 h13 y64 ff9 fs1 fc1 sc0 ls1 ws0\">  <span class=\"_ _1\"></span> <span class=\"_ _1\"></span><span class=\"ff8 ls1d2\">7Ey5<span class=\"ls1 ws1\">$ </span></span><span class=\"ff7\">  <span class=\"_ _2\"></span> <span class=\"ls1d0\">tlE<span class=\"ls53 ws1\">- </span><span class=\"ls1d3\">`V</span></span> <span class=\"ls1d2\">q7R4</span>$<span class=\"ls1b3\">jR<span class=\"ls53 ws1\">- </span><span class=\"ls1d4\">z`V<span class=\"_ _0\"></span></span></span> <span class=\"ls1d9\">7`N96BБ</span></span></div><div class=\"t m0 x0 h14 y65 ff8 fs1 fc1 sc0 lsa3 ws0\">7Ex<span class=\"ls1\">#</span>7^5<span class=\"ls1\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span> <span class=\"ff7\">j.D<span class=\"ls53 ws1\">- </span>t<span class=\"ws1\">% </span><span class=\"ls1da\">2~M52</span>#5<span class=\"_ _2\"></span><span class=\"ls1d3\">#-</span></span></span></div><div class=\"t m0 x0 h14 y66 ff8 fs1 fc1 sc0 ls1 ws0\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span><span class=\"ls1d2\">7Ey5<span class=\"ff7 ls1 ws1\">$ <span class=\"ws0\">  <span class=\"_ _2\"></span><span class=\"ff8\"> <span class=\"ff7 lsa3\">zhJ`N<span class=\"ls1\"> <span class=\"_ _2\"></span><span class=\"ls1d0\">rlE<span class=\"ls53 ws1\">= <span class=\"_ _1\"></span></span><span class=\"lsa3\">7Ex<span class=\"ls1\">#<span class=\"ls1db\">7^5Б</span></span></span></span></span></span></span></span></span></span></div><div class=\"t m0 x0 h14 y67 ff8 fs1 fc1 sc0 lsa3 ws0\">7Ex<span class=\"ls1\">#</span>7^5<span class=\"ff7 ls1\">  <span class=\"_ _2\"></span> <span class=\"_ _2\"></span><span class=\"lsa3\">zhJ`N<span class=\"ls1\"> <span class=\"ls1d0\">rlE<span class=\"ls53 ws1\">= </span><span class=\"ls1dc\">7Ey5Б</span></span>$</span></span></span></div><div class=\"t m0 x0 h11 y68 ff5 fs5 fc1 sc0 ls1dd ws0\">Mae<span class=\"ls1\"> <span class=\"ls1de\">govannen<span class=\"_ _1\"></span></span>!</span></div><div class=\"t m0 x0 he y69 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 lsb9\">Mae<span class=\"ls1\"> <span class=\"lsb0\">govannen</span>!</span></span></span></div><div class=\"t m0 x0 he y6a ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 lsb9\">Mae<span class=\"ls1\"> <span class=\"lsb0\">govannen<span class=\"ls41\">! <span class=\"_ _1\"></span><span class=\"ls1e1\">Im</span></span></span> <span class=\"ls1e2\">Arwen</span>.</span></span></span></div><div class=\"t m0 x0 he y6b ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1e1\">Im<span class=\"ls1\"> <span class=\"ls1e3\">Aragorn</span>. </span>Im<span class=\"ls1\"> <span class=\"ls1e4\">adan</span>. <span class=\"ls5a\">Ce</span> <span class=\"ls1e5\">elleth</span>?</span></span></span></div><div class=\"t m0 x0 he y6c ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 lsb9\">Mae<span class=\"ls1\">. <span class=\"ls1e1\">Im</span> <span class=\"ls1e5\">elleth</span>. <span class=\"ls1e1\">Im</span> <span class=\"ls1e6\">sell</span> <span class=\"ls1e7\">Elrond</span>.</span></span></span></div><div class=\"t m0 x0 he y6d ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1\">E<span class=\"_ _2\"></span> <span class=\"ls1e8\">hнr</span> <span class=\"ls1e9\">Imladris</span>?</span></span></div><div class=\"t m0 x0 he y7 ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 lsb9\">Mae<span class=\"ls1\">. E<span class=\"_ _2\"></span> <span class=\"ls1ea\">peredhel</span>. <span class=\"ls5a\">Ce</span> <span class=\"ls1eb\">Rohir</span>?</span></span></span></div><div class=\"t m0 x0 he y6e ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1ec\">Law<span class=\"ls1\">. <span class=\"ls1e1\">Im</span> <span class=\"ls1ed\">Dъnadan</span>.</span></span></span></div><div class=\"t m0 x0 he y6f ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 ls1ee\">Cuio<span class=\"ls1\"> <span class=\"ls153\">vae</span>, <span class=\"ls1e3\">Aragorn</span>!</span></span></span></div><div class=\"t m0 x0 he y0 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1ee\">Cuio<span class=\"ls1\"> <span class=\"ls153\">vae</span>, <span class=\"ls1e2\">Arwen</span>!</span></span></span></div><div class=\"t m0 x0 h11 y70 ff5 fs5 fc1 sc0 ls1ef ws0\">Well<span class=\"ls1\"> <span class=\"lsaf\">met</span>!</span></div><div class=\"t m0 x0 he y71 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1f0\">Well<span class=\"ls1\"> <span class=\"ls1f1\">met</span>!</span></span></span></div><div class=\"t m0 x0 he y72 ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 ls1f0\">Well<span class=\"ls1\"> <span class=\"ls1f1\">met<span class=\"ls41\">! <span class=\"_ _1\"></span></span></span>I'm<span class=\"_ _16\"></span> <span class=\"ls1e2\">Arwen</span>.</span></span></span></div><div class=\"t m0 x0 he y73 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1\">I'm<span class=\"_ _16\"></span> <span class=\"ls1e3\">Aragorn</span>, a <span class=\"ls1f2\">human</span>. <span class=\"ls1f3\">Are</span> <span class=\"ls1f4\">you</span> <span class=\"ls1f5\">an</span> <span class=\"ls1f6\">elven</span>-<span class=\"_ _1\"></span><span class=\"ls1f7\">maiden</span>?</span></span></div><div class=\"t m0 x0 he y74 ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 ls1f8\">Yes<span class=\"ls1\">, I <span class=\"ls1f0\">am</span> <span class=\"ls1f5\">an</span> <span class=\"ls1f6\">elven</span>-<span class=\"_ _3\"></span><span class=\"ls1f7\">maiden</span>. I<span class=\"_ _2\"></span> <span class=\"ls1f0\">am</span> <span class=\"ls1e7\">Elrond</span>'<span class=\"_ _1\"></span>s <span class=\"ls1f9\">daughter</span>.</span></span></span></div><div class=\"t m0 x0 he y75 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls19d\">He<span class=\"ls1\"> <span class=\"ls1fa\">is</span> <span class=\"ls1fb\">the</span> <span class=\"ls1fc\">lord</span> <span class=\"ls1fd\">of</span> <span class=\"ls1fe\">Rivendell</span>?</span></span></span></div><div class=\"t m0 x0 he y76 ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 ls1f8\">Yes<span class=\"ls1\">. <span class=\"ls19d\">He</span> <span class=\"ls1fa\">is</span> a <span class=\"ls1ff\">half</span>-<span class=\"_ _3\"></span><span class=\"ls200\">elf</span>. <span class=\"ls1f3\">Are</span> <span class=\"ls1f4\">you</span> <span class=\"ls201\">from<span class=\"ls1\"> <span class=\"ls1fb\">the</span> <span class=\"ls202\">Rohirrim</span>?</span></span></span></span></span></div><div class=\"t m0 x0 he y77 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls1b8\">No<span class=\"ls1\">. I <span class=\"ls1f0\">am</span> a <span class=\"ls1ed\">Dъnadan</span>.</span></span></span></div><div class=\"t m0 x0 he y78 ff9 fs8 fc1 sc0 ls1e0 ws0\">Arwen<span class=\"ls41\">: <span class=\"ff5 ls203\">Farewell<span class=\"ls1\">, <span class=\"ls1e3\">Aragorn</span>!</span></span></span></div><div class=\"t m0 x0 he y79 ff9 fs8 fc1 sc0 ls1df ws0\">Aragorn<span class=\"ls41\">: <span class=\"ff5 ls203\">Farewell<span class=\"ls1\">, <span class=\"ls1e2\">Arwen</span>.</span></span></span></div><div class=\"t m0 x0 ha yd ff3 fs7 fc1 sc0 ls1 ws0\">4</div></div>", 1);
        db.getLectureDao().insert(a);

        a = new Lecture("Maroon 5", "11", 2);
        db.getLectureDao().insert(a);

        a = new Lecture("Born to Die", "12", 3);
        db.getLectureDao().insert(a);

        a = new Lecture("Honeymoon", "14", 4);
        db.getLectureDao().insert(a);

        a = new Lecture("I Need a Doctor", "1", 5);
        db.getLectureDao().insert(a);

        a = new Lecture("Loud", "11", 6);
        db.getLectureDao().insert(a);

        a = new Lecture("Legend", "14", 7);
        db.getLectureDao().insert(a);

        a = new Lecture("Hello", "11", 8);
        db.getLectureDao().insert(a);

        a = new Lecture("Greatest Hits", "17", 9);
        db.getLectureDao().insert(a);

        newList = db.getLectureDao().getAllLectures();
        for (int i=0; i<newList.size(); i++){
            if(newList.get(i).bookId == id){
                lectureList.add(newList.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }
}
