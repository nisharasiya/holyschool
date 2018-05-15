package rahul.com.newholypublicschool.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 4/24/2017.
 */
//SharedPreferences manager class
public class SharedPresencesUtility {

    //SharedPreferences file name
    private static String SHARED_PREFS_FILE_NAME = "my_app_shared_prefs";

    //here you can centralize all your shared prefs keys
    public static String KEY_MY_SHARED_BOOLEAN = "my_shared_boolean";
    public static String KEY_MY_SHARED_FOO = "my_shared_foo";
    private Context context;

    public SharedPresencesUtility(Context context) {
        this.context = context;
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }


    public static String getUserId(Context context) {
        return getPrefs(context).getString("UserId", "");
    }

    //Strings
    public static void setUserLoginId(Context context, String value) {
        getPrefs(context).edit().putString("UserId", value).commit();
    }

    public static String getPassword(Context context) {
        return getPrefs(context).getString("Password", "");
    }

    //Strings
    public static void setUserPassword(Context context, String value) {
        getPrefs(context).edit().putString("Password", value).commit();
    }

    public static String getNumberForgot(Context context) {
        return getPrefs(context).getString("NumberForgot", "");
    }

    //Strings
    public static void setNumberForgot(Context context, String value) {
        getPrefs(context).edit().putString("NumberForgot", value).commit();
    }

    public static String getStudentName(Context context) {
        return getPrefs(context).getString("Student_Name", "");
    }

    //Strings
    public static void setStudentName(Context context, String value) {
        getPrefs(context).edit().putString("Student_Name", value).commit();
    }

    public static String getRegistration(Context context) {
        return getPrefs(context).getString("Registration_no", "");
    }

    //Strings
    public static void setRegistration(Context context, String value) {
        getPrefs(context).edit().putString("Registration_no", value).commit();
    }

    public static String getPdfLink(Context context) {
        return getPrefs(context).getString("imageURL", "");
    }

    //Strings
    public static void setPdfLink(Context context, String value) {
        getPrefs(context).edit().putString("imageURL", value).commit();
    }

    public static String getSyllabusPdfLink(Context context) {
        return getPrefs(context).getString("imageURL", "");
    }

    //Strings
    public static void setSyllabusPdfLink(Context context, String value) {
        getPrefs(context).edit().putString("imageURL", value).commit();
    }

    public static String getCircularPdfLink(Context context) {
        return getPrefs(context).getString("imageURL", "");
    }

    //Strings
    public static void setCircularPdfLink(Context context, String value) {
        getPrefs(context).edit().putString("imageURL", value).commit();
    }

    public static String getResultPdfLink(Context context) {
        return getPrefs(context).getString("imageURL", "");
    }

    //Strings
    public static void setResultPdfLink(Context context, String value) {
        getPrefs(context).edit().putString("imageURL", value).commit();
    }






}

