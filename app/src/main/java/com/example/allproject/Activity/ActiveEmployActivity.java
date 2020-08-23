package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.allproject.Adapter.ActiveEmployAdapter;
import com.example.allproject.Adapter.JSONAdapter;
import com.example.allproject.Class.Members;
import com.example.allproject.Class.Product;
import com.example.allproject.R;
import com.example.allproject.interfaces.IActiveEmployeeCallBack;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActiveEmployActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    List<Members> memberList;

    private CollectionReference memberRef ;
    private String currentDate, currentTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_employ);

        memberRef = FirebaseFirestore.getInstance().collection("Members");

        recyclerView = findViewById(R.id.userActiveRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        memberList = new ArrayList<>();

        LoadMembersList();

        RecentDate();
    }

    private void RecentDate() {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());
        Log.d("TAG"," currentDate: "+ currentDate ) ;

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());
        Log.d("TAG"," currentTime: "+ currentTime ) ;
    }

    private void LoadMembersList() {
        memberRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Members member = documentSnapshot.toObject(Members.class);
                    memberList.add(new Members(
                            member.getCurrentId(),
                            member.getMemberFirstName(),
                            member.getMemberLastName(),
                            member.getMemberUserName(),
                            member.getMemberEmail(),
                            member.getMemberPassword(),
                            member.getMemberState(),
                            member.getMemberProfileImage()
                    ));

                }
                Log.d("TAG", "memberList: "+memberList ) ;
                ActiveEmployAdapter adapter = new ActiveEmployAdapter(getApplicationContext(), memberList);
//                ActiveEmployAdapter adapter1 = new ActiveEmployAdapter() {
//                    @Override
//                    public void loadDataFromPage(int page) {
//
//                    }
//                };
                recyclerView.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);



//                textViewData.setText(data);
            }
        });
    }

}