package com.example.SE1719_SE172258_ducthse172258.apdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.SE1719_SE172258_ducthse172258.R;
import com.example.SE1719_SE172258_ducthse172258.UpdateActivity;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorService;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentService;
import com.example.SE1719_SE172258_ducthse172258.models.Major;
import com.example.SE1719_SE172258_ducthse172258.models.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends BaseAdapter {
    Context context;
    List<Student> studentList;
    StudentService studentService;
    MajorService majorService;
    Map<Long, String> majorMap = new HashMap<>();

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        fetchMajors();

    }

    private void fetchMajors() {
        majorService = MajorRepository.getMajorService();
        Call<Major[]> call = majorService.getAllMajor();
        call.enqueue(new Callback<Major[]>() {
            @Override
            public void onResponse(Call<Major[]> call, Response<Major[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Major major : response.body()) {
                        majorMap.put(major.getID(), major.getName_Major());
                        Log.d("MajorID", String.valueOf(major.getID()));
                        Log.d("MajorName", String.valueOf(major.getName_Major()));
                    }
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Failed to fetch majors", Toast.LENGTH_SHORT).show();
                    Log.e("fetch major err", response.message());
                }

            }

            @Override
            public void onFailure(Call<Major[]> call, Throwable throwable) {
                Toast.makeText(context, "Failed to fetch majors", Toast.LENGTH_SHORT).show();
                Log.e("fetch major err", throwable.toString());

            }
        });

    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return studentList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_student_list, parent, false);
        }
        Student student = studentList.get(position);
        TextView id = convertView.findViewById(R.id.tv_ID);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvDate = convertView.findViewById(R.id.tv_date);
        TextView tvGender = convertView.findViewById(R.id.tv_Gender);
        TextView tvEmail = convertView.findViewById(R.id.tv_email);
        TextView tvAddress = convertView.findViewById(R.id.tv_address);
        TextView tvMajor = convertView.findViewById(R.id.tv_major);

        // edit delete
        AppCompatButton edit_btn = convertView.findViewById(R.id.edit_student);
        AppCompatButton delete_btn = convertView.findViewById(R.id.delete_student);

        // set data
        id.setText(student.getID() + "");
        tvName.setText(student.getName());
        tvDate.setText(student.getDate());
        tvGender.setText(student.getGender());
        tvEmail.setText(student.getEmail());
        tvAddress.setText(student.getAddress());

//        if (student.getId_major() == 1) {
//            tvMajor.setText("Kỹ Thuật Phần Mềm2");
//        } else if (student.getId_major() == 2) {
//            tvMajor.setText("Truyền thông");
//        } else if (student.getId_major() == 3) {
//            tvMajor.setText("Kinh Tế");
//        } else if (student.getId_major() == 4) {
//            tvMajor.setText("Ngôn ngữ nhật");
//        } else if (student.getId_major() == 5) {
//            tvMajor.setText("Kinh Doanh");
//        } else {
//            tvMajor.setText("An toàn thông tin");
//        }
        // fetch Majorr
        Long majorId = student.getId_major();
        String majorName = majorMap.get(majorId);
        if (majorName != null) {
            tvMajor.setText(majorName);
        } else {
            tvMajor.setText("Công nghệ thông tin");
        }

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDeleteConfirm(student);
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateEdit(student);
            }
        });
        return convertView;
    }

    private void navigateEdit(Student student) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("ID", student.getID() + "");
        intent.putExtra("Name", student.getName());
        intent.putExtra("Email", student.getEmail());
        intent.putExtra("Address", student.getAddress());
        intent.putExtra("Gender", student.getGender());
        intent.putExtra("Date", student.getDate());
        intent.putExtra("Major", String.valueOf(student.getId_major()));
        context.startActivity(intent);
    }

    private void showDialogDeleteConfirm(Student student) {
        // init dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm_delete, null);
        builder.setView(dialogView);
        // init dialog
        AppCompatButton btnCancel = dialogView.findViewById(R.id.btn_cancel_order);
        AppCompatButton btnConfirm = dialogView.findViewById(R.id.btn_complete_order);
        // create Dialog
        AlertDialog alertDialog = builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(student.getID(), alertDialog);
            }
        });
        // Show the dialog
        alertDialog.show();

    }

    private void deleteStudent(long studentID, AlertDialog alertDialog) {
        studentService = StudentRepository.getStudentService();
        Call<Student> call = studentService.deleteStudent(studentID);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                    removeStudentFromList(studentID);
                    notifyDataSetChanged();

                } else {
                    Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();

                }
                alertDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Student> call, Throwable throwable) {
                Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void removeStudentFromList(long studentID) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getID() == studentID) {
                studentList.remove(i);
                break;
            }
        }
    }

}
