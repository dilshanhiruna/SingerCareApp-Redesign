package Dilshan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uee.singercare.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HistoryAdapter2 extends RecyclerView.Adapter<HistoryAdapter2.ViewHolder> {

    Context context;
    List<String> id;
    List<String> dp;
    List<String> title;
    List<String> category;
    List<Boolean> rated;
    List<Double> ratings;
    List<Timestamp> date;


    public HistoryAdapter2(Context ct, List<String> id, List<String> dp,  List<String> title,  List<String> category,  List<Timestamp> date,  List<Boolean> rated,List<Double> ratings){
        this.context = ct;
        this.id = id;
        this.dp = dp;
        this.title = title;
        this.category = category;
        this.date = date;
        this.rated =rated;
        this.ratings =ratings;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_card_view_2, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        byte[] decodedString = Base64.decode(dp.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        String nd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String cd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date.get(position).toDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date currentDate = new Date();
        Date createdDate = new Date();
        try {
            currentDate = sdf.parse(nd);
            createdDate = sdf.parse(cd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.title.setText(title.get(position));
        String d = date.get(position).toDate().toLocaleString();
        holder.category.setText(category.get(position)+"  -  "+ d.substring(13,17)+" "+ d.substring(21,23)  +" "+d.substring(0,12));
        holder.dp.setImageBitmap(decodedByte);
        holder.datediff.setText(String.valueOf(dateDifferent(createdDate,currentDate))+"d");

        if (rated.get(position)){
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(ratings.get(position).floatValue());

            holder.dp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Map<String, Object> data = new HashMap<>();
                    data.put("rated", false);

                    DocumentReference docRef = db.collection("Cards").document(id.get(position));
                    docRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(context, "UnRated", Toast.LENGTH_SHORT).show();

                        }
                    });



                    return true;
                }
            });

        }else {
            holder.rate_btn.setVisibility(View.VISIBLE);
        }


        holder.rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), RateService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("ID",id.get(position));
                v.getContext().startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("active", true);

                DocumentReference docRef = db.collection("Cards").document(id.get(position));
                docRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(context, "Moved to Ongoing", Toast.LENGTH_SHORT).show();

                    }
                });


                return true;
            }
        });



    }

    public Long dateDifferent(Date pdate, Date cdate){
        long diff = cdate.getTime() - pdate.getTime();
        TimeUnit time = TimeUnit.DAYS;
        return time.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, category, date,datediff;
        ImageView dp;
        RatingBar ratingBar;
        Button rate_btn;

        public ViewHolder(@NonNull View items){
            super(items);
            title = items.findViewById(R.id.history_history_title);
            category = items.findViewById(R.id.history_history_category);
            dp = items.findViewById(R.id.history_history_dp);
            datediff = items.findViewById(R.id.history_history_datediff);
            ratingBar = items.findViewById(R.id.ratingBarDisplay);
            rate_btn = items.findViewById(R.id.rate_my_service);

        }

    }
}
