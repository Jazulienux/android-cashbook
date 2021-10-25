package com.example.bnsp_jazulienux.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bnsp_jazulienux.Activities.MainActivity;
import com.example.bnsp_jazulienux.Adapters.ItemAdapters;
import com.example.bnsp_jazulienux.Models.Databases;
import com.example.bnsp_jazulienux.Models.ItemModels;
import com.example.bnsp_jazulienux.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CashFlow extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String menu = "";
    String password = "";
    String username = "";
    int id_user;
    View view;
    CardView back;


    RecyclerView recyclerView;
    List<ItemModels> dataItem;
    ItemAdapters itemAdapters;
    ItemModels itemModels;

    // TODO: Rename and change types of parameters

    public CashFlow() {
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
        view =  inflater.inflate(R.layout.fragment_cash_flow, container, false);
        back = view.findViewById(R.id.back);

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

        Databases db = new Databases(getContext().getApplicationContext());
        Cursor data = db.getDataCash();
        data.moveToFirst();

        dataItem = new ArrayList<>();

        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            int jenis = data.getInt(data.getColumnIndex("jenis"));
            String cash = "[ + ] ";
            int img = R.drawable.in;
            if(jenis == 0){
                cash = "[ - ] ";
                img = R.drawable.out;
            }
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);

            double nominal = data.getDouble(data.getColumnIndex("nominal"));
            cash += kursIndonesia.format(nominal);
            String keterangan = data.getString(data.getColumnIndex("keterangan"));
            keterangan = keterangan.length() == 0 ? "-" : keterangan;
            String tgl = data.getString(data.getColumnIndex("tgl"));
            itemModels = new ItemModels(cash,keterangan,tgl,img);
            dataItem.add(itemModels);
        }

        recyclerView = view.findViewById(R.id.cashItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapters = new ItemAdapters(getActivity(),dataItem);
        recyclerView.setAdapter(itemAdapters);

        return  view;
    }
}