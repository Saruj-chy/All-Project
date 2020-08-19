package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.allproject.Adapter.ActiveEmployAdapter;
import com.example.allproject.Adapter.PostProductListAdapter;
import com.example.allproject.Class.Members;
import com.example.allproject.Class.Product;
import com.example.allproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PostProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    List<Product> productList;

    private CollectionReference productRef ;
    String currentId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product_list);

        currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        productRef = FirebaseFirestore.getInstance().collection("ProductPost");

        recyclerView = findViewById(R.id.productListRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        LoadProductList();

    }

    private void LoadProductList() {
        productRef.document().collection("post").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Product products = documentSnapshot.toObject(Product.class);

                    productList.add(new Product(
                            products.getCurrentId(),
                            products.getFood_name(),
                            products.getTotalPrice(),
                            products.getResturant_name(),
                            products.getRating(),
                            products.getImage()
                    ));

                }
                PostProductListAdapter adapter = new PostProductListAdapter(getApplicationContext(), productList);
                recyclerView.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);

            }
        });
    }
}