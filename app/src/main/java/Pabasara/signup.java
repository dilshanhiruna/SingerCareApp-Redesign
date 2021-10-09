package Pabasara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.uee.singercare.R;

import java.util.Calendar;

public class signup extends AppCompatActivity implements View.OnClickListener {

    private EditText fullName,email,phone,password,confirmPassword,address,dob;
    Button signUp;
    TextView signupTOlogin;

    DatePickerDialog.OnDateSetListener setListener;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        //initialize the variables
        fullName = findViewById(R.id.et_signup_fullname);
        email = findViewById(R.id.et_signup_email);
        phone = findViewById(R.id.et_signup_phone);
        password = findViewById(R.id.et_signup_pwd);
        confirmPassword = findViewById(R.id.et_signup_cmpwd);
        address = findViewById(R.id.et_signup_address);
        dob = findViewById(R.id.et_signup_dob);

        signUp = findViewById(R.id.signupBtn);
        signUp.setOnClickListener(this);

        //redirect to login page
        signupTOlogin = findViewById(R.id.tv_signup_TO_login);
        signupTOlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(signup.this,login.class) ;
                startActivity(i);
                //Toast.makeText(signup.this, "Login page", Toast.LENGTH_SHORT).show();
            }
        });

        //set dummy data
        fullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.setText("xxx@gmail.com");
                phone.setText("0745678995");
                password.setText("1234567");
                confirmPassword.setText("1234567");
                address.setText("Kandy");

            }
        });
        //calender
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        signup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        String date = day + "/" +month +"/"+year;
                        dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signup_alreadyreg:
                startActivity(new Intent(this,login.class));
                break;
            case R.id.signupBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {


        //get data from the feilds
        String et_fullname = fullName.getText().toString().trim();
        String et_email = email.getText().toString().trim();
        String et_phone = phone.getText().toString().trim();
        String et_password = password.getText().toString().trim();
        String et_confirmpassword = confirmPassword.getText().toString().trim();
        String et_address = address.getText().toString().trim();

        //validate
        if (et_fullname.isEmpty() || et_email.isEmpty() || et_phone.isEmpty() || et_password.isEmpty() || et_confirmpassword.isEmpty() || et_address.isEmpty() || dob.getText().toString().isEmpty()) {
            fullName.setError("Required field");
            email.setError("Required field");
            phone.setError("Required field");
            password.setError("Required field");
            confirmPassword.setError("Required field");
            address.setError("Required field");
            //dob.setError("Required field");
        }
        if(!et_fullname.isEmpty() && !et_address.isEmpty() && !et_phone.isEmpty() && !et_password.isEmpty() && !et_confirmpassword.isEmpty() && !et_address.isEmpty() && !dob.getText().toString().isEmpty()){
            if (!et_fullname.matches("^[a-zA-Z]+(([,. ][a-zA-Z])?[a-zA-Z]*)*$") || et_fullname.length() < 5) {
                fullName.setError("Invalid name");
                fullName.setFocusable(true);
            } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email).matches()) {
                email.setError("Invalid email");
                email.setFocusable(true);
            } else if (!et_phone.matches("^0[0-9][0-9]{8}$")) {
                phone.setError("Invalid phone no");
                phone.setFocusable(true);
            } else if (!(et_password.length() >= 6)) {
                password.setError("password should be at least 6 characters");
                password.setFocusable(true);
            }
//            else if(et_confirmpassword != et_password){
//               confirmPassword.setError("Mismatching");
//               confirmPassword.setFocusable(true);
//            }else{
            {
            mAuth.createUserWithEmailAndPassword(et_email, et_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user user = new user(et_fullname, et_email, et_phone, et_address, dob.getText().toString());

                                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //redirect to login page
                                            startActivity(new Intent(signup.this,login.class));
                                            Toast.makeText(signup.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            //Toast.makeText(Signup.this, "Failed to registered. Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(signup.this, "Failed to registered. Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }}
    }
}