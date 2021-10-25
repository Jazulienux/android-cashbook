package com.example.bnsp_jazulienux.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bnsp_jazulienux.Models.Databases;
import com.example.bnsp_jazulienux.R;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private CardView login;
    private Databases db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        db = new Databases(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_username = username.getText().toString();
                String text_password = password.getText().toString();
                if(text_password.length() == 0 && text_username.length() == 0){
                    showMsg("Password dan Username Tidak Boleh Kosong");
                }
                else if(text_password.length() == 0){
                    showMsg("Password Tidak Boleh Kosong");
                }
                else if(text_username.length() == 0){
                    showMsg("Username Tidak Boleh Kosong");
                }
                else{
                    Object[] temp = db.checkLogin(text_username,text_password);
                    String usr = temp[1].toString();
                    String pass = temp[2].toString();
                    int status = (int) temp[0];

                    switch (status){
                        case 0:
                            showMsg("Password Salah");
                            break;

                        case -1:
                            showMsg("Akun Tidak Ditemukan");
                            break;

                        default:
                            showMsg("Berhasil Login Dengan ID User : " + status);
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("id_user",status);
                            i.putExtra("username",usr);
                            i.putExtra("password",pass);
                            startActivity(i);
                            break;
                    }
                }
            }
        });
    }

    private void showMsg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}