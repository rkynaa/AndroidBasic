package k.asic40;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentItemAdapter extends ArrayAdapter {

    List<Student> studentList;
    LayoutInflater layoutInflater;

    public StudentItemAdapter(Context context, List<Student> students){
        super(context,R.layout.student_item,students);
        studentList=students;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Nullable
    @Override
    public Student getItem(int position) {
        return studentList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view=layoutInflater.inflate(R.layout.student_item,null);

        TextView tvNama=view.findViewById(R.id.tv_nama);
        TextView tvGender=view.findViewById(R.id.tv_gender);
        TextView tvJenjang=view.findViewById(R.id.tv_jenjang);
        TextView tvNoHp=view.findViewById(R.id.tv_no_hp);

        Student student=getItem(position);

        tvNama.setText(student.getNamaDepan()+" "+student.getNamaBelakang());
        tvGender.setText(student.getGender());
        tvJenjang.setText(student.getJenjang());
        tvNoHp.setText(student.getNoHp());

        return view;
    }
}