package Dilshan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.uee.singercare.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    List<String> dp;
    List<String> title;
    List<String> category;
    List<Timestamp> date;


    public HistoryAdapter(Context ct, List<String> dp,  List<String> title,  List<String> category,  List<Timestamp> date){
        this.context = ct;
        this.dp = dp;
        this.title = title;
        this.category = category;
        this.date = date;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_card_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


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

        public ViewHolder(@NonNull View items){
            super(items);
            title = items.findViewById(R.id.ongoing_history_title);
            category = items.findViewById(R.id.ongoing_history_category);
            dp = items.findViewById(R.id.ongoing_history_dp);
            datediff = items.findViewById(R.id.ongoing_history_datediff);

        }

    }
}
