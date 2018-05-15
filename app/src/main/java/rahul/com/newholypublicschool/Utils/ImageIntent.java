package rahul.com.newholypublicschool.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rahul.com.newholypublicschool.Constants;

public class ImageIntent {

    public static final int PICK_CAMERA = 32;
    OnCameraClickedListener listener;

    public ImageIntent(OnCameraClickedListener listener) {
        this.listener = listener;
    }

    public ImageIntent() {
    }

    public static String getRealPathFromUri(Context activity, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public void showImageChooser(final Activity activity) {
        final CharSequence[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo from!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = createImageFile(activity);
                    if (file != null) {
                        try {
                            String currentPath = file.getAbsolutePath();
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                            cameraIntent.putExtra("return-data", false);
                            cameraIntent.putExtra("path", currentPath);
                            if(listener!=null){
                                listener.onCameraImage(currentPath);
                            }
                            activity.startActivityForResult(cameraIntent, Constants.PICK_CAMERA);
                        } catch (Exception e) {
                        }
                    }
                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(intent, Constants.PICK_GALLERY);
                }
            }
        });
        builder.show();
    }

	/*public File createImageFile() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File mFileTemp = null;
		//ContextWrapper contextWrapper=new ContextWrapper(activity);
		//String root=contextWrapper.getFilesDir().getAbsolutePath();
		String root=activity.getDir("my_sub_dir",Context.MODE_PRIVATE).getAbsolutePath();
		File myDir = new File(root + "/Img");
		if(!myDir.exists()){
			myDir.mkdirs();
		}
		try {
			mFileTemp=File.createTempFile(imageFileName,".jpg",myDir.getAbsoluteFile());
			Log.e("path",mFileTemp.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return mFileTemp;
	}*/

    public static File createImageFile(Activity activity) {
        String state = Environment.getExternalStorageState();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        File directory;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                directory=new File(Environment.getExternalStorageDirectory()+"/ldir");
                if(!directory.exists()){
                    directory.mkdirs();
                }
                mFileTemp = File.createTempFile(imageFileName, ".jpg", Environment.getExternalStorageDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                directory=new File(activity.getFilesDir()+ File.separator+"ldir");
                if(!directory.exists()){
                    directory.mkdirs();
                }
                mFileTemp = File.createTempFile(imageFileName, ".jpg",activity.getFilesDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mFileTemp;
    }


    public String saveImageToSdcard(Bitmap currentImage, Activity activity) {
        String path = "";
        File file = createImageFile(activity);
        FileOutputStream fout;
        try {
            fout = new FileOutputStream(file);
            currentImage.compress(Bitmap.CompressFormat.PNG, 70, fout);
            fout.flush();
            path = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            path = null;
        }
        return path;
    }

    public interface OnCameraClickedListener {
        void onCameraImage(String path);
    }


}
