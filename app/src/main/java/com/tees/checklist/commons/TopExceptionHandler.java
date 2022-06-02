package com.tees.checklist.commons;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static androidx.core.content.ContextCompat.startActivity;

public class TopExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Intent intent = null;
    private Context context = null;

    public TopExceptionHandler(Context context, Intent intent) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.intent = intent;
        this.context = context;
    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-------------------------------\n\n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        report += "-------------------------------\n\n";

        try {
            File nomeDiretorio = Utils.getStorageDirectory("CrashLog");
            String nomeArquivo = "CrashLog.txt";
            File gpxfile = new File(nomeDiretorio, nomeArquivo);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(report);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Ocorreu um erro fatal na " +
                    "aplicação! Um log do erro foi gerado para análise!", Toast.LENGTH_LONG);
            startActivity(context, intent, null);

        } catch (IOException ioe) {
            // ...
        }

        // defaultUEH.uncaughtException(t, e);
    }
}