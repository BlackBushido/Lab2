package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class GradesActivity extends AppCompatActivity {
    public static final String MEAN_KEY =
            "com.example.w4_two_activities_and.MEAN_KEY";

    private Button mean;
    private ArrayList<ModelOceny> mSubjects;
    private RecyclerView recyclerView;
    private InteraktywnyAdapterTablicy adapterTablicy = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        recyclerView = findViewById(R.id.lista_ocen);
        mSubjects = new ArrayList<>();

        setSubjcetsInfo();
        setAdapter();

        mean = findViewById(R.id.mean);
        mean.setOnClickListener(view -> {
            double srednia = (double) mSubjects.stream().mapToInt(ModelOceny::getOcena).sum() / mSubjects.size();
            Bundle bundle = new Bundle(1);
            bundle.putString(MEAN_KEY,Double.toString(srednia));

            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        });

    }

    private void setSubjcetsInfo() {
        String[] subjects = getResources().getStringArray(R.array.subjects);
        int subjectCount = Integer.parseInt(getIntent().getStringExtra(MainActivity.GRADES_KEY));

        for (int i = 0; i < subjectCount; i++) {
            mSubjects.add(new ModelOceny(subjects[i],2));
        }
    }

    private void setAdapter() {
        adapterTablicy = new InteraktywnyAdapterTablicy(this, mSubjects);
        recyclerView.setAdapter(adapterTablicy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
