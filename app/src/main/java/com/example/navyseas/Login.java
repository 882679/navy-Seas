package com.example.navyseas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Family;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Login extends AppCompatActivity {
	public static Family selectedFamily;
	public String email, password;
	public TextInputLayout input_email, input_pwd;
	public String error;
	public View v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		input_email = findViewById(R.id.input_email);
		input_pwd = findViewById(R.id.input_password);

		Button btnLogin = findViewById(R.id.button_login);
		btnLogin.setOnClickListener(view -> {
			DBHelper db = new DBHelper(Login.this);
			v = view;

			if (check()) {
				// Credenziali: "Navy@stud.unive.it", "Navy".
				selectedFamily = db.login(email, password);
				if (selectedFamily != null) startActivity(new Intent(this, MainActivity.class));

				else {
					error = "Email o password errati!";
					printError();
				}
			}
		});
	}


	public boolean check() {
		email = Objects.requireNonNull(input_email.getEditText()).getText().toString();
		password = Objects.requireNonNull(input_pwd.getEditText()).getText().toString();

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
