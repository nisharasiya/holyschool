package rahul.com.newholypublicschool;

/**
 * Created by hp on 9/2/2017.
 */

import android.content.Context;
import android.preference.PreferenceManager;

public class Prefs {
    static Context ctx;

    public Prefs(Context ctx){
        Prefs.ctx = ctx;
    }

    public static String isLogin() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("isLogin", "false");
    }

    public void setLogin(String isLogin) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("isLogin", isLogin).commit();
    }

    public String isForgot() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("isForgot", "false");
    }

    public void setForgot(String isForgot) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("isForgot", isForgot).commit();
    }


    public String getMobile() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("mobile_number", "false");
    }

    public void setMobile(String mobile) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("mobile_number", mobile).commit();
    }

    public String getNameForgot() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("name_by_forgot", "false");
    }

    public void setNameForgot(String name) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("name_by_forgot", name).commit();
    }


    public String getPin() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("pin", "false");
    }

    public void setPin(String pin) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("pin", pin).commit();
    }

    public String getNumberForgot() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("numberForgot", "false");
    }

    public void setNumberForgot(String number) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("numberForgot", number).commit();
    }

    public String getRoll() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("rollno", "false");
    }

    public void setRoll(String rollno) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("rollno", rollno).commit();
    }

    public String getDob() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("dob", "false");
    }

    public void setDob(String dob) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("dob", dob).commit();
    }

    public String getAge() {
        return 	PreferenceManager.getDefaultSharedPreferences(ctx).getString("age", "false");
    }

    public void setAge(String age) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("age", age).commit();
    }
}
