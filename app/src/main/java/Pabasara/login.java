package Pabasara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uee.singercare.MainActivity;
import com.uee.singercare.R;

public class login extends AppCompat {

    TextView registerLink,forgot;
    EditText login_email,login_pwd;
    Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = findViewById(R.id.tvjoinNow);
        forgot = findViewById(R.id.tvforgot);

        login_email = findViewById(R.id.etEmail_login);
        login_pwd = findViewById(R.id.etpwd_login);

        login = findViewById(R.id.btnlogin);

        //signup page
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, signup.class);
                startActivity(i);

                //Toast.makeText(login.this, "welcome to register page", Toast.LENGTH_SHORT).show();
            }
        });

        //forgot password page
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,forgot_password.class);
                startActivity(i);

                //Toast.makeText(login.this, "forgot password page", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    //validations
    public void userLogin(){
        String email = login_email.getText().toString().trim();
        String password = login_pwd.getText().toString().trim();

        if(email.isEmpty()){
            login_email.setError("Email is required");
            login_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            login_email.setError("Invalid email");
            login_email.requestFocus();
        }
        if(password.isEmpty()){
            login_pwd.setError("Password is required");
            login_pwd.requestFocus();
            return;
        }
        if (!(password.length() >= 6)) {
            login_pwd.setError("Password should be at least 6 characters");
            login_pwd.requestFocus();
        }

        //progress Dialog
        ProgressDialog progressDialogd = new ProgressDialog(this);
        progressDialogd.setTitle("Please wait");
        progressDialogd.setMessage("Logging in....");
        progressDialogd.setCanceledOnTouchOutside(false);
        progressDialogd.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to dashboard
                    progressDialogd.dismiss();
                    startActivity(new Intent(login.this, MainActivity.class));
                }else{
                    progressDialogd.dismiss();
                    Toast.makeText(login.this, "Failed to login! Please check your credential", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}