package Pabasara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] {0xFFF83600,0xFFFE8C00});
        gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("en");
//                recreate();
                Toast.makeText(selecet_language.this, "Changed the language to English", Toast.LENGTH_SHORT).show();
//                english.setBackgroundResource(R.drawable.select_lanuage_button);
 //               english.setBackgroundResource(R.color.select_lngBtn);
                gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});
                english.setBackgroundDrawable(gd);
                english.setTextColor(Color.parseColor("#FF5864"));
                tamil.setTextColor(Color.parseColor("#000000"));
                sinhala.setTextColor(Color.parseColor("#000000"));
            }
        });
        sinhala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("si");
//                recreate();
                Toast.makeText(selecet_language.this, "Changed the language to Sinhala", Toast.LENGTH_SHORT).show();

                gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});
                sinhala.setBackgroundDrawable(gd);
                sinhala.setTextColor(Color.parseColor("#FF5864"));
                english.setTextColor(Color.parseColor("#000000"));
                tamil.setTextColor(Color.parseColor("#000000"));
            }
        });
        tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lm.updateResource("ta");
//                recreate();
                Toast.makeText(selecet_language.this, "Changed the language to Tamil", Toast.LENGTH_SHORT).show();

                gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});
                tamil.setBackgroundDrawable(gd);
                tamil.setTextColor(Color.parseColor("#FF5864"));
                english.setTextColor(Color.parseColor("#000000"));
                sinhala.setTextColor(Color.parseColor("#000000"));
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