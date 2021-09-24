package Dilshan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uee.singercare.R;

public class MyHistory extends AppCompatActivity {

    Button ongoingBtn, historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        ongoingBtn = findViewById(R.id.ongoing_history_btn);
        historyBtn = findViewById(R.id.history_history_btn);

        //Show ongoing fragment style on startup
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new OngoingFragment());
        fragmentTransaction.commit();

        //Show ongoing button style on startup
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] {0xFFF83600,0xFFFE8C00});
        gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});
        ongoingBtn.setBackgroundDrawable(gd);
        ongoingBtn.setTextColor(Color.parseColor("#fafafa"));

        ongoingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gd.setCornerRadii(new float[]{20,20,0,0,0,0,15,15});
                ongoingBtn.setBackgroundDrawable(gd);
                ongoingBtn.setTextColor(Color.parseColor("#fafafa"));
                historyBtn.setTextColor(Color.parseColor("#2e2e2e"));
                historyBtn.setBackgroundResource(R.drawable.history_btn);
                replaceFragment(new OngoingFragment());
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gd.setCornerRadii(new float[]{0,0,20,20,20,20,0,0});
                historyBtn.setBackgroundDrawable(gd);
                historyBtn.setTextColor(Color.parseColor("#fafafa"));
                ongoingBtn.setTextColor(Color.parseColor("#2e2e2e"));
                ongoingBtn.setBackgroundResource(R.drawable.ongoing_btn);
                replaceFragment(new HistoryFragment());
            }
        });



    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}