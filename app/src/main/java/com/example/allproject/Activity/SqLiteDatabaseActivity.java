package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.allproject.Class.Category;
import com.example.allproject.Database.DatabaseHandler;
import com.example.allproject.R;

import java.util.ArrayList;

public class SqLiteDatabaseActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    DatabaseHandler db ;

    ListView list;
    EditText pdfName, pdfUrl;
    Button pdfSave;

    String name, url ;
    public ArrayAdapter<String> adapter;
    ArrayList<String> tempList1;

    ArrayList<Category> listCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sq_lite_database);

        list = (ListView) findViewById(R.id.listView);

        dialogBuilder = new AlertDialog.Builder(this);
        db = new DatabaseHandler(getApplicationContext());

//        showDataAtListView();
    }

//    public void showDataAtListView() {
//        tempList1 = new ArrayList<String>();
//        listCampaign = db.getAllInfo(getApplicationContext());
//
//        for (int i = 0; i < listCampaign.size(); i++) {
//
//            if (!listCampaign.get(i).getTitle().isEmpty())
//            {
//                tempList1.add(
//                        "  Title:  "+listCampaign.get(i).getTitle()+"," +
//                        "   Details:  "+listCampaign.get(i).getDetails()
//                );
//
//            }
//        }
//
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tempList1);
//        list.setAdapter(adapter);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_munu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.action_item_one) {
//            createPopupDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

//    private void createPopupDialog()
//    {
//        View view = getLayoutInflater().inflate(R.layout.popup, null);
//        pdfName = (EditText) view.findViewById(R.id.addPdfName);
//        pdfUrl = (EditText) view.findViewById(R.id.addPdfUrl);
//        pdfSave = (Button) view.findViewById(R.id.addPdfButton);
//
//        dialogBuilder.setView(view);
//        dialog = dialogBuilder.create();
//        dialog.show();
//
//        pdfSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!pdfName.getText().toString().isEmpty()
//                        && !pdfUrl.getText().toString().isEmpty()) {
//
//                    name = pdfName.getText().toString().trim();
//                    url = pdfUrl.getText().toString().trim();
////                    Toast.makeText(SqLiteDatabaseActivity.this, "name: " + name + " url: " + url, Toast.LENGTH_SHORT).show();
//                    db.insertUserInfo(name, url);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//
//                            Intent intent = new Intent(SqLiteDatabaseActivity.this, SqLiteDatabaseActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }, 1000); //  1 second.
//                }
//
//            }
//        });
//    }


}