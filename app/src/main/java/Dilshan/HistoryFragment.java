package Dilshan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.uee.singercare.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;

    HistoryAdapter2 historyAdapter;

    List<String> id ;
    List<String> titles ;
    List<String> categories;
    List<Timestamp> date;
    List<String> dp;
    List<Boolean> rated;
    List<Double> ratings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id = new ArrayList<>();
        titles = new ArrayList<>();
        categories= new ArrayList<>();
        date= new ArrayList<>();
        dp= new ArrayList<>();
        rated= new ArrayList<>();
        ratings= new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.ongoing_history_recyclerView_2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cards").whereEqualTo("active", false).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                    id.add(document.getId());
                    titles.add(document.getString("title"));
                    categories.add(document.getString("category"));
                    date.add(document.getTimestamp("date"));
                    dp.add(document.getString("dp"));
                    rated.add(document.getBoolean("rated"));
                    ratings.add(document.getDouble("rating"));

                    Log.d("IMAGE", "IMAGE: " + document.getString("dp"));
                }
                historyAdapter = new HistoryAdapter2(this.getContext(),id,dp,titles,categories,date,rated,ratings);
                recyclerView.setAdapter(historyAdapter);

            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}