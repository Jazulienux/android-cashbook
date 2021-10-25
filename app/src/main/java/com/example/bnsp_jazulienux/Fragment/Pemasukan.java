package com.example.bnsp_jazulienux.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bnsp_jazulienux.Activities.MainActivity;
import com.example.bnsp_jazulienux.Models.Databases;
import com.example.bnsp_jazulienux.R;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Pemasukan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String menu = "";
    String password = "";
    String username = "";
    int id_user;
    View view;
    EditText tgl,nominal,keterangan;
    CardView back,simpan;
    DatePickerDialog.OnDateSetListener listener;
    String temp_date = "";

    public Pemasukan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menu = getArguments().getString("menu","");
            id_user = getArguments().getInt("id_user",0);
            username = getArguments().getString("username","");
            password = getArguments().getString("password","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_pemasukan, container, false);
        tgl = view.findViewById(R.id.tgl);
        nominal = view.findViewById(R.id.nominal);
        keterangan = view.findViewById(R.id.keterangan);
        back = view.findViewById(R.id.back);
        simpan = view.findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("id_user",id_user);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nominal_text = nominal.getText().toString();
                String ket_text = keterangan.getText().toString();
                if(temp_date.length() == 0){
                    showMsg("Harap Memaskukkan Tanggal");
                }
                else if(nominal_text.length() == 0){
                    showMsg("Nominal Tidak Boleh Kosong");
                }
                else{
                    int nom = Integer.parseInt(nominal_text);
                    if(nom <= 0){
                        showMsg("Nominal Harus Lebih Dari 0");
                    }
                    else {
                        Databases db = new Databases(getActivity().getApplicationContext());
                        boolean cond = db.insertCash(id_user, temp_date, nom, ket_text, 1);
                        if(cond){
                            showMsg("Data Tambah Pemasukan Berhasil Ditambahkan");
                            temp_date = "";
                            tgl.setText("");
                            nominal.setText("");
                            keterangan.setText("");
                        }
                        else{
                            showMsg("Data Tambah Pemasukan Gagal Ditambahkan");
                        }
                    }
                }
            }
        });

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog_MinWidth,listener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                temp_date = year + "-" + month + "-" + dayOfMonth;
                tgl.setText(date);
            }
        };

        return  view;
    }

    private void showMsg(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}