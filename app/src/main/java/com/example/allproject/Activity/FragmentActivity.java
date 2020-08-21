package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.allproject.Adapter.FragmentAdapter;
import com.example.allproject.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentActivity extends AppCompatActivity {

    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

//        tv2 = (TextView)findViewById(R.id.tv2);
        final TabLayout tabLayout2 = (TabLayout) findViewById(R.id.tab_layout);





        tabLayout2.addTab(tabLayout2.newTab().setText("Fragment 1"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Fragment 2"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Fragment 3"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Fragment 4"));




        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);



        //===================   tab layout scroll korte   ====================
        tabLayout2.setTabMode(TabLayout.MODE_SCROLLABLE);




        final FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout2.getTabCount() );
        viewPager.setAdapter(adapter);

//        String key = getIntent().getExtras().getString("abc").trim();


//        switch (key){
//            case "0":
//                viewPager2.setCurrentItem(0);
//                //  setTitle("অধ্যায়গুলি");
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "1":
//                viewPager2.setCurrentItem(1);
//                //  setTitle("অধ্যায়গুলির তালিকা");
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "2":
//                viewPager2.setCurrentItem(2);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "3":
//                viewPager2.setCurrentItem(3);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "4":
//                viewPager2.setCurrentItem(4);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "5":
//                viewPager2.setCurrentItem(5);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "6":
//                viewPager2.setCurrentItem(6);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "7":
//                viewPager2.setCurrentItem(7);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "8":
//                viewPager2.setCurrentItem(8);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "9":
//                viewPager2.setCurrentItem(9);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "10":
//                viewPager2.setCurrentItem(10);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "11":
//                viewPager2.setCurrentItem(11);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "12":
//                viewPager2.setCurrentItem(12);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "13":
//                viewPager2.setCurrentItem(13);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "14":
//                viewPager2.setCurrentItem(14);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "15":
//                viewPager2.setCurrentItem(15);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "16":
//                viewPager2.setCurrentItem(16);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "17":
//                viewPager2.setCurrentItem(17);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "18":
//                viewPager2.setCurrentItem(18);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//            case "19":
//                viewPager2.setCurrentItem(19);
//                tv2.setText("অধ্যায়গুলি তালিকা");
//                break;
//
//
//        }







        //-extra
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout2));// ------  page er sathe tab change korte

//------------   tab select korle fragment soho niye change hobe   ------------
        tabLayout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}