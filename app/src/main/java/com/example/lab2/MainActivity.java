package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameField, lastNameField, gradesCountField;
    Button gradesButton;

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

        firstNameField.addTextChangedListener(textWatcher);
        lastNameField.addTextChangedListener(textWatcher);
        gradesCountField.addTextChangedListener(textWatcher);

        firstNameField.setOnFocusChangeListener(((view, hasFocus) -> checkField(hasFocus, getString(R.string.firstNameEmpty), firstNameField)));
        lastNameField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus, getString(R.string.lastNameEmpty), lastNameField));
        gradesCountField.setOnFocusChangeListener((view, hasFocus) -> checkField(hasFocus, getString(R.string.gradesCountEmpty), gradesCountField));
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
}