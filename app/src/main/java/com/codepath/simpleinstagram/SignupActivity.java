package com.codepath.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    private Button btnSignup;
    private EditText etSignUsername;
    private EditText etSignPassword;
    private EditText etSignConfirmPassword;
    private EditText etSignEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSignup = findViewById(R.id.btnSignup);
        etSignUsername = findViewById(R.id.etSignUsername);
        etSignPassword = findViewById(R.id.etSignPassword);
        etSignConfirmPassword = findViewById(R.id.etSignConfirmPass);
        etSignEmail = findViewById(R.id.etSignEmail);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etSignUsername.getText().toString();
                String pass = etSignPassword.getText().toString();
                String passconfirm = etSignConfirmPassword.getText().toString();
                String email = etSignEmail.getText().toString();
                //signup(username,pass,passconfirm,email);

            }
        });
    }

    private void signup(String username, String pass, String passconfirm, String email) {
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);
        user.put("username",username);
        user.put("password",pass);
        user.put("email", email);

// Set custom properties
// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("SignUpActivity", "Issue with Signup");
                    Toast.makeText(SignupActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
                startLoginActivity();
            }
        });
    }

    private void startLoginActivity() {
        Log.e("SignUpActivity", "Issue with Signup");
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
