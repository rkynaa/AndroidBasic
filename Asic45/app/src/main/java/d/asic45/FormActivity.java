package d.asic45;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etNamaDepan, etNamaBelakang, etNoHp, etAlamat, etTanggal;
    RadioGroup rgGender;
    RadioButton rbPria, rbWanita;
    Spinner spJenjang;
    CheckBox cbMembaca, cbMenulis, cbMenggambar;
    Button btnSimpan;
    StudentDataSource studentDataSource;
    Student student;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        linkToLayout();

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namaDepan = etNamaDepan.getText().toString();
                String namaBelakang = etNamaBelakang.getText().toString();
                String noHp = etNoHp.getText().toString();
                String alamat = etAlamat.getText().toString();
                String jenjang = spJenjang.getSelectedItem().toString();
                String tanggal = etTanggal.getText().toString();

                String gender = "";

                int radioId = rgGender.getCheckedRadioButtonId();

                if (radioId == R.id.rb_pria) {
                    gender = "Pria";
                } else {
                    gender = "Wanita";
                }

                List<String> hobi = new ArrayList<>();

                if (cbMenulis.isChecked()) {
                    hobi.add("Menulis");
                }
                if (cbMembaca.isChecked()) {
                    hobi.add("Membaca");
                }
                if (cbMenggambar.isChecked()) {
                    hobi.add("Menggambar");
                }

                String gabungHobi = TextUtils.join(",", hobi);

                String message = "1. Nama Depan: " + namaDepan;
                message = message + "\n2. Nama Belakang: " + namaBelakang;
                message = message + "\n3. No Hp: " + noHp;
                message = message + "\n4. Hobi: " + gabungHobi;
                message = message + "\n5. Gender: " + gender;
                message = message + "\n7. Jenjang: " + jenjang;
                message = message + "\n8. Alamat: " + alamat;

                if (namaDepan.isEmpty()) {
                    etNamaDepan.setError("Nama Depan Kosong!!");
                    return;
                }
                if (namaBelakang.isEmpty()) {
                    etNamaBelakang.setError("Nama Belakang Kosong!!");
                    return;
                }

                studentDataSource = new StudentDataSource(getApplicationContext());
                student = new Student();

                student.setNamaDepan(namaDepan);
                student.setNamaBelakang(namaBelakang);
                student.setGender(gender);
                student.setHobi(gabungHobi);
                student.setNoHp(noHp);
                student.setJenjang(jenjang);
                student.setAlamat(alamat);
                student.setTanggal(tanggal);

                boolean statusInput;

                statusInput = studentDataSource.addStudent(student);

                if (statusInput) {
                    finish();
                    showToast("Berhasil Input Data");
                }


            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void linkToLayout() {
        etNamaDepan = findViewById(R.id.et_nama_depan);
        etNamaBelakang = findViewById(R.id.et_nama_belakang);
        etNoHp = findViewById(R.id.et_no_hp);
        etAlamat = findViewById(R.id.et_alamat);
        rgGender = findViewById(R.id.rg_gender);
        rbPria = findViewById(R.id.rb_pria);
        rbWanita = findViewById(R.id.rb_wanita);
        spJenjang = findViewById(R.id.sp_jenjang);
        cbMembaca = findViewById(R.id.cb_membaca);
        cbMenulis = findViewById(R.id.cb_menulis);
        cbMenggambar = findViewById(R.id.cb_menggambar);
        btnSimpan = findViewById(R.id.btn_simpan);
        etTanggal = findViewById(R.id.et_tanggal);
    }

    private void showDate() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        etTanggal.setText(day + "/" + (month + 1) + "/" + year);
    }

}