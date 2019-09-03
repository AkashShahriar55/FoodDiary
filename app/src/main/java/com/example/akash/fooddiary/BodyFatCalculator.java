package com.example.akash.fooddiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.zip.Inflater;

public class BodyFatCalculator extends AppCompatActivity implements TextWatcher {

    TextView txtResult;
    EditText edtWeight;
    EditText edtAge;
    EditText edthgtft;
    EditText edthgtin;
    EditText edtwaist;
    EditText edtHip;
    EditText edtNeck;
    Button btncalculate;
    RadioGroup rdgGender;
    CheckBox cbBmi;
    int selecteditem;
    String resultinp;

    double mWeight;
    double mHight;
    double mAge;
    double mWaist;
    double mHip;
    double mNeck;
    double mGenderId;

    double bmi;
    double bodyfat;
    double fatmass;
    double leanmass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_calculator);

        edtWeight=(EditText) findViewById(R.id.editText_weight);
        edtAge= (EditText) findViewById(R.id.editText_age);
        edthgtft= (EditText) findViewById(R.id.editText_heightft);
        edthgtin=(EditText) findViewById(R.id.editText_heightin);
        edtHip=(EditText) findViewById(R.id.editText_hip);
        edtNeck=(EditText) findViewById(R.id.editText_neck);
        edtwaist=(EditText) findViewById(R.id.editText_waist);

        rdgGender= (RadioGroup) findViewById(R.id.radiogrp_bdyfat);

        btncalculate=(Button) findViewById(R.id.btn_bodyfat);

        cbBmi= (CheckBox) findViewById(R.id.checkbox_dntknow);




        Bundle bundleget=getIntent().getBundleExtra("Passed bundle");
        String[] dataget=bundleget.getStringArray("Already given data");
        rdgGender.check(Integer.parseInt(dataget[4]));
        edtAge.setText(dataget[0]);
        edtWeight.setText(dataget[1]);
        edthgtft.setText(dataget[2]);
        edthgtin.setText(dataget[3]);




        edthgtft.addTextChangedListener(this);
        edthgtin.addTextChangedListener(this);

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEmpty(edtAge) || isEmpty(edtWeight) || isEmpty(edthgtft) || isEmpty(edthgtin)
                        || ((isEmpty(edtNeck) || isEmpty(edtwaist) || isEmpty(edtHip)) && !cbBmi.isChecked())
                        ){
                    Snackbar empty=Snackbar.make(findViewById(R.id.bdyfatcal),"Please fill Your Details Properly",1000);
                    empty.show();
                }
                else{
                    if(cbBmi.isChecked()){
                        calbdyfatwithbmi();
                    }
                    else{
                        calculateBodyFat();
                    }
                }

            }
        });
    }

    private void calbdyfatwithbmi() {


        mWeight=Double.parseDouble(edtWeight.getText().toString())*2.20462262;
        mAge=Double.parseDouble(edtAge.getText().toString());
        mHight=(Double.parseDouble(edthgtft.getText().toString())*12.0)+Double.parseDouble(edthgtin.getText().toString());
        bmi=(mWeight/(mHight*mHight))*703.0;

        if(R.id.radiobtn_male==rdgGender.getCheckedRadioButtonId()){
            mGenderId=1;
            bodyfat = (1.2*bmi)+(0.23*mAge)-(10.8*mGenderId)-5.4;
        }
        else {
            mGenderId=0;
            bodyfat = (1.2*bmi)+(0.23*mAge)-(10.8*mGenderId)-5.4;
        }

        fatmass=(bodyfat*mWeight)/100;
        leanmass=mWeight-fatmass;

        showresult();
    }

    private void calculateBodyFat() {
        mWeight=Double.parseDouble(edtWeight.getText().toString())*2.20462262;
        mAge=Double.parseDouble(edtAge.getText().toString());
        mHight=(Double.parseDouble(edthgtft.getText().toString())*12.0)+Double.parseDouble(edthgtin.getText().toString());
        mHip=Double.parseDouble(edtHip.getText().toString());
        mWaist=Double.parseDouble(edtwaist.getText().toString());
        mNeck=Double.parseDouble(edtNeck.getText().toString());
        if(R.id.radiobtn_male==rdgGender.getCheckedRadioButtonId()){
            mGenderId=1;
            bodyfat=86.010*Math.log10(mWaist-mNeck)-70.041*Math.log10(mHight)+36.76;
        }
        else {
            mGenderId=0;
            bodyfat=(495/(1.29579-(0.35004*Math.log10((mWaist+mHip-mNeck)*2.54))+(0.22100*Math.log10(mHight*2.54))))-450;
        }

        fatmass=(bodyfat*mWeight)/100;
        leanmass=mWeight-fatmass;

        Log.d("test","result "+bodyfat+" "+fatmass+" "+leanmass);

        showresult();

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
            }
        }
        catch (NumberFormatException e){
        }
    }

    public boolean isEmpty(EditText edt){
        return edt.getText().toString().matches("");
    }

    void showresult(){
        AlertDialog.Builder resultdialogbuilder= new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View view =inflater.inflate(R.layout.activity_result_dialog,null);
        resultdialogbuilder.setView(view);
        final String temp=String.format("%.4f",bodyfat);
        txtResult = view.findViewById(R.id.txt_result);
        txtResult.setText(temp+"%");
        resultdialogbuilder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentbdyfat = new Intent();
                Bundle bundlebdyfat = new Bundle();
                double[] data={Double.parseDouble(temp),bmi,leanmass,fatmass};
                bundlebdyfat.putDoubleArray("Body Fat",data);
                intentbdyfat.putExtras(bundlebdyfat);
                setResult(RESULT_OK,intentbdyfat);
                finish();
            }
        });

        Dialog dialog=resultdialogbuilder.create();
        dialog.show();
    }



}
