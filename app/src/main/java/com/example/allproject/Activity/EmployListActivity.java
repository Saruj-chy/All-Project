package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.Class.Employ;
import com.example.allproject.Database.DatabaseHandler;
import com.example.allproject.R;

import java.util.ArrayList;

public class EmployListActivity extends AppCompatActivity {

    private ListView employList ;
    ArrayList<String> tempList;
    ArrayList<Employ> sqLiteList;
    private ArrayAdapter<String> adapter;


    private EditText editEmployName, editEmployPosition, editEmployContact, editEmployWebpage, editEmployEmail, editEmployAdress ;
    private String employName, employPosition, employContact, employWebpage, employEmail, employAdress ;
    private Button submitBtn;

    private AlertDialog.Builder dialogBuilder, clickBuilder  ;
    private AlertDialog dialog;
    DatabaseHandler db ;


    //show employ details
     private TextView headingTextView ;
     private RelativeLayout relativeLayout;
     private TextView textViewEmployName, textViewEmployPostion, textViewEmployContact, textViewEmployWebpage, textViewEmployEmail, textViewEmployAdress;
    private Button backBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ_list);




        employList = (ListView) findViewById(R.id.employListView);

        dialogBuilder = new AlertDialog.Builder(this);
        clickBuilder = new AlertDialog.Builder(this);
        db = new DatabaseHandler(getApplicationContext());

        showDataAtListView();

        listViewClick() ;

        //  show employ details
        initialRecognize() ;
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headingTextView.setText("All Employ List ");
                relativeLayout.setVisibility(View.GONE);
                employList.setVisibility(View.VISIBLE);
            }
        });


    }

    private void ApplyDetails(int position) {
        headingTextView.setText("Employ "+ sqLiteList.get(position).getName() + "'s Details ");
        relativeLayout.setVisibility(View.VISIBLE);
        employList.setVisibility(View.GONE);
        textViewEmployName.setText(sqLiteList.get(position).getName());
        textViewEmployPostion.setText(sqLiteList.get(position).getPosition());
        textViewEmployContact.setText(sqLiteList.get(position).getContact());
        textViewEmployWebpage.setText(sqLiteList.get(position).getWebpage());
        textViewEmployEmail.setText(sqLiteList.get(position).getEmail());
        textViewEmployAdress.setText(sqLiteList.get(position).getAddress());
    }

    private void initialRecognize() {
        headingTextView = findViewById(R.id.textView) ;
        relativeLayout = findViewById(R.id.relatvelayout) ;
        textViewEmployName = findViewById(R.id.textViewemployName) ;
        textViewEmployPostion = findViewById(R.id.textViewemployPosition) ;
        textViewEmployContact = findViewById(R.id.textViewemployContact) ;
        textViewEmployWebpage = findViewById(R.id.textViewemployWebpage) ;
        textViewEmployEmail = findViewById(R.id.textViewemployEmail) ;
        textViewEmployAdress = findViewById(R.id.textViewemployAddress) ;
        backBtn = findViewById(R.id.backBtn) ;
    }

    private void listViewClick() {
        employList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id ) {
              CharSequence options[] = new CharSequence[]
                        {
                                "Call",
                                "Email",
                                "Web Page",
                                "HTML Page",
                                "Details"
                        };

                clickBuilder.setTitle("Select Any Item in below?") ;

                clickBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                if( i == 0){

                    String phnNo = sqLiteList.get(position).getContact() ;

                    Toast.makeText(EmployListActivity.this, "phn No. "+phnNo, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+ phnNo));
                    startActivity(intent);


                }
                else if( i == 1){
//                    Toast.makeText(EmployDetailsActivity.this, "Email: "+ sqLiteList.get(position).getEmail() , Toast.LENGTH_SHORT).show();
                    String email = sqLiteList.get(position).getEmail() ;

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "details");
                    intent.putExtra(Intent.EXTRA_TEXT   , "Please, send your details towards the authority");
                    try {
                        startActivity(Intent.createChooser(intent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(EmployListActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                }
                else if( i == 2){
//                    Toast.makeText(EmployDetailsActivity.this, "Web Page: "+ sqLiteList.get(position).getWebpage() , Toast.LENGTH_SHORT).show();
                    String WebLink = sqLiteList.get(position).getWebpage() ;
                    Intent webIntent = new Intent(getApplicationContext(), WebViewActivity.class) ;
                    webIntent.putExtra("webLink", WebLink) ;
                    startActivity(webIntent);

                }
                else if( i == 3){
//                    Toast.makeText(EmployDetailsActivity.this, "Html Page: "+ sqLiteList.get(position).getName() , Toast.LENGTH_SHORT).show();
                    String HtmlPage = sqLiteList.get(position).getName() ;
                    Intent webIntent = new Intent(getApplicationContext(), WebViewActivity.class) ;
                    webIntent.putExtra("webLink", "file:///android_asset/app/index.html?val=100" ) ;
                    startActivity(webIntent);


                }
                else if( i == 4){
                    Toast.makeText(EmployListActivity.this, "Details: "+ sqLiteList.get(position).getAddress()  , Toast.LENGTH_SHORT).show();
//                    String details =  sqLiteList.get(position).getAddress() ;

//                    Intent detailIntent = new Intent(getApplicationContext(), EmployDetailsActivity.class) ;
//                    detailIntent.putExtra("name", sqLiteList.get(position).getAddress() ) ;
//                    detailIntent.putExtra("name", sqLiteList.get(position).getAddress() ) ;
//                    detailIntent.putExtra("name", sqLiteList.get(position).getAddress() ) ;
//                    detailIntent.putExtra("name", sqLiteList.get(position).getAddress() ) ;
//                    startActivity(detailIntent);

                    ApplyDetails(position);

                }
                    }
                });
                clickBuilder.show() ;

            }
        });
    }

    public void showDataAtListView() {
        tempList = new ArrayList<String>();
        sqLiteList = db.getAllInfo(getApplicationContext());

//        Log.d("TAG", "name: "+ sqLiteList.get(0).getName()) ;
//        Employ employ = new Employ();
//        Log.d("TAG", employ.getName()) ;

        for (int i = 0; i < sqLiteList.size(); i++) {

            if (!sqLiteList.get(i).getName().isEmpty())
            {
                tempList.add(
                        "Employ Name:  "+sqLiteList.get(i).getName() + "\n" +
                         "Employ Position:  "+sqLiteList.get(i).getPosition()
                );
            }

            Log.d("TAG", "tempList: "+tempList) ;
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tempList);
        employList.setAdapter(adapter);

    }

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
            createPopupDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void createPopupDialog()
    {
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        editEmployName = (EditText) view.findViewById(R.id.editEmployName);
        editEmployPosition = (EditText) view.findViewById(R.id.editEmployPosition);
        editEmployContact = (EditText) view.findViewById(R.id.editEmployContact);
        editEmployWebpage = (EditText) view.findViewById(R.id.editEmployWebpage);
        editEmployEmail = (EditText) view.findViewById(R.id.editEmployEmail);
        editEmployAdress = (EditText) view.findViewById(R.id.editEmployAddress);
        submitBtn = (Button) view.findViewById(R.id.submitEmployBtn);


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editEmployName.getText().toString().isEmpty() && !editEmployPosition.getText().toString().isEmpty()
                    && !editEmployContact.getText().toString().isEmpty() && !editEmployWebpage.getText().toString().isEmpty()
                    && !editEmployEmail.getText().toString().isEmpty() && !editEmployAdress.getText().toString().isEmpty()
                        ) {

                    employName = editEmployName.getText().toString().trim();
                    employPosition = editEmployPosition.getText().toString().trim();
                    employContact = editEmployContact.getText().toString().trim();
                    employWebpage = editEmployWebpage.getText().toString().trim();
                    employEmail = editEmployEmail.getText().toString().trim();
                    employAdress = editEmployAdress.getText().toString().trim();

                    db.insertUserInfo(employName, employPosition, employContact, employWebpage, employEmail, employAdress);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                            Intent intent = new Intent(getApplicationContext(), EmployListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000); //  1 second.
                }

            }
        });
    }


}