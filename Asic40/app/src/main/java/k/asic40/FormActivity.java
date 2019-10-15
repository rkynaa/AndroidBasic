package k.asic40;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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


    EditText etNamaDepan, etNamaBelakang, etNoHp, etAlamat, etEmail, etTanggal;
    RadioGroup rgGender;
    RadioButton rbPria, rbWanita;
    Spinner spJenjang;
    CheckBox cbMembaca, cbMenulis, cbMenggambar;
    Button btnSimpan;

    Perhitungan perhitungan;

    StudentDataSource studentDataSource;
    Student student;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        linkToLayout();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentDataSource = new StudentDataSource(this);

        studentId = getIntent().getIntExtra("id", 0);

        if (studentId > 0) {

            Student studentDetail = studentDataSource.getStudent(studentId);

            etNamaDepan.setText(studentDetail.getNamaDepan());
            etNamaBelakang.setText(studentDetail.getNamaBelakang());
            etNoHp.setText(studentDetail.getNoHp());
            etEmail.setText(studentDetail.getEmail());
            etTanggal.setText(studentDetail.getTanggal());
            etAlamat.setText(studentDetail.getAlamat());

            if (studentDetail.getGender().equals("Pria")) {
                rbPria.setChecked(true);
            } else {
                rbWanita.setChecked(true);
            }

            if (studentDetail.getJenjang().equals("SD")) {
                spJenjang.setSelection(0);
            } else if (studentDetail.getJenjang().equals("SMP")) {
                spJenjang.setSelection(1);
            } else {
                spJenjang.setSelection(2);
            }


            String splitHobi[] = studentDetail.getHobi().split(",");

            for (int i = 0; i < splitHobi.length; i++) {
                if (splitHobi[i].equals("Membaca")) {
                    cbMembaca.setChecked(true);
                } else if (splitHobi[i].equals("Menulis")) {
                    cbMenulis.setChecked(true);
                } else {
                    cbMenggambar.setChecked(true);
                }
            }

        }

        perhitungan = new Perhitungan();

        Log.d("Pesan", " a + b = " + perhitungan.penambahan(2, 5));

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText
                String namaDepan = etNamaDepan.getText().toString();
                String namaBelakang = etNamaBelakang.getText().toString();
                String noHp = etNoHp.getText().toString();
                String alamat = etAlamat.getText().toString();
                String email = etEmail.getText().toString();
                String tanggal = etTanggal.getText().toString();


                //Spinner
                String jenjang = spJenjang.getSelectedItem().toString();

                //RadioGroup
                int radioId = rgGender.getCheckedRadioButtonId();
                String gender;

                switch (radioId) {
                    case R.id.rb_pria:
                        gender = "Pria";
                        break;
                    default:
                        gender = "Wanita";
                        break;
                }

                //Checkbox
                List<String> hobi = new ArrayList<>();

                if (cbMembaca.isChecked()) {
                    hobi.add("Membaca");
                }
                if (cbMenulis.isChecked()) {
                    hobi.add("Menulis");
                }
                if (cbMenggambar.isChecked()) {
                    hobi.add("Menggambar");
                }

                String gabungHobi = TextUtils.join(",", hobi);

                String pesan = "1. Nama Depan : " + namaDepan;
                pesan = pesan + "\n2. Nama Belakang : " + namaBelakang;
                pesan = pesan + "\n3. No Hp : " + noHp;
                pesan = pesan + "\n4. Gender : " + gender;
                pesan = pesan + "\n5. Jenjang : " + jenjang;
                pesan = pesan + "\n6. Hobi : " + gabungHobi;
                pesan = pesan + "\n7. Alamat : " + alamat;

                if (namaDepan.isEmpty()) {
                    etNamaDepan.setError("Nama Depan Kosong !!");
                    return;
                }

                if (namaBelakang.isEmpty()) {
                    etNamaBelakang.setError("Nama Belakang Kosong !!");
                    return;
                }

                if (noHp.isEmpty()) {
                    etNoHp.setError("No Hp Kosong !!");
                    return;
                }

                if (alamat.isEmpty()) {
                    etAlamat.setError("Alamat Kosong !!");
                    return;
                }


                student = new Student();

                student.setNamaDepan(namaDepan);
                student.setNamaBelakang(namaBelakang);
                student.setNoHp(noHp);
                student.setGender(gender);
                student.setHobi(gabungHobi);
                student.setJenjang(jenjang);
                student.setAlamat(alamat);
                student.setEmail(email);
                student.setTanggal(tanggal);

                boolean statusInput;

                if (studentId>0){
                    student.setId(studentId);
                    statusInput=studentDataSource.editStudent(student);
                }else{
                    statusInput = studentDataSource.addStudent(student);
                }


                if (statusInput) {
                    finish();
                    showToast("berhasil input");
                }


            }
        });

    }

    private void showToast(String pesan) {
        Toast.makeText(this, pesan, Toast.LENGTH_LONG).show();
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
        etEmail = findViewById(R.id.et_email);
        etTanggal = findViewById(R.id.et_tanggal);

    }

    private void showDate() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etTanggal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuId=item.getItemId();

        switch (menuId){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}