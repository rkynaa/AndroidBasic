package k.asic40;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Student> studentList;
    StudentItemAdapter studentItemAdapter;
    StudentDataSource studentDataSource;
    ListView lvStudent;
    SearchView svStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        linkToLayout();
        setTitle(Html.fromHtml("<font color='#000'> mmm </font>"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int itemPosition=adapterContextMenuInfo.position;

        int studentId=studentList.get(itemPosition).getId();

        switch (menuId) {
            case R.id.edit_action:

                Intent intent = new Intent(this, FormActivity.class);
                intent.putExtra("id", studentId);
                startActivity(intent);
                break;
            default:
                studentDataSource.deleteStudent(studentId);
                studentList.clear();
                List<Student> studentListSearch = studentDataSource.getAllStudent();
                studentList.addAll(studentListSearch);
                studentItemAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuId = item.getItemId();

        switch (menuId) {
            case R.id.form_action:
                startActivity(new Intent(this, FormActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        studentDataSource = new StudentDataSource(this);
        studentList = studentDataSource.getAllStudent();

        studentItemAdapter = new StudentItemAdapter(this, studentList);
        lvStudent.setAdapter(studentItemAdapter);

        svStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                studentList.clear();
                List<Student> studentListSearch = studentDataSource.getAllStudentSearch(newText);
                studentList.addAll(studentListSearch);
                studentItemAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void linkToLayout() {
        lvStudent = findViewById(R.id.lv_student);
        lvStudent.setOnItemClickListener(this);
        svStudent = findViewById(R.id.sv_student);
        registerForContextMenu(lvStudent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int studentId = studentList.get(position).getId();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", studentId);
        startActivity(intent);
    }
}
