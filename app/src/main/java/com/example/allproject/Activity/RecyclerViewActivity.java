package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.allproject.Adapter.RecyclerViewAdapter;
import com.example.allproject.Class.Category;
import com.example.allproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerList;
    List<Category> mainToolsList;
    String[] mainToolsName = {
            "Bangladesh",
            "India",
            "Garmany",
            "America",
            "Australia",
            "Camnada",
            "Austria",
            "Mexica"
    };

    int[] imageList = {
            R.drawable.bd, R.drawable.hsc,  R.drawable.bd, R.drawable.hsc,
            R.drawable.bd, R.drawable.hsc,  R.drawable.bd, R.drawable.hsc
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        setTitle("RecyclerView");

        recyclerList = findViewById(R.id.recycler_view) ;
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        mainToolsList = new ArrayList<>();

        for(int i=0; i<mainToolsName.length; i++){
            mainToolsList.add(
                    new Category(
                            mainToolsName[i],
                            imageList[i]
                    )
            );
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mainToolsList);
        recyclerList.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerList.setLayoutManager(manager);

    }
}