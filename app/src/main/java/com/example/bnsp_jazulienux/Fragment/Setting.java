package com.example.bnsp_jazulienux.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bnsp_jazulienux.Activities.MainActivity;
import com.example.bnsp_jazulienux.Models.Databases;
import com.example.bnsp_jazulienux.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Setting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String menu = "";
    String password = "";
    String username = "";
    int id_user;
    View view;
    EditText pass_last, pass_new;
    CardView back,simpan;

    // TODO: Rename and change types of parameters

    public Setting() {
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
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        pass_last = view.findViewById(R.id.pass_last);
        pass_new = view.findViewById(R.id.pass_new);


        back = view.findViewById(R.id.back);
        simpan = view.findViewById(R.id.change_password);

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
                String temp_pass_new = pass_new.getText().toString();
                String temp_pass_last = pass_last.getText().toString();

                if(temp_pass_new.length() == 0){
                    showMsg("Password Baru Tidak Boleh Kosong");
                }
                else if(temp_pass_last.length() == 0){
                    showMsg("Password Lama Tidak Boleh Kosong");
                }
                else if(temp_pass_last.length() == 0 && temp_pass_new.length() == 0){
                    showMsg("Password Lama dan Baru Tidak Boleh Kosong");
                }
                else {
                    if(password.equals(temp_pass_last)){
                        Databases db = new Databases(getActivity().getApplicationContext());
                        boolean update = db.updatePassword(id_user,temp_pass_new);
                        if(update){
                            showMsg("Password Berhasil Diubah");
                            password = temp_pass_new;
                        }
                        else{
                            showMsg("Password Gagal Diubah");
                        }
                    }
                    else{
                        showMsg("Password Lama Tidak Cocok");
                    }
                }
            }
        });

        return view;
    }

    private void showMsg(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}