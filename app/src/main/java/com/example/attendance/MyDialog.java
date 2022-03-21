package com.example.attendance;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";


    OnClickLister onClickLister;
    private String roll;
    private String name;



    public MyDialog(String roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    public MyDialog() {

    }

    public interface  OnClickLister{
        void onClick(String text1,String text2);
    }

    public void setListener(OnClickLister onClickLister){
        this.onClickLister = onClickLister;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if (getTag().equals(CLASS_ADD_DIALOG))dialog = getAddClassDialog();
        if (getTag().equals(STUDENT_ADD_DIALOG))dialog = getAddStudentDialog();
        if (getTag().equals(CLASS_UPDATE_DIALOG))dialog = getUpdateDialog();
        if (getTag().equals(STUDENT_UPDATE_DIALOG))dialog = getStudentUpdateDialog();
        return dialog;
    }

    private Dialog getStudentUpdateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Update Student");

        EditText rollEt = view.findViewById(R.id.class_name_text);
        EditText nameEt = view.findViewById(R.id.subject_name_text);

        rollEt.setText(roll);
        rollEt.setEnabled(false);
        nameEt.setText(name);


        rollEt.setInputType(InputType.TYPE_CLASS_NUMBER);


        Button cancelBtn = view.findViewById(R.id.cancel_Btn);
        Button okBtn = view.findViewById(R.id.ok_button);

        okBtn.setText("Update");

        cancelBtn.setOnClickListener(v -> dismiss());

        okBtn.setOnClickListener(v -> {
            String roll = rollEt.getText().toString();
            String name = nameEt.getText().toString();

            onClickLister.onClick(roll,name);
            dismiss();

        });
        return builder.create();
    }

    private Dialog getUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Update Class");

        EditText classNameEt = view.findViewById(R.id.class_name_text);
        EditText subjectNameEt = view.findViewById(R.id.subject_name_text);

        classNameEt.setHint("Class Name");
        subjectNameEt.setHint("Subject Name");

        Button cancelBtn = view.findViewById(R.id.cancel_Btn);
        Button okBtn = view.findViewById(R.id.ok_button);
        okBtn.setText("Update");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameEt.getText().toString();
                String subjectName = subjectNameEt.getText().toString();

                onClickLister.onClick(className,subjectName);
                dismiss();

            }
        });
        return builder.create();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Add New Student");

        EditText rollEt = view.findViewById(R.id.class_name_text);
        EditText nameEt = view.findViewById(R.id.subject_name_text);

        rollEt.setHint("Roll");
        nameEt.setHint("Name");

        rollEt.setInputType(InputType.TYPE_CLASS_NUMBER);


        Button cancelBtn = view.findViewById(R.id.cancel_Btn);
        Button okBtn = view.findViewById(R.id.ok_button);

        cancelBtn.setOnClickListener(v -> dismiss());

        okBtn.setOnClickListener(v -> {
            String roll = rollEt.getText().toString();
            String name = nameEt.getText().toString();
            rollEt.setText(String.valueOf(Integer.parseInt(roll)+1));
            nameEt.setText("");

            onClickLister.onClick(roll,name);


        });
        return builder.create();
    }

    private Dialog getAddClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.title_dialog);
        title.setText("Add New Class");

        EditText classNameEt = view.findViewById(R.id.class_name_text);
        EditText subjectNameEt = view.findViewById(R.id.subject_name_text);

        classNameEt.setHint("Class Name");
        subjectNameEt.setHint("Subject Name");

        Button cancelBtn = view.findViewById(R.id.cancel_Btn);
        Button okBtn = view.findViewById(R.id.ok_button);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameEt.getText().toString();
                String subjectName = subjectNameEt.getText().toString();

                onClickLister.onClick(className,subjectName);
                dismiss();

            }
        });
        return builder.create();
    }
}
