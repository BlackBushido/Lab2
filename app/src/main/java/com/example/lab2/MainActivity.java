package com.example.lab2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, gradesCountField;
    private Button gradesButton, resultButton;
    public static final String GRADES_KEY = "com.example.w4_two_activities_and.GRADES_KEY";
    private ActivityResultLauncher<Intent> mActivityResultLauncher;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstNameInput = firstNameField.getText().toString();
            String lastNameInput = lastNameField.getText().toString();
            String gradesCountInput = gradesCountField.getText().toString();

            if(!firstNameInput.isEmpty() && !lastNameInput.isEmpty() && gradesCountInput.length() > 0){
                if (Integer.parseUnsignedInt(gradesCountInput) >= 5 && Integer.parseUnsignedInt(gradesCountInput) <= 15) {
                    gradesButton.setVisibility(View.VISIBLE);
                }
            }else{
                gradesButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameField = findViewById(R.id.editTextTextPersonName);
        lastNameField = findViewById(R.id.editTextTextPersonName2);
        gradesCountField = findViewById(R.id.editTextNumber);
        gradesButton = findViewById(R.id.button);
        resultButton = findViewById(R.id.result);

        firstNameField.addTextChangedListener(textWatcher);
        lastNameField.addTextChangedListener(textWatcher);
        gradesCountField.addTextChangedListener(textWatcher);

        firstNameField.setOnFocusChangeListener(((view, hasFocus) -> checkField(hasFocus, getString(R.string.firstNameEmpty), firstNameField)));
        lastNameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus, getString(R.string.lastNameEmpty), lastNameField));
        gradesCountField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus, getString(R.string.gradesCountEmpty), gradesCountField));

        gradesButton.setOnClickListener(v -> startGradesActivity());
        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle bundle = result.getData().getExtras();
                        String srednia = bundle.getString(GradesActivity.MEAN_KEY);

                        resultButton.setVisibility(View.VISIBLE);
                        String resultString = (Double.parseDouble(srednia) >= 3.0) ? getString(R.string.gj) : getString(R.string.nope);
                        resultButton.setText(resultString);
                        resultButton.setOnClickListener(view -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(R.string.app_name);
                            builder.setIcon(R.mipmap.ic_launcher);
                            String res = (Double.parseDouble(srednia) >= 3.0) ? getString(R.string.congratulation) : getString(R.string.condition);
                            builder.setMessage(res)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", (dialog, id) -> finish());
                            AlertDialog alert = builder.create();
                            alert.show();
                        });
                    }
                }
        );

    }

    private void checkField(boolean hasFocus, String msg, EditText textField){
        if(!hasFocus){
            if(textField.getText().toString().isEmpty()){
                textField.setError(msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
            else if(textField == gradesCountField){
                if(Integer.parseInt(textField.getText().toString()) < 5 || Integer.parseInt(textField.getText().toString()) > 15) {
                    msg = getString((R.string.gradesCountRange));
                    gradesCountField.setError(msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startGradesActivity(){
        Intent intent = new Intent(this, GradesActivity.class);
        intent.putExtra(GRADES_KEY, gradesCountField.getText().toString());
        mActivityResultLauncher.launch(intent);
    }
}