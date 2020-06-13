package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail,edtLoginPassword;
    private Button btnLoginActivity,btnSignupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLoginActivity);
                }
                return false;
            }
        });
        btnLoginActivity =  findViewById(R.id.btnLoginActivity);
        btnSignupActivity = findViewById(R.id.btnSignUpLoginActivity);

        btnSignupActivity.setOnClickListener(this);
        btnLoginActivity.setOnClickListener(this);

        if(ParseUser.getCurrentUser() !=null) {

            ParseUser.getCurrentUser().logOut();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLoginActivity:

                if(edtLoginEmail.getText().toString().equals("") ||
                        edtLoginPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this,
                            "Email, Password is required!",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                            true).show();

                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging in");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(),
                            edtLoginPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {

                                        FancyToast.makeText(LoginActivity.this, user.getUsername() + " is signed up", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                                    } else {

                                        FancyToast.makeText(LoginActivity.this, "There was an error: " + e.getMessage(),
                                                Toast.LENGTH_SHORT, FancyToast.ERROR,
                                                false).show();

                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;

            case R.id.btnSignUpLoginActivity:

                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);

                break;

        }


    }

    public void rootLoginActivityIsTapped (View view){

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}