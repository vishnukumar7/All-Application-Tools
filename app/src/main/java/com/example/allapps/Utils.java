package com.example.allapps;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.example.allapps.model.AllFacer;
import com.example.allapps.model.FolderModel;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.allapps.database.DataBaseContext.FILE_STORAGE_APP;


public class Utils {


    public static void logcatMarvel(String str) {
        Log.i("////marvel", str);
    }

    public static void logcatDemoNuts(String str) {
        Log.d("demonuts", str);
    }

    private static void sout(String str) {
        Log.i("////sout ", str);
    }


    public static String getFileSizeMegaBytes(File file) {
        double size = (double) file.length() / (1024 * 1024);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(size) + " MB";
    }

    private static String getFileSizeKiloBytes(File file) {
        double size = (double) file.length() / (1024);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(size) + " KB";
    }

    private static String getFileSizeBytes(File file) {
        double size = (double) file.length();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(size) + " Bytes";
    }

    private static void log(String str) {
        Log.i(":// values", str);
    }

    public static List<ResolveInfo> getAllPackage(Context context) {
        List<ResolveInfo> applicationInfos = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Intent intent = new Intent(ACTION_MAIN);
            intent.addCategory(CATEGORY_LAUNCHER);
            applicationInfos = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_UNINSTALLED_PACKAGES);

        }
        return applicationInfos;

    }



    @SuppressLint("SetTextI18n")
    public static void setTimeText(TextView duration, int time) {
        long sec = toSeconds((long) time);
        long min = toMinutes((long) time);
        System.out.println("//sec  : " + sec);
        System.out.println("//min  : " + min);
        if (min > 9 && sec > 9)
            duration.setText(min + ":" + sec);
        else {
            if (min <= 9 && sec <= 9)
                duration.setText("0" + min + ":0" + sec);
            else if (min <= 9)
                duration.setText("0" + min + ":" + sec);
            else duration.setText(min + ":0" + sec);
        }
    }

    private static long toSeconds(long time) {
        return (time / 1000) % 60;
    }

    private static long toMinutes(long time) {
        return (time / 1000) / 60;
    }

    public static String getFileStorage(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        String localFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FILE_STORAGE_APP + File.separator + "Images";
        return localFile + File.separator + fileName;
    }


    public static void downloadImage(String url, Context context) {
        String name = url.substring(url.lastIndexOf('/') + 1);
        String localFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FILE_STORAGE_APP + File.separator + "Images";
        String localFileName = localFile + File.separator + name;
        File file = new File(localFile);
        File imageFile = new File(localFileName);
        if (!imageFile.exists()) {
            file.mkdirs();
            /*
            DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
                    .setTitle(localFileName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalFilesDir(context,localFile,fileName)
                    .setDestinationInExternalPublicDir(localFile,fileName)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true);
            downloadManager.enqueue(request);*/
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(name);
            request.setDescription("Downloading");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setVisibleInDownloadsUi(false);
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d(TAG, localFile);
            // request.setDestinationUri(Uri.parse(url));
            // request.setDestinationInExternalFilesDir(context,localFile, File.separator+fileName);
            request.setDestinationUri(Uri.fromFile(imageFile));

            downloadManager.enqueue(request);
        }

    }


    public static void viewProperties(final Context context, final String path) {
        final File file = new File(path);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.file_details, null);
        ImageView imageType = dialogView.findViewById(R.id.fileImageType);
        imageType.setImageResource(getFileImageType(path));
        TextView fileName = dialogView.findViewById(R.id.name);
        fileName.setText(file.getName());
        TextView fileSize = dialogView.findViewById(R.id.fileSize);
        String fileValue = getFileSizeBytes(file);
        if (fileValue.length() > 11)
            fileValue = getFileSizeKiloBytes(file);
        if (fileValue.length() > 8)
            fileValue = getFileSizeMegaBytes(file);
        fileSize.setText(fileValue);
        TextView fileLocation = dialogView.findViewById(R.id.fileLocation);
        fileLocation.setText(file.getAbsolutePath());
        TextView fileImageName = dialogView.findViewById(R.id.fileImageName);
        if (file.isDirectory())
            fileImageName.setText("Directory");
        else
            fileImageName.setText(getFileType(path));
        TextView fileLastModified = dialogView.findViewById(R.id.fileModified);
        fileLastModified.setText(new Date(file.lastModified()).toString());
        builder.setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .setNeutralButton("RENAME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callRename(file, path, context);
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public static void createNewFolder(final Context context, String path) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.create_new_folder, null);
        EditText newFileName = dialogView.findViewById(R.id.createNewFileName);
        final File file = new File(path + File.separator + newFileName.getText().toString());
        builder.setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (file.exists())
                            Toast.makeText(context, "File Name is already created", Toast.LENGTH_SHORT).show();
                        else {
                            boolean success = file.mkdir();
                            if (success)
                                Toast.makeText(context, "Directory Created", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, "Failed - Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();

    }


    private static void callRename(final File file, String path, Context context) {
        final View renameView = LayoutInflater.from(context).inflate(R.layout.rename_view, null);
        final EditText fileName = renameView.findViewById(R.id.renameFile);
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(renameView)
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileNewName = fileName.getText().toString();
                        String[] split = file.getAbsoluteFile().toString().split("\\.");
                        if (fileNewName.length() > 0)
                            file.renameTo(new File(file.getParentFile(), fileNewName.trim() + "." + split[split.length - 1]));

                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }


    public static String getFileType(String path) {
        path=path.toLowerCase();
        if (path.endsWith(".mp3") )
            return "Audio format";
        else if (path.endsWith(".mp4") || path.endsWith(".avi") || path.endsWith(".3gp"))
            return "Video format";
        else if (path.endsWith(".jpeg") || path.endsWith(".jpg") || path.endsWith(".png"))
            return "Image format";
        else if (path.endsWith(".pdf"))
            return "PDF format";
        else if (path.endsWith(".doc"))
            return "Document format";
        else if (path.endsWith(".apk"))
            return "APK format";
        else if (path.endsWith(".zip"))
            return "ZIP format";
        else if (path.endsWith(".exe"))
            return "EXE format";
        else
            return "Not Found Format";
    }

    public static int getFileImageType(String path) {
        String image = getFileType(path);
        switch (image) {
            case "Audio format":
                return R.drawable.music_icon;
            case "Video format":
                return R.drawable.videoicon;
            case "Image format":
                return R.drawable.image_icon;
            case "Document format":
                return R.drawable.doc;
            case "APK format":
                return R.drawable.ic_apk;
            case "PDF format":
                return R.drawable.pdf;
            case "ZIP format":
                return R.drawable.zip_icon;
            case "EXE format":
                return R.drawable.exe_icon;
            default:
                return R.drawable.file_icon;

        }
    }

    public static void openFileType(Context context, String path) {
        String image = getFileType(path);
        switch (image) {
            case "Audio format":
                // return R.drawable.music_icon;
            case "Video format":
                // return R.drawable.videoicon;
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("filePath", path);
                context.startActivity(intent);
            case "Image format":
                // return R.drawable.image_icon;
            case "Document format":
                // return R.drawable.doc;
            case "APK format":
                //return R.drawable.ic_apk;
            case "PDF format":
                // return R.drawable.pdf;
            case "ZIP format":
                //return R.drawable.zip_icon;
            case "EXE format":
                // return R.drawable.exe_icon;
            default:
                // return R.drawable.file_icon;

        }
    }


    private static ArrayList<FolderModel> getFolderData(Context context, Uri fileUri, String[] columnValues) {
        ArrayList<String> picPaths = new ArrayList<>();
        ArrayList<FolderModel> picFolders = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(fileUri, columnValues, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();

                do {
                    FolderModel folds = new FolderModel();
                    String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder + "/"));
                    folderpaths = folderpaths + folder + "/";
                    if (!picPaths.contains(folderpaths)) {
                        picPaths.add(folderpaths);

                        folds.setFolderPath(folderpaths);
                        folds.setFolderName(folder);
                        folds.setFirstPics(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                        folds.addPics();
                        picFolders.add(folds);
                    } else {
                        for (int i = 0; i < picFolders.size(); i++) {
                            if (picFolders.get(i).getFolderPath().equals(folderpaths)) {
                                picFolders.get(i).setFirstPics(datapath);
                                picFolders.get(i).addPics();
                            }
                        }
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < picFolders.size(); i++) {
            Log.d("picture folders", picFolders.get(i).getFolderName() + " and path = " + picFolders.get(i).getFolderPath() + " " + picFolders.get(i).getPics());
        }
        return picFolders;
    }

    public static ArrayList<FolderModel> getPicturePaths(Context context) {
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, //filepath
                MediaStore.Images.Media.DISPLAY_NAME, //file name
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME //folder name
        };
        return getFolderData(context, allImagesuri, projection);
    }

    public static ArrayList<FolderModel> getVideoPaths(Context context) {
        Uri allImagesuri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.BUCKET_ID};

        return getFolderData(context, allImagesuri, projection);

    }

    public static ArrayList<FolderModel> getAudioPaths(Context context) {

        Uri allImagesuri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ALBUM
        };
        ArrayList<String> picPaths = new ArrayList<>();
        ArrayList<FolderModel> picFolders = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();

                do {
                    FolderModel folds = new FolderModel();
                    //  String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));

                    // String folderpaths = datapath.substring(0, datapath.indexOf(folder + "/"));
                    //  String folderpaths = datapath.split(folder+"/")[0];
                    File folderpaths = new File(datapath);
                    System.out.println("//pathh : " + folderpaths.getParent());
                    //folderpaths = folderpaths + folder + "/";
                    String folder = folderpaths.getParentFile().getName();
                    System.out.println("//path : " + folder + " " + datapath);
                    if (!picPaths.contains(folderpaths.getParent())) {
                        picPaths.add(folderpaths.getParent());

                        folds.setFolderPath(folderpaths.getParent());
                        folds.setFolderName(folder);
                        folds.setFirstPics(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                        folds.addPics();
                        picFolders.add(folds);
                    } else {
                        for (int i = 0; i < picFolders.size(); i++) {
                            if (picFolders.get(i).getFolderPath().equals(folderpaths.getParent())) {
                                picFolders.get(i).setFirstPics(datapath);
                                picFolders.get(i).addPics();
                            }
                        }
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < picFolders.size(); i++) {
            Log.d("picture folders", picFolders.get(i).getFolderName() + " and path = " + picFolders.get(i).getFolderPath() + " " + picFolders.get(i).getPics());
        }
        return picFolders;
    }

    public static ArrayList<AllFacer> getAllImagesByFolder(String path, Context context) {
        Uri allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        return getFileFromFolder(context, allVideosuri, projection, path);
    }

    public static ArrayList<AllFacer> getAllVideoByFolder(String path, Context context) {

        Uri allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.DURATION
        };
        System.out.println("////foldertype : " + path);
        return getFileFromFolder(context, allVideosuri, projection, path);
    }

    private static ArrayList<AllFacer> getFileFromFolder(Context context, Uri videoUri, String[] column, String path) {
        ArrayList<AllFacer> images = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(videoUri, column, MediaStore.Video.Media.DATA + " like ? ", new String[]{"%" + path + "%"}, null);
        // Cursor cursor = context.getContentResolver().query( allVideosuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    AllFacer pic = new AllFacer();

                    pic.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));

                    pic.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

                    pic.setFileSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                    File file = new File(pic.getFilePath());
                    System.out.println("//file path : "+file.getAbsolutePath());
                    System.out.println("//file parent path : "+file.getParent());
                    if (path.equalsIgnoreCase(file.getParent()) || path.equalsIgnoreCase(file.getParent()+"/"))
                        images.add(pic);
                } while (cursor.moveToNext());
                cursor.close();
                ArrayList<AllFacer> reSelection = new ArrayList<>();
                for (int i = images.size() - 1; i > -1; i--) {
                    reSelection.add(images.get(i));
                }
                images = reSelection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public static ArrayList<AllFacer> getAllAudioByFolder(String path, Context context) {
        ArrayList<AllFacer> images = new ArrayList<>();
        Uri allVideosuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE};
        Cursor cursor = context.getContentResolver().query(allVideosuri, projection, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%" + path + "%"}, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    AllFacer pic = new AllFacer();

                    pic.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));

                    pic.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

                    pic.setFileSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                    System.out.println("//audio path : " + pic.getFilePath());
                    File file = new File(pic.getFilePath());
                    if (path.equalsIgnoreCase(file.getParent()))
                        images.add(pic);
                } while (cursor.moveToNext());
                cursor.close();
            }

            ArrayList<AllFacer> reSelection = new ArrayList<>();
            for (int i = images.size() - 1; i > -1; i--) {
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public static ArrayList<AllFacer> getFilesMEdisStore(String currentPath, Context context) {
        Uri filesUri = MediaStore.Files.getContentUri("external");
        ArrayList<AllFacer> folderModels = new ArrayList<>();
        //  MediaStore.Video.Thumbnails.
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE;
        //Cursor cursor = context.getContentResolver().query( allVideosuri, projection, null, null,null);
        Cursor cursor = context.getContentResolver().query(filesUri, null, selection, null, "title ASC");

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String path = "_data";
                File file = new File(cursor.getString(cursor.getColumnIndex(path)));
                if (currentPath.equalsIgnoreCase(file.getParent())) {
                    AllFacer model = new AllFacer();
                    System.out.println("//Video Path : column " + "title" + " value : " + cursor.getString(cursor.getColumnIndex("title")));

                    model.setFilePath(cursor.getString(cursor.getColumnIndex("_data")));
                    model.setFileSize(cursor.getLong(cursor.getColumnIndex("_size")));
                    model.setExtensionType(cursor.getString(cursor.getColumnIndex("mime_type")));
                    model.setFolderName(cursor.getString(cursor.getColumnIndex("bucket_display_name")));
                    model.setDateModified(cursor.getLong(cursor.getColumnIndex("date_modified")));
                    model.setMediaType(cursor.getInt(cursor.getColumnIndex("media_type")));
                    model.setFile(new File(model.getFilePath()).isFile());
                    if(model.isFile())
                        model.setFileName(cursor.getString(cursor.getColumnIndex("_display_name")));
                    else
                        model.setFileName(cursor.getString(cursor.getColumnIndex("title")));
                    folderModels.add(model);

                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return folderModels;

    }


}

/*

2020-01-28 14:28:21.829 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column _data value : /storage/emulated/0/DCIM/Camera/IMG_20200125_163757.jpg
2020-01-28 14:28:21.829 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column _size value : 4486951
2020-01-28 14:28:21.829 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column format value : 14337
2020-01-28 14:28:21.829 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column parent value : 130151
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column date_added value : 1579950477
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column date_modified value : 1579950477
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column mime_type value : image/jpeg
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column title value : IMG_20200125_163757
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column description value : null
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column _display_name value : IMG_20200125_163757.jpg
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column picasa_id value : null
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column orientation value : 0
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column latitude value : null
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column longitude value : null
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column datetaken value : 1579950477496
2020-01-28 14:28:21.830 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column mini_thumb_magic value : -870639894354429929
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column bucket_id value : -1739773001
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column bucket_display_name value : Camera
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column isprivate value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column title_key value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column artist_id value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column album_id value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column composer value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column track value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column year value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_ringtone value : null
2020-01-28 14:28:21.831 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_music value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_alarm value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_notification value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_podcast value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column album_artist value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column duration value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column bookmark value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column artist value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column album value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column resolution value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column tags value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column category value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column language value : null
2020-01-28 14:28:21.832 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column mini_thumb_data value : null
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column name value : null
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column media_type value : 1
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column old_id value : null
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column storage_id value : 65537
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column is_drm value : null
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column width value : 4000
2020-01-28 14:28:21.833 28310-28310/com.novatium.android.myapplication I/System.out: //Video Path : column height value : 2000*/
