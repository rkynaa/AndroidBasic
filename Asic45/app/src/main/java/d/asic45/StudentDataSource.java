package d.asic45;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {

    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public StudentDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    private void openDb() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    private void closeDb() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    public boolean addStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("tanggal", student.getTanggal());
        openDb();
        long statusInput = sqLiteDatabase.insert("student", null, contentValues);
        closeDb();
        return statusInput > 0;
    }

    private Student fetchToPojo(Cursor cursor) {
        Student student = new Student();
        student.setId(cursor.getLong(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNoHp(cursor.getString(3));
        student.setGender(cursor.getString(4));
        student.setJenjang(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));
        student.setTanggal(cursor.getString(8));
        return student;
    }


    public List<Student> getAllStudent() {
        String query = "SELECT * FROM student";
        openDb();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        closeDb();
        cursor.moveToFirst();
        List<Student> studentList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            Student student = fetchToPojo(cursor);
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }


    public Student getStudent(long id) {
        String query = "SELECT * FROM student WHERE id=" + id;
        openDb();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        return fetchToPojo(cursor);
    }

}