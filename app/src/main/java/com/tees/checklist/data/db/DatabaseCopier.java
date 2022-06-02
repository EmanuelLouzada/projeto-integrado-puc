package com.tees.checklist.data.db;

import android.content.Context;
import android.util.Log;

import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseCopier {
    private static final String TAG = DatabaseCopier.class.getSimpleName();


    public void copyAttachedDatabase(Context context, String databaseName) {


        String PATH = Utils.getStorageDirectory("database",false).getAbsolutePath();
        File file = new File(PATH,databaseName);
        if (file.exists()) {
            return;
        }

        final File dbPath = Utils.getStorageDirectory("database");

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open(Constants.DatabasePath);
            final OutputStream output = new FileOutputStream(dbPath+"/"+databaseName);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();
        }
        catch (IOException e) {
            Log.d(TAG, "Failed to open file", e);
            e.printStackTrace();
        }
    }

}