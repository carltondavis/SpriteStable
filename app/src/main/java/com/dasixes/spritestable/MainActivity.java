package com.dasixes.spritestable;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends FragmentActivity {

    // Declare Variables
    public PersistentValues data = new PersistentValues();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action.compareTo(Intent.ACTION_VIEW) == 0) {
            String scheme = intent.getScheme();
            ContentResolver resolver = getContentResolver();

            if (scheme.compareTo(ContentResolver.SCHEME_CONTENT) == 0) {
                Uri uri = intent.getData();
                String name = getContentName(resolver, uri);

                Log.v("tag", "Content intent detected: " + action + " : " + intent.getDataString() + " : " + intent.getType() + " : " + name);
                InputStream input = null;
                try {
                    input = resolver.openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String importfilepath = "/sdcard/" + name;

                InputStreamToFile(input, importfilepath);

                ReadXMLFile readXMLFile = new ReadXMLFile();
                data.ResetDB(this);
                data.RestoreFromDB(this);
                readXMLFile.loadFile(importfilepath, this);
                data.SaveAllToDB();
                data.RestoreFromDB(this);
            }
            else if (scheme.compareTo(ContentResolver.SCHEME_FILE) == 0) {
                Uri uri = intent.getData();
                String name = uri.getLastPathSegment();

                Log.v("tag" , "File intent detected: " + action + " : " + intent.getDataString() + " : " + intent.getType() + " : " + name);

                ReadXMLFile readXMLFile = new ReadXMLFile();
                data.ResetDB(this);
                data.RestoreFromDB(this);
                readXMLFile.loadFile(uri.getPath(), this);
                data.SaveAllToDB();
                data.RestoreFromDB(this);

                /*
                InputStream input = null;
                try {
                    input = resolver.openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String importfilepath = "/sdcard/My Documents/" + name;
                InputStreamToFile(input, importfilepath);*/
            }
            else if (scheme.compareTo("http") == 0) {
                // TODO Import from HTTP!
            }
            else if (scheme.compareTo("ftp") == 0) {
                // TODO Import from FTP!
            }
        }else {
            data.RestoreFromDB(this);
        }
        setContentView(R.layout.viewpager_main);


        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//TODO: Move Stats, Skills, Qualities to Config page

    }

    private String getContentName(ContentResolver resolver, Uri uri){
        Cursor cursor = resolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        if (nameIndex >= 0) {
            return cursor.getString(nameIndex);
        } else {
            return null;
        }
    }

    private void InputStreamToFile(InputStream in, String file) {
        try {
            OutputStream out = new FileOutputStream(new File(file));

            int size = 0;
            byte[] buffer = new byte[1024];

            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }

            out.close();
        }
        catch (Exception e) {
            Log.e("MainActivity", "InputStreamToFile exception: " + e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        if (data != null) {
            data.SaveAllToDB();
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        if (data == null) {
            data.RestoreFromDB(this);
        }
    }

}