package Pabasara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uee.singercare.R;

import java.util.Locale;

public class selecet_language extends AppCompat {

    Button getstarted,english,sinhala,tamil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecet_language);
        english = findViewById(R.id.englishBtn);
        sinhala = findViewById(R.id.sinhalaBtn);
        tamil = findViewById(R.id.TamilBtn);

        language_manager lm = new language_manager(this);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("en");
//                recreate();
            }
        });
        sinhala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("si");
//                recreate();
            }
        });
        tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("ta");
//                recreate();
            }
        });
        getstarted= findViewById(R.id.getstartedBtn);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(selecet_language.this,login.class);
                startActivity(i);

                Toast.makeText(selecet_language.this, "Login Page", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*public void setLanguage(Activity activity, String language){
        Locale locale = new Locale(language);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.englishBtn:{
                setLanguage(this,"en");
                break;
            }
            case R.id.sinhalaBtn:{
                setLanguage(this,"si");
                break;
            }
            case R.id.TamilBtn:{
                setLanguage(this,"ta");
                break;
            }
        }
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selecet_language.this,login.class));
                Toast.makeText(selecet_language.this, "Login Page", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


}