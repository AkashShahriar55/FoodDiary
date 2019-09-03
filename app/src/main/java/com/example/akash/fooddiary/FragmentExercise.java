package com.example.akash.fooddiary;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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



public class FragmentExercise extends Fragment {

    ArrayList<HashMap<String,String>> calorieList=new ArrayList<>();

    ListView lv;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_exercise, container, false);


        return view;
    }

    public static FragmentExercise newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentExercise fragment = new FragmentExercise();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = getActivity().findViewById(R.id.progressbarexr);
        lv= getActivity().findViewById(R.id.listexr);

        new Exercisecalorielist().execute();
    }


    public class Exercisecalorielist extends AsyncTask<String,String,String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                String jsonstr;

                /*URL url = new URL("http://godzeela.com/exercise.js");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream in=new BufferedInputStream(connection.getInputStream());
                BufferedReader reader= new BufferedReader(new InputStreamReader(in));
                StringBuilder sb=new StringBuilder();

                String line;

                while ((line=reader.readLine())!=null){
                    sb.append(line).append("\n");
                }

                jsonstr=sb.toString();*/

                InputStream is = getActivity().getAssets().open("exercise.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonstr = new String(buffer, "UTF-8");

                if(jsonstr!=null){
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray jsonArray=jsonObject.getJSONArray("Exercise");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String Description=jsonObject1.getString("Description");
                        String a=jsonObject1.getString("a");
                        String b=jsonObject1.getString("b");
                        String c=jsonObject1.getString("c");

                        HashMap<String,String> tempHash=new HashMap<>();

                        tempHash.put("Description",Description);
                        tempHash.put("a",a);
                        tempHash.put("b",b);
                        tempHash.put("c",c);

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
                    R.layout.exrlist_layout,new String[]{"Description","a","b","c"},
                    new int[]{R.id.Description,R.id.a,R.id.b,R.id.c});
            lv.setAdapter(adapter);
        }
    }
}
