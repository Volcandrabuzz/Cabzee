package com.example.cabzee;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;

    public static SharedPreferences.Editor editor;
    public Context context;
    int Private_Mode =0;

    private static final String Pref_Name="Login";
    private static final String Login="Is_Login";
    private static final String User_Id="User_Id";
    private static final String User_Type="User_Type";
    private static final String User_Phone="User_Phone";
    private static final String Country_Code="Country_Code";

    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(Pref_Name,Private_Mode);
        editor=sharedPreferences.edit();
    }

    public void CreateSession(String user_Id,String user_Type,String user_Phone,String Country_Code){
        editor.putBoolean(Login,true);
        editor.putString(User_Id,user_Id);
        editor.putString(User_Type,user_Type);
        editor.putString(User_Phone,user_Phone);
        editor.putString(Country_Code,Country_Code);

    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user=new HashMap<>();
        user.put(User_Id,sharedPreferences.getString(User_Id,null));
        user.put(User_Phone,sharedPreferences.getString(User_Phone,null));
        user.put(User_Type,sharedPreferences.getString(User_Type,null));
        user.put(Country_Code,sharedPreferences.getString(Country_Code,null));

        return user;
    }

}
