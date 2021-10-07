package Pabasara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.uee.singercare.R;

public class forgot_password extends AppCompatActivity {

    EditText resetpwd_email;
    Button send;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetpwd_email = findViewById(R.id.et_forgotpwd_enteremail);
        send = findViewById(R.id.sendBtn);

        auth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = resetpwd_email.getText().toString().trim();

        //validation
        if(email.isEmpty()){
            resetpwd_email.setError("Email is required");
            resetpwd_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resetpwd_email.setError("Please provide an email");
            resetpwd_email.requestFocus();
        }

        //send email to reset the password
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(forgot_password.this,login.class));
                    Toast.makeText(forgot_password.this, "Check your email to reset the password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(forgot_password.this, "Try again! Something went wrong", Toast.LENGTH_LONG).show();}
            }
        });
    }
}