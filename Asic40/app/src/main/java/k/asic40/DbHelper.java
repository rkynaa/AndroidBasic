package k.asic40;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context,"sekolahku.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE student (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nama_depan TEXT," +
                "nama_belakang TEXT," +
                "no_hp TEXT," +
                "jenjang TEXT," +
                "gender TEXT," +
                "hobi TEXT," +
                "alamat TEXT," +
                "email Text," +
                "tanggal TEXT)";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}