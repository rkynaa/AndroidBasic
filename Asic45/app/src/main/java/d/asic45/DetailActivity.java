package d.asic45;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    TextView tvNama, tvNoHp, tvTanggal, tvGender, tvJenjang, tvHobi, tvAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        linkToLayout();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long studentId=getIntent().getLongExtra("student_id",0);

        StudentDataSource studentDataSource=new StudentDataSource(this);
        Student student=studentDataSource.getStudent(studentId);

        tvNama.setText(student.getNamaDepan()+" "+student.getNamaBelakang());
        tvNoHp.setText(student.getNoHp());
        tvTanggal.setText(student.getTanggal());
        tvHobi.setText(student.getHobi());
        tvAlamat.setText(student.getAlamat());
        tvJenjang.setText(student.getJenjang());
        tvGender.setText(student.getGender());

    }

    private void linkToLayout() {
        tvNama=findViewById(R.id.tv_nama);
        tvNoHp=findViewById(R.id.tv_no_hp);
        tvTanggal=findViewById(R.id.tv_tanggal);
        tvGender=findViewById(R.id.tv_gender);
        tvJenjang=findViewById(R.id.tv_jenjang);
        tvHobi=findViewById(R.id.tv_hobi);
        tvAlamat=findViewById(R.id.tv_alamat);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == android.R.id.home) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}