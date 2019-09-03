package com.example.akash.fooddiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BmrCalculator extends AppCompatActivity implements TextWatcher {

    TextView txt1;
    TextView txtResult;
    TextView txtResultTitle;
    EditText edtWeight;
    EditText edtAge;
    EditText edthgtft;
    EditText edthgtin;
    EditText edtbdyfat;
    EditText edtexrcs;

    Button btncalculate;

    RadioGroup rdgrpgndr;

    String[] some_array;
    int selecteditem;
    double[] exrcsfactor={1.2,1.375,1.55,1.725,1.9};

    double mWeight;
    double mHight;
    double mAge;
    double mBdyfat;
    double mGenderId;

    double resultBmr;
    double resultTDEE;
    double bmi;
    double leanmass;
    double fatmass;



    final int requestcodebodyfat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr_calculator);

        txt1 = (TextView) findViewById(R.id.text_nobodyfat);


        edtWeight=(EditText) findViewById(R.id.editText_weight);
        edtAge= (EditText) findViewById(R.id.editText_age);
        edthgtft= (EditText) findViewById(R.id.editText_heightft);
        edthgtin=(EditText) findViewById(R.id.editText_heightin);
        edtbdyfat=(EditText) findViewById(R.id.editText_bodyfat);
        edtexrcs=(EditText) findViewById(R.id.editTextExercise);

        rdgrpgndr= (RadioGroup) findViewById(R.id.radiogrp_bmr);

        edthgtft.addTextChangedListener(this);
        edthgtin.addTextChangedListener(this);
        edtbdyfat.addTextChangedListener(this);

        btncalculate=(Button) findViewById(R.id.btnbmr);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BmrCalculator.this,BodyFatCalculator.class);
                Bundle bundle = new Bundle();
                String[] data={edtAge.getText().toString(),edtWeight.getText().toString(),
                        edthgtft.getText().toString(),edthgtin.getText().toString(),
                        String.valueOf(rdgrpgndr.getCheckedRadioButtonId())};
                bundle.putStringArray("Already given data",data);
                intent.putExtra("Passed bundle",bundle);
                intent.putExtra("activity",1);
                startActivityForResult(intent,requestcodebodyfat);
            }
        });


        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(edtAge) || isEmpty(edtWeight) || isEmpty(edthgtft) || isEmpty(edthgtin)
                        || (isEmpty(edtexrcs))
                        ){
                    Snackbar empty=Snackbar.make(findViewById(R.id.bmrcalculator),"Please fill Your Details Properly",1000);
                    empty.show();
                }
                else{
                    calculate_bmr();
                }

            }
        });

    }

    public void showdialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( Html.fromHtml("<font color='#7d22be'>How Much Do You Exercise?</font>"));
        builder.setIcon(R.drawable.ic_noun_988592_cc);
        builder.setItems(R.array.Exercise_details, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selecteditem=which;
                some_array = getResources().getStringArray(R.array.Exercise_details);
                edtexrcs.setText(some_array[which]);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            int temp = Integer.parseInt(s.toString());
            if (edthgtft.getEditableText().hashCode() == s.hashCode()) {
                if(temp>7){
                    s.replace(0,s.length(),"7");
                }
            } else if (edthgtin.getEditableText().hashCode() == s.hashCode()) {
                if(temp>11){
                    s.replace(0,s.length(),"11");
                }
            } else if (edtbdyfat.getEditableText().hashCode() == s.hashCode()) {
                if(temp>100){
                    s.replace(0,s.length(),"100");
                }
            }
        }
        catch (NumberFormatException e){
        }

    }

    void calculate_bmr(){
        mWeight=Double.parseDouble(edtWeight.getText().toString());
        mAge=Double.parseDouble(edtAge.getText().toString());
        mHight=(Double.parseDouble(edthgtft.getText().toString())*12.0)+Double.parseDouble(edthgtin.getText().toString());
        mBdyfat=Double.parseDouble(edtbdyfat.getText().toString());
        if(rdgrpgndr.getCheckedRadioButtonId()==R.id.radiobtn_male){
            mGenderId=1;
        }
        else{
            mGenderId=0;
        }

        resultBmr=370+(21.6*(mWeight-(mBdyfat*mWeight)/100));

        resultTDEE=resultBmr*exrcsfactor[selecteditem];

        showresult();
    }

    public boolean isEmpty(EditText edt){
        return edt.getText().toString().matches("");
    }

    void showresult(){
        AlertDialog.Builder resultdialogbuilder= new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View view =inflater.inflate(R.layout.activity_result_dialog,null);
        resultdialogbuilder.setView(view);
        String temp=String.format("%.4f",resultBmr);
        txtResult = view.findViewById(R.id.txt_result);
        txtResultTitle=view.findViewById(R.id.resultTitle);
        txtResultTitle.setText("Your BMR Is");
        txtResult.setText(temp+"Kcal/Day");
        txtResult.setTextSize(30);
        resultdialogbuilder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentbmr = new Intent();
                Bundle bndlbmr = new Bundle();
                double [] data={mAge,mGenderId,mWeight,mHight,mBdyfat,bmi,resultBmr,resultTDEE,leanmass,fatmass};
                bndlbmr.putDoubleArray("bmr bundle",data);
                intentbmr.putExtras(bndlbmr);
                setResult(RESULT_OK,intentbmr);
                finish();
            }
        });

        Dialog dialog=resultdialogbuilder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            double[] temp= bundle.getDoubleArray("Body Fat");
            bmi=temp[1];
            leanmass=temp[2];
            fatmass=temp[3];
            edtbdyfat.setText(String.valueOf(temp[0]));
        }
    }
}
