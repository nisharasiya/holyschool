package rahul.com.newholypublicschool.Utils;

import android.graphics.Bitmap;

/**
 * Created by owner on 13/6/16.
 */
public class SingleInstance {
    private static SingleInstance instance;
    public Bitmap recentBitmap;

    private SingleInstance(){
    }
    public static SingleInstance getInstance(){
        if(instance==null){
            instance=new SingleInstance();
        }
        return instance;
    }
}
