package Dilshan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uee.singercare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OngoingFragment extends Fragment {


    RecyclerView recyclerView;

    HistoryAdapter historyAdapter;

    TextView nothingFound_ongoing_text;

    ImageView nothingFound_ongoing;

    List<String> titles ;
    List<String> categories;
    List<Timestamp> date;
    List<String> dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        titles = new ArrayList<>();
        categories= new ArrayList<>();
        date= new ArrayList<>();
        dp= new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);
        recyclerView = view.findViewById(R.id.ongoing_history_recyclerView);
        nothingFound_ongoing= view.findViewById(R.id.nothingFound_ongoing);
        nothingFound_ongoing_text= view.findViewById(R.id.nothingFound_ongoing_text);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cards").whereEqualTo("active", true).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
//                        Toast.makeText(this.getContext(), "Hello", Toast.LENGTH_SHORT).show();

                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                    titles.add(document.getString("title"));
                    categories.add(document.getString("category"));
                    date.add(document.getTimestamp("date"));
                    dp.add(document.getString("dp"));

                    Log.d("IMAGE", "IMAGE: " + document.getString("dp"));
                }
//                Toast.makeText(this.getContext(), titles.toString() , Toast.LENGTH_SHORT).show();
                historyAdapter = new HistoryAdapter(this.getContext(),dp,titles,categories,date);
                recyclerView.setAdapter(historyAdapter);

                if (historyAdapter.getItemCount()==0){
                    nothingFound_ongoing.setVisibility(View.VISIBLE);
                    nothingFound_ongoing_text.setVisibility(View.VISIBLE);
                }

            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}