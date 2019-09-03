package com.example.akash.fooddiary;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class FragmentFoods extends Fragment {

    ArrayList<HashMap<String,String>> calorieList=new ArrayList<>();

    ListView lv;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_foods, container, false);
        return view;
    }


    public static FragmentFoods newInstance() {

        Bundle args = new Bundle();

        FragmentFoods fragment = new FragmentFoods();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = getActivity().findViewById(R.id.progressbar);
        lv= getActivity().findViewById(R.id.list);

        new Foodcalorielist().execute();
    }


    public class Foodcalorielist extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... params) {
            try {
                String jsonstr;

                /*URL url = new URL("http://godzeela.com/Foods.js");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream in=new BufferedInputStream(connection.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(getActivity().getResources().openRawResource(R.raw.exercise)));
                StringBuilder sb=new StringBuilder();

                String line;

                while ((line=reader.readLine())!=null){
                    sb.append(line).append("\n");
                }

                jsonstr=sb.toString();*/

                InputStream is = getActivity().getAssets().open("foods.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonstr = new String(buffer, "UTF-8");

                if(jsonstr!=null){
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray jsonArray=jsonObject.getJSONArray("calories");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String name=jsonObject1.getString("s");
                        String quantity=jsonObject1.getString("m");
                        String calories=jsonObject1.getString("t");

                        HashMap<String,String> tempHash=new HashMap<>();

                        tempHash.put("name",name);
                        tempHash.put("quantity",quantity);
                        tempHash.put("calories",calories);

                        calorieList.add(tempHash);
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            ListAdapter adapter= new SimpleAdapter(getActivity(),calorieList,
                    R.layout.list_layout,new String[]{"name","quantity","calories"},
                    new int[]{R.id.name,R.id.quantity,R.id.calories});
            lv.setAdapter(adapter);
        }
    }
}
