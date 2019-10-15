package d.asic45;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    StudentDataSource studentDataSource;
    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        linkToLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        studentDataSource = new StudentDataSource(this);
        studentList = studentDataSource.getAllStudent();

        String name[] = new String[studentList.size()];
        int i = 0;
        while (i < name.length) {
            name[i] = studentList.get(i).getNamaDepan();
            i++;
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, name);

        StudentItemAdapter studentItemAdapter = new StudentItemAdapter(this, studentList);


        listView.setAdapter(studentItemAdapter);

    }

    private void linkToLayout() {
        listView = findViewById(R.id.lv_student);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == R.id.form) {
            startActivity(new Intent(this, FormActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("student_id", studentList.get(i).getId());
        startActivity(intent);
    }
}