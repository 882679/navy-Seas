package com.example.navyseas;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    public String email, password;
    public TextInputLayout input_email, input_pswd;
    public String error;
    public View v;

    public static Family selectedFamily;
    public static ArrayList<Student> children;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        DBHelper db = new DBHelper(Login.this);

        input_email = findViewById(R.id.input_email);
        input_pswd = findViewById(R.id.input_password);

        Button btnLogin = findViewById(R.id.button_login);


        btnLogin.setOnClickListener(view -> {

            v = view;
            if(check()){
                // credenziali: "Navy@stud.unive.it", "Navy".
                selectedFamily = db.login(email, password);
                if(selectedFamily != null) {
                    Intent switchActivity = new Intent(this, MainActivity.class);
                    startActivity(switchActivity);
                }else{
                    error = "Email o password errati!";
                    printError();
                }
            }
        });

    }


    public boolean check() {
        email = input_email.getEditText().getText().toString();
        password = input_pswd.getEditText().getText().toString();

        if (email.equals("") && password.equals("")) {
            error = "Email e password mancanti!";
            return printError();
        }
        if (email.equals("")) {
            error = "Inserisci una email!";
            return printError();
        }
        if (password.equals("")) {
            error = "Inserisci una password!";
            return printError();
        }
        if (!email.contains("@")) {
            error = "Email non valida!";
            return printError();
        }
        return true;
    }


    public boolean printError() {
        Snackbar snackbar = Snackbar.make(v, error, 2000);
        snackbar.setAction("Riprova", view -> {
        });
        snackbar.show();
        return false;
    }

}
