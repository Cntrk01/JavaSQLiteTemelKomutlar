package com.example.sanatkitabmsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1)Veritabanı ile çalışırken mutlaka trycatch kullan.Olası çökme hatalara karşı try-catch güvenlidir.2.1 de tablo oluştururken orda hatalıu yazdık diyelim.
        //trycatch bize uyarısını verir.Eger try olmasa hata olmadan çalışırdı.Anlamazdık sorna veri getirmek isteyince getiremezdik
        try{
            //2)Önce veritabanını oluşturduk. openOrCreate varsa açıyor yoksa oluşturduk
            SQLiteDatabase db=this.openOrCreateDatabase("Musician",MODE_PRIVATE,null);
            //2.1)Tablo oluşturuyoruz burda.Bir tablo oluştur eger yoksa. existsden sonraki tablo isimi.istediğimizi verebiliriz.Onun içinede tablo içinde olcak değişlenleri giriyoruz
            db.execSQL("CREATE TABLE IF NOT EXISTS musician(id INTEGER PRIMARY KEY,name VARCHAR,age INTEGER)");
           //db.execSQL("UPDATE musicians SET age=61 WHERE name='Cantürk'"); ismi cantürk olan kiişinin yaşını 61 yaptı
            //db.execSQL("DELETE FROM musicians where id=2");
            //3.1)Eklenen verimizi cursor aracılığıyla okuyalım.rawQuery() içine yazılacak sql sorgusu bize veriyi getirecek.selectionArgs filtreleme işllemi icin
            Cursor cursor=db.rawQuery("SELECT * FROM musician",null);
            //3.2)Burada sütunlarda olan değişkenlere gidiyoruz. getColumnIndex("name"); bize name nin kaçıncı indexte olduğunu vericek
            int nameIx=cursor.getColumnIndex("name");
            int ageIx=cursor.getColumnIndex("age");
            //3.3)cursor burda en sona kadar gidicek bitene kadar bizde while içindeki sorgularımızı ccalıstırıcaz.
            while(cursor.moveToNext()){
                System.out.println("Name : "+cursor.getString(nameIx));
                System.out.println("Age : "+cursor.getInt(ageIx));
            }
            //işlem bitince cursoru kapatmayı unutma !!!
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}