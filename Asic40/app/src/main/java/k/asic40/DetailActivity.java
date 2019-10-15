package k.asic40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvNama, tvNoHp, tvEmail, tvTanggal, tvGender, tvJenjang, tvAlamat, tvHobi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        linkToLayout();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int studentId = getIntent().getIntExtra("id", 0);

        StudentDataSource studentDataSource = new StudentDataSource(this);
        Student student = studentDataSource.getStudent(studentId);

        tvNama.setText(student.getNamaDepan() + " " + student.getNamaBelakang());
        tvNoHp.setText(student.getNoHp());
        tvEmail.setText(student.getEmail());
        tvTanggal.setText(student.getTanggal());
        tvGender.setText(student.getGender());
        tvJenjang.setText(student.getJenjang());
        tvAlamat.setText(student.getAlamat());
        tvHobi.setText(student.getHobi());
    }

    private void linkToLayout() {
        tvNama = findViewById(R.id.tv_nama);
        tvNoHp = findViewById(R.id.tv_no_hp);
        tvEmail = findViewById(R.id.tv_email);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvGender = findViewById(R.id.tv_gender);
        tvJenjang = findViewById(R.id.tv_jenjang);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvHobi = findViewById(R.id.tv_hobi);
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
