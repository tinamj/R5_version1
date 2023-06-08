package com.example.testlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PatientView extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Patient itemsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        imageView = findViewById(R.id.ImageViewPatient);
        textView = findViewById(R.id.TextViewPatient);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            itemsModel = (Patient) intent.getSerializableExtra("name");

            imageView.setImageResource(itemsModel.getImage());
            textView.setText(itemsModel.getName());
        }

    }
}