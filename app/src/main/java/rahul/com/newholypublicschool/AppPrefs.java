package rahul.com.newholypublicschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class AppPrefs {

    private Context context;

    private static AppPrefs ourInstance;
    private SharedPreferences preferences;

    public static AppPrefs getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppPrefs(context);
        }
        return ourInstance;
    }

    private AppPrefs(Context context) {
        this.context = context;
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return preferences;
    }

    public <T> void putData(String key, T obj) {
        SharedPreferences.Editor editor = getPreferences().edit();
        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Integer) {
            editor.putInt(key, ((Integer) obj).intValue());
        }else if (obj instanceof Long) {
            editor.putLong(key, ((Long) obj).longValue());
        }
        else if (obj instanceof Double) {
            editor.putLong(key, ((Double) obj).longValue());
        }

        editor.commit();
    }


    public <T> T getData(String key, T obj) {
        if (obj instanceof String) {
            return (T) getPreferences().getString(key, (String) obj);
        } else if (obj instanceof Boolean) {
            return (T) (Boolean) getPreferences().getBoolean(key, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Integer) {
            return (T) (Integer) getPreferences().getInt(key, ((Integer) obj).intValue());
        }else if (obj instanceof Long) {
            return (T) (Long) getPreferences().getLong(key, ((Long) obj).longValue());
        }

        return null;
    }

    public void removeKeyData(String key)
    {
         getPreferences().edit().remove(key).commit();
    }
    public void clearAll()
    {
        getPreferences().edit().clear().commit();
    }

    public Typeface getTypeFaceByName(String fontName) {
        return Typeface.createFromAsset(context.getAssets(), fontName);
    }





    public static String getDateDifference(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dateFormat.parse(date + "GMT+05:30"));
        return getTimeToShow(c1.getTimeInMillis(), c.getTimeInMillis());
    }

    private static String getTimeToShow(long post, long now) {
        long diff = (now - post) / 1000;
        long day = diff / (24 * 60 * 60);
        long hour = diff / (60 * 60);
        long min = diff / (60);
        if (day > 0) {
            return day + " day";
        }
        if (hour > 0) {
            return hour + " hr";
        }
        if (min > 0) {
            return min + " min";
        } else {
            return 0 + " min";
        }
    }

}
