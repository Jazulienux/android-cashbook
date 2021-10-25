package com.example.bnsp_jazulienux.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Databases  extends SQLiteOpenHelper {

    private  static  final String DATABASE_NAME = "bnsp-fixed.db";
    private  static  final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public  Databases(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.createDatabase();
        this.createUser();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void createDatabase(){
        db = this.getWritableDatabase();
        try {
            String tbl_login = "CREATE TABLE IF NOT EXISTS login (id_login INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255) DEFAULT NULL UNIQUE, password VARCHAR(255) DEFAULT NULL)";
            String tbl_log = "CREATE TABLE IF NOT EXISTS log (id_log INTEGER PRIMARY KEY AUTOINCREMENT, tgl DATE, nominal DOUBLE DEFAULT 0, keterangan VARCHAR(255) DEFAULT NULL, id_user INT DEFAULT 0, jenis INT, FOREIGN KEY (id_user) REFERENCES login(id_login) ON DELETE CASCADE ON UPDATE CASCADE)";
            String[] tbl_temporary = {tbl_log,tbl_login};
            for (int i=0;i<tbl_temporary.length;i++){
                db.execSQL(tbl_temporary[i]);
                Log.d("Data", "onCreate: " + tbl_temporary[i]);
            }
        } catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    public void createUser(){
        db = this.getWritableDatabase();
        try{
            String login_insert = "INSERT INTO login (username,password) VALUES ('user','user')";
            db.execSQL(login_insert);
            Log.d("Data", "onCreate: " + login_insert);
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Object[] checkLogin(String username, String password){
        db = this.getReadableDatabase();
        Object[] ob = new Object[3];
        ob[0] = -1;
        ob[1] = "";
        ob[2] = "";

        String query = "SELECT * FROM login WHERE username = '" + username.trim()+ "'";
        Cursor c = db.rawQuery(query,null);
        if(c.getCount() > 0){
            c.moveToFirst();
            String check_password = c.getString(c.getColumnIndex("password"));
            if(check_password.equals(password)){
                ob[0] = c.getInt(c.getColumnIndex("id_login"));
                ob[1] = c.getString(c.getColumnIndex("username"));
                ob[2] = c.getString(c.getColumnIndex("password"));
            }
            else{
                ob[0] = 0;
            }
        }
        else{
            ob[0] = -1;
        }
        return ob;
    }

    public boolean updatePassword(int id_user, String new_password){
        db = this.getWritableDatabase();
        boolean cond = false;
        try{
            String update_pass = "UPDATE login SET password = '" + new_password.trim()+ "' WHERE id_login = '" + id_user + "'";
            db.execSQL(update_pass);
            Log.d("Data", "onCreate: " + update_pass);
            cond = true;
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return cond;
    }

    public boolean insertCash(int id_user, String tgl, int nominal, String keterangan, int jenis){
        db = this.getWritableDatabase();
        boolean cond = false;
        try{
            String insert_cash = "INSERT INTO log (id_user,tgl,nominal,keterangan,jenis) VALUES ('" + id_user+ "','" + tgl+ "','" + nominal + "','" + keterangan+ "','" + jenis+ "')";
            db.execSQL(insert_cash);
            Log.d("Data", "onCreate: " + insert_cash);
            cond = true;
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return cond;
    }

    public Cursor getDataCash(){
        Cursor cursor = null;
        try{
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM log",null);
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return  cursor;
    }

    public double getPengeluaran(int month,int year){
        double nominal = 0;
        try {
            String mth = String.valueOf(month);
            if(month < 10){
                mth = "0" + String.valueOf(month);
            }
            String query = "SELECT SUM(nominal) as pengeluaran FROM log WHERE jenis = '0' AND strftime('%Y', tgl) = '" + year + "' AND strftime('%m', tgl) = '" + mth + "'";
            Cursor c = db.rawQuery(query,null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                nominal = c.getDouble(c.getColumnIndex("pengeluaran"));
            }

        } catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return nominal;
    }

    public double getPemasukan(int month,int year){
        double nominal = 0;
        try {
            String mth = String.valueOf(month);
            if(month < 10){
                mth = "0" + String.valueOf(month);
            }
            String query = "SELECT SUM(nominal) as pemasukan FROM log WHERE jenis = '1' AND strftime('%Y', tgl) = '" + year + "' AND strftime('%m', tgl) = '" + mth + "'";
            Cursor c = db.rawQuery(query,null);
            if(c.getCount() > 0) {
                c.moveToFirst();
                nominal = c.getDouble(c.getColumnIndex("pemasukan"));
            }
        } catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return nominal;
    }

    public Cursor getData(){
        Cursor cursor = null;
        try{
            db = getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM log",null);
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            e.printStackTrace();
        }
        return  cursor;
    }
}


