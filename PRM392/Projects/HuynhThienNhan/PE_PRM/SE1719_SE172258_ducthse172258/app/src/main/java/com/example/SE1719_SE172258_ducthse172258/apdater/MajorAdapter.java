package com.example.SE1719_SE172258_ducthse172258.apdater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.SE1719_SE172258_ducthse172258.MajorUpdateActivity;
import com.example.SE1719_SE172258_ducthse172258.R;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorService;
import com.example.SE1719_SE172258_ducthse172258.models.Major;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MajorAdapter extends BaseAdapter {
    Context context;
    List<Major> majorList;
    MajorService majorService;

    public MajorAdapter(Context context, List<Major> majorList) {
        this.context = context;
        this.majorList = majorList;
    }

    @Override
    public int getCount() {
        return majorList.size();
    }

    @Override
    public Object getItem(int position) {
        return majorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return majorList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_major_list, parent, false);
        }
        Major major = majorList.get(position);
        TextView id = convertView.findViewById(R.id.tv_ID_major);
        TextView name = convertView.findViewById(R.id.tv_name_major);
        // edit delete
        AppCompatButton edit_btn = convertView.findViewById(R.id.edit_student);
        AppCompatButton delete_btn = convertView.findViewById(R.id.delete_student);
        // set data
        id.setText(major.getID() + "");
        name.setText(major.getName_Major());
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDeleteConfirm(major);
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateEdit(major);
            }
        });


        return convertView;
    }

    private void navigateEdit(Major major) {
        Intent intent = new Intent(context, MajorUpdateActivity.class);
        intent.putExtra("ID", major.getID() + "");
        intent.putExtra("Name", major.getName_Major());
        context.startActivity(intent);
    }

    private void showDialogDeleteConfirm(Major major) {
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
                deleteMajor(major.getID(), alertDialog);
            }
        });
        // Show the dialog
        alertDialog.show();
    }

    private void deleteMajor(long id, AlertDialog alertDialog) {
        majorService = MajorRepository.getMajorService();
        Call<Major> call = majorService.deleteMajor(id);
        call.enqueue(new Callback<Major>() {
            @Override
            public void onResponse(Call<Major> call, Response<Major> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                    removeStudentFromList(id);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();

                }
                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Major> call, Throwable throwable) {
                Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void removeStudentFromList(long studentID) {
        for (int i = 0; i < majorList.size(); i++) {
            if (majorList.get(i).getID() == studentID) {
                majorList.remove(i);
                break;
            }
        }
    }
}
