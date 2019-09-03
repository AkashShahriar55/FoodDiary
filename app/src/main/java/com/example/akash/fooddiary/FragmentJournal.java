package com.example.akash.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.app.Activity.RESULT_OK;



public class FragmentJournal extends Fragment implements View.OnClickListener{

    private double[] data=new double[12];

    private OnFragmentInteractionListener mListener;

    TextView age;
    TextView gender;
    TextView weight;
    TextView height;
    TextView bodyfat;
    TextView bmi;
    TextView BMR;
    TextView TDEE;
    TextView Leanmass;
    TextView Fatmass;
    TextView Bmirange;
    TextView Idealweight;
    TextView tomaintain;
    TextView tolosshalf;
    TextView tolossone;
    TextView togainhalf;
    TextView togainone;
    TextView commentbodyfat;
    TextView commentBMI;

    Button floatingbutton;
    Button historybutton;

    TextView date;
    TextView time;

    double mage;
    double mgender;
    double mweight;
    double mheight;
    double mbodyfat;
    double mbmi;
    double mBMR;
    double mTDEE;
    double mLeanmass;
    double mFatmass;
    double mIdealweight;
    String mdate;
    String mtime;


           // mAge,mGenderId,mWeight,mHight,mBdyfat,bmi,resultBmr,resultTDEE,leanmass,fatmass

    public FragmentJournal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_journal, container, false);
        age=view.findViewById(R.id.Ageval);
        gender=view.findViewById(R.id.genderval);
        weight=view.findViewById(R.id.Weightval);
        height=view.findViewById(R.id.heightval);
        bodyfat=view.findViewById(R.id.BodyFatperval);
        bmi=view.findViewById(R.id.BMIval);
        BMR=view.findViewById(R.id.BMRrateval);
        TDEE=view.findViewById(R.id.CalorieIntakeval);
        Leanmass=view.findViewById(R.id.Leanmassval);
        Fatmass=view.findViewById(R.id.Fatmassval);
        Bmirange=view.findViewById(R.id.BMIrangeval);
        Idealweight=view.findViewById(R.id.Idealweightval);
        tomaintain=view.findViewById(R.id.tomaintainval);
        tolosshalf=view.findViewById(R.id.halflossval);
        tolossone=view.findViewById(R.id.onelossval);
        togainhalf=view.findViewById(R.id.halfgainval);
        togainone=view.findViewById(R.id.onegainval);
        commentBMI=view.findViewById(R.id.BMIcomment);
        commentbodyfat=view.findViewById(R.id.BodyFatpercomm);
        date=view.findViewById(R.id.date);
        time=view.findViewById(R.id.time);

        floatingbutton = view.findViewById(R.id.floatingbtn);
        historybutton=view.findViewById(R.id.historybtn);
        floatingbutton.setOnClickListener(this);
        historybutton.setOnClickListener(this);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DatabaseHelper dh =new DatabaseHelper(getActivity());
        Cursor cursor=dh.getAllData();
        while (cursor.moveToNext()){
            Log.d("test",""+cursor.getDouble(0));
            Log.d("test",""+cursor.getString(1));
            Log.d("test",""+cursor.getString(2));
            Log.d("test",""+cursor.getDouble(3));
            Log.d("test",""+cursor.getDouble(4));
            Log.d("test",""+cursor.getDouble(5));
            Log.d("test",""+cursor.getDouble(6));
            Log.d("test",""+cursor.getDouble(7));
            Log.d("test",""+cursor.getDouble(8));
            Log.d("test",""+cursor.getDouble(9));
            Log.d("test",""+cursor.getDouble(10));
            Log.d("test",""+cursor.getDouble(11));
            Log.d("test",""+cursor.getDouble(12));

            if(cursor.getInt(0)==cursor.getCount()){
                break;
            }
        }
          /*mdate=cursor.getString(1);
          mtime=cursor.getString(2);
        for(int i=0;i<10;i++){
            data[i]=cursor.getDouble(i+3);
        }
        updateanalytics();
        */

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.historybtn){
            Intent intent=new Intent(getActivity(),HistoryList.class);
            startActivityForResult(intent,0);
        }
        else{
            Intent intent =new Intent(getActivity(),BmrCalculator.class);
            startActivityForResult(intent,0);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent databundle) {
        super.onActivityResult(requestCode, resultCode, databundle);
        if(resultCode==RESULT_OK){
            Bundle bundle=databundle.getExtras();
            this.data= bundle.getDoubleArray("bmr bundle");

            Date currenttime= Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            mdate=simpleDateFormat.format(currenttime);
            SimpleDateFormat simpletimeFormat=new SimpleDateFormat("HH:mm:ss");
            mtime=simpletimeFormat.format(currenttime);

            updateanalytics();

            DatabaseHelper dh=new DatabaseHelper(getActivity());
            dh.insertData(mdate,mtime,data);


        }
        else{
            DatabaseHelper dh =new DatabaseHelper(getActivity());
            Cursor cursor=dh.getAllData();
            while (cursor.moveToNext()){
                Log.d("test",""+cursor.getDouble(5));
                if(cursor.getInt(0)==(databundle.getExtras().getInt("selected pos")+1)){
                    break;
                }
            }
            mdate=cursor.getString(1);
            mtime=cursor.getString(2);
            for(int i=0;i<10;i++){
                data[i]=cursor.getDouble(i+3);

            }
            updateanalytics();
        }

    }

    public void updateanalytics(){
        // mAge,mGenderId,mWeight,mHight,mBdyfat,bmi,resultBmr,resultTDEE,leanmass,fatmass

        mage=data[0];
        mgender=data[1];
        mweight=data[2];
        mheight=data[3];
        mbodyfat=data[4];
        mbmi=data[5];
        mBMR=data[6];
        mTDEE=data[7];
        mLeanmass=data[8];
        mFatmass=data[9];

        date.setText(""+mdate);
        time.setText(""+mtime);

        if(mgender==1){
            gender.setText("Male");
            mIdealweight=56.2+1.41*(mheight%60);
            Idealweight.setText(""+mIdealweight+"kg");
            if(mbodyfat>2&&mbodyfat<5){
                commentbodyfat.setText("Essential");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Green));
            }
            else if(mbodyfat>6&&mbodyfat<13){
                commentbodyfat.setText("Athletes");
                commentbodyfat.setTextColor(getResources().getColor(R.color.yellowGreen));
            }
            else if(mbodyfat>14&&mbodyfat<17){
                commentbodyfat.setText("Fitness");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Yellow));
            }
            else if(mbodyfat>18&&mbodyfat<24){
                commentbodyfat.setText("Average");
                commentbodyfat.setTextColor(getResources().getColor(R.color.YelloOrange));
            }
            else if(mbodyfat>25){
                commentbodyfat.setText("Obese");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Orange));
            }

        }
        else{
            gender.setText("Female");
            mIdealweight=53.1+1.36*(mheight%60);
            Idealweight.setText(""+mIdealweight+"kg");

            if(mbodyfat>10&&mbodyfat<13){
                commentbodyfat.setText("Essential");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Green));
            }
            else if(mbodyfat>14&&mbodyfat<20){
                commentbodyfat.setText("Athletes");
                commentbodyfat.setTextColor(getResources().getColor(R.color.yellowGreen));
            }
            else if(mbodyfat>21&&mbodyfat<24){
                commentbodyfat.setText("Fitness");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Yellow));
            }
            else if(mbodyfat>25&&mbodyfat<31){
                commentbodyfat.setText("Average");
                commentbodyfat.setTextColor(getResources().getColor(R.color.YelloOrange));
            }
            else if(mbodyfat>32){
                commentbodyfat.setText("Obese");
                commentbodyfat.setTextColor(getResources().getColor(R.color.Orange));
            }

        }


        age.setText(""+(int)mage);
        weight.setText(""+mweight+"Kg");
        height.setText(""+(int)(mheight/12)+'"'+" "+(mheight%12)+"'");
        bmi.setText(""+String.format("%.2f",mbmi));
        Bmirange.setText(""+String.format("%.2f",18.5*(mheight*mheight*0.0254*0.0254))+"kg -"+
                String.format("%.2f",25*(mheight*mheight*0.0254*0.0254))+"kg");
        bodyfat.setText(""+mbodyfat+"%");
        Leanmass.setText(""+String.format("%.2f",mLeanmass)+"kg");
        Fatmass.setText(""+String.format("%.2f",mFatmass)+"kg");
        BMR.setText(""+(int)mBMR+"Cal/day");
        TDEE.setText(""+(int)mTDEE+"Cal/day");
        tomaintain.setText(""+(int)mTDEE+"Cal/day");
        tomaintain.setTextColor(getResources().getColor(R.color.Green));
        tolosshalf.setText(""+(int)(mTDEE-500)+"Cal/day");
        tolossone.setText(""+(int)(mTDEE-1000)+"Cal/day");
        togainhalf.setText(""+(int)(mTDEE+500)+"Cal/day");
        togainone.setText(""+(int)(mTDEE+1000)+"Cal/day");

            /*Very severely underweight	        < 15
            Severely underweight	            15 - 16
            Underweight	                        16 - 18.5
            Normal (healthy weight)     	    18.5 - 25
            Overweight	                        25 - 30
            Obese Class I (Moderately obese)	30 - 35
            Obese Class II (Severely obese)	    35 - 40
            Obese Class III (Morbid)	        > 40*/

        if(mbmi<15){
            commentBMI.setText("Very severely underweight");
            commentBMI.setTextColor(getResources().getColor(R.color.Red));
        }
        else if(mbmi>15&&mbmi<16){
            commentBMI.setText("Severely underweight");
            commentBMI.setTextColor(getResources().getColor(R.color.OrangeREd));
        }
        else if(mbmi>16&&mbmi<18.5){
            commentBMI.setText("Underweight");
            commentBMI.setTextColor(getResources().getColor(R.color.Orange));
        }
        else if(mbmi>18.5&&mbmi<25){
            commentBMI.setText("Normal (healthy weight)");
            commentBMI.setTextColor(getResources().getColor(R.color.Green));
        }
        else if(mbmi>25&&mbmi<30){
            commentBMI.setText("Overweight");
            commentBMI.setTextColor(getResources().getColor(R.color.YelloOrange));
        }
        else if(mbmi>30&&mbmi<35){
            commentBMI.setText("Obese Class I (Moderately obese)");
            commentBMI.setTextColor(getResources().getColor(R.color.Orange));
        }
        else if(mbmi>35&&mbmi<40){
            commentBMI.setText("Obese Class II (Severely obese)");
            commentBMI.setTextColor(getResources().getColor(R.color.OrangeREd));
        }
        else if(mbmi>40){
            commentBMI.setText("Obese Class III (Morbid)");
            commentBMI.setTextColor(getResources().getColor(R.color.Red));
        }
    }

}
