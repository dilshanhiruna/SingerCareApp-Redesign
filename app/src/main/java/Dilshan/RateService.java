package Dilshan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uee.singercare.MainActivity;
import com.uee.singercare.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RateService extends AppCompatActivity {
    String ID;
    TextView Title;
    ImageView Img;
    TextView date;
    RatingBar ratingBar;
    Button cancel,submit_rate;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_service);
        ID = getIntent().getExtras().getString("ID");
        Title = findViewById(R.id.rate_title);
        date = findViewById(R.id.rate_date);
        Img = findViewById(R.id.rate_dp);
        submit_rate = findViewById(R.id.SubmitRateBtn);
        cancel = findViewById(R.id.SubmitRateBackBtn);
        ratingBar = findViewById(R.id.ratingBar_ratingInput);

//        Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateService.this.finish();
                getWindow().setWindowAnimations(0);
            }
        });
        submit_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float getrating = ratingBar.getRating();

                if(getrating > 0){

                    Map<String, Object> rate_data = new HashMap<>();
                    rate_data.put("rating", getrating);
                    rate_data.put("rated", true);

                    DocumentReference docRef = db.collection("Cards").document(ID);
                    docRef.update(rate_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("SUC", "DocumentSnapshot successfully written!");

                            Dialog dialog = new Dialog(RateService.this, android.R.style.Theme_Light);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.custom_modal);
                            dialog.show();
                            long delay = 2000L;
                            new Timer().schedule(new RemindTask(), delay);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ERR", "Error writing document", e);
                        }
                    });

                }
            }
        });


        DocumentReference docRef = db.collection("Cards").document(ID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        byte[] decodedString = Base64.decode(document.getString("dp"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        String d = document.getTimestamp("date").toDate().toLocaleString();
                        String theDated =d.substring(13,17)+" "+ d.substring(21,23)  +" - "+d.substring(0,12);

                        Title.setText(document.getString("title"));
                        date.setText(theDated);
                        Img.setImageBitmap(decodedByte);

//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("NO_DOC", "No such document");
                    }
                } else {
                    Log.d("FAILED", "get failed with ", task.getException());
                }
            }
        });

        // NAVIGATION BAR START
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0); // IN HERE I = ITEM NUMBER (HOME=1,HISTORY=2,CONTACT=3,WARRANTY=4)
        menuItem.setChecked(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_mainActivity:
                        Intent intent = new Intent(RateService.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                    case R.id.ic_myHistory:
                        Intent intent1 = new Intent(RateService.this, MyHistory.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        break;
                    case R.id.ic_Contact:

                        break;
                    case R.id.ic_Warrenty:

                        break;
                }
                return true;
            }
        });
        //NAVIGATION BAR END



    }

    class RemindTask extends TimerTask {
        public void run() {
            Intent intent = new Intent(RateService.this, MyHistory.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            RateService.this.startActivity(intent);

        }}

}
