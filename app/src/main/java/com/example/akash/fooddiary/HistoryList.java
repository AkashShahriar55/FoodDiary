package com.example.akash.fooddiary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryList extends AppCompatActivity {
    ArrayList<HashMap<String,String>> dates=new ArrayList<>();

    ListView lv;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        lv= (ListView) findViewById(R.id.histlist);

        DatabaseHelper dh=new DatabaseHelper(this);
        Cursor res=dh.getAllData();
        while(res.moveToNext()){
            HashMap<String,String> temp=new HashMap<>();
            temp.put("Date",res.getString(1));
            temp.put("Time",res.getString(2));
            dates.add(temp);
        }

        ListAdapter adapter = new SimpleAdapter(this,dates,R.layout.historylist,new String[]{"Date","Time"},new int[]{R.id.listdate,R.id.listtime});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id=position;
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putInt("selected pos",position);
                intent.putExtras(bundle);
                setResult(2,intent);
                finish();
                Log.d("test",""+id);
            }
        });

    }
}
