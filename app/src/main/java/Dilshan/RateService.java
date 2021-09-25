package Dilshan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.uee.singercare.R;

public class RateService extends AppCompatActivity {
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_service);
        ID = getIntent().getExtras().getString("ID");

//        Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();




    }
}