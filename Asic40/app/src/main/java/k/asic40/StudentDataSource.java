package k.asic40;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {

    private SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;

    public StudentDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    private void open() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    private void close() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    public boolean addStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("email", student.getEmail());
        contentValues.put("tanggal", student.getTanggal());

        open();
        long status = sqLiteDatabase.insert("student", null, contentValues);
        close();

        return status > 0;
    }

    public boolean editStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("email", student.getEmail());
        contentValues.put("tanggal", student.getTanggal());

        open();
        long status = sqLiteDatabase.update("student", contentValues, "id="+student.getId(),null);
        close();

        return status > 0;
    }

    private Student fetchToPojo(Cursor cursor) {

        Student student = new Student();

        student.setId(cursor.getInt(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNoHp(cursor.getString(3));
        student.setJenjang(cursor.getString(4));
        student.setGender(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));
        student.setEmail(cursor.getString(8));
        student.setTanggal(cursor.getString(9));

        return student;
    }

    public List<Student> getAllStudent() {
        String query = " SELECT * FROM student";
        open();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        close();
        cursor.moveToFirst();

        List<Student> studentList = new ArrayList<>();

        /*for (int i=0;i<cursor.getCount();i++){
            Student student=fetchToPojo(cursor);

            studentList.add(student);

            cursor.moveToNext();
        }*/

        while (!cursor.isAfterLast()) {
            Student student = fetchToPojo(cursor);

            studentList.add(student);

            cursor.moveToNext();
        }

        return studentList;
    }

    public List<Student> getAllStudentSearch(String keyword) {
        String query = " SELECT * FROM student WHERE nama_depan LIKE ? OR nama_belakang LIKE ?";
        open();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{"%"+keyword+"%","%"+keyword+"%"});
        close();
        cursor.moveToFirst();

        List<Student> studentList = new ArrayList<>();

        /*for (int i=0;i<cursor.getCount();i++){
            Student student=fetchToPojo(cursor);

            studentList.add(student);

            cursor.moveToNext();
        }*/

        while (!cursor.isAfterLast()) {
            Student student = fetchToPojo(cursor);

            studentList.add(student);

            cursor.moveToNext();
        }

        return studentList;
    }

    public Student getStudent(int id) {
        String query = "SELECT * FROM student WHERE id=" + id;
        open();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        return fetchToPojo(cursor);
    }

    public void deleteStudent(int id){
        open();
        sqLiteDatabase.delete("student","id="+id,null);
        close();
    }
}