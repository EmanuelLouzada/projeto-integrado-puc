package com.tees.checklist.commons;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Utils {


    public static boolean checkDeviceLocale(Configuration config, Context context) {
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (!language.equals("pt") && !country.equals("BR")) {
            return false;
        }
        return true;

    }

    public static void setLocale(Configuration config, Context context) {
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (!language.equals("pt") && !country.equals("BR")) {
            Locale locale = new Locale("pt", "BR");
            Locale.setDefault(locale);
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public static BigDecimal arredondarNumero(BigDecimal valor, int decimais) {

        //return valor;
        boolean valorNegativo = false;
        BigDecimal novoValor;
        BigDecimal valorDecimal = valor;
        if ((decimais <= 0)) {
            String novoValorTruncado = valor.toPlainString().replace(',', '.');
            String retornoTruncado = novoValorTruncado;
            if (novoValorTruncado.split("\\.").length > 0) {
                retornoTruncado = novoValorTruncado.split("\\.")[0];
            }
            return new BigDecimal(retornoTruncado);
        }

        BigDecimal multiplicador = new BigDecimal(Math.pow(10, decimais), new MathContext(0));
        novoValor = valor.multiply(multiplicador);
        if ((novoValor.remainder(multiplicador).equals(BigDecimal.ZERO))) {
            return valor;
        }

        if ((novoValor.signum() < 0)) {
            novoValor = novoValor.multiply(new BigDecimal(-1));
            valorNegativo = true;
        }

        String valorTruncado = novoValor.toPlainString().replace(',', '.');
        String[] aux = valorTruncado.split("\\.");
        if (aux.length != 0) {
            String retorno = valorTruncado.split("\\.")[0];

            while ((retorno.length() < decimais)) {
                retorno = ("0" + retorno);
            }

            retorno = (retorno.substring(0, (retorno.length() - decimais)) + ("." + retorno.substring((retorno.length() - decimais))));

            return valorNegativo ? (new BigDecimal(retorno).multiply(new BigDecimal(-1))) : new BigDecimal(retorno);

        } else {
            return BigDecimal.ZERO;
        }
    }




    public static String formatDecimal(BigDecimal x, String format) {
        return formatDecimal(x, format, new Locale("pt", "BR"));
    }


    public static String formatDecimal(BigDecimal x, String format, Locale locale) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(format);
        return decimalFormat.format(x);
    }


    public static String formatDecimalString(BigDecimal valor, int decimals) {

        String format = "#0.";
        for (int i = 1; i <= decimals; i++) {
            format += "0";
        }


        return formatDecimal(arredondarNumero(valor, decimals), format).replace(',', '.');
    }


    public static String zip(File directory, String zipFileName, String typeOnError) {
        int BUFFER = 5000;
        try {

            for (File file : directory.listFiles()) {
                if (file.getName().endsWith("zip") || file.getName().endsWith("tmp"))
                    file.delete();
            }

            BufferedInputStream origin = null;

            File zipfile = new File(directory, "/" + zipFileName);
            FileOutputStream dest = new FileOutputStream(zipfile);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));


            byte data[] = new byte[BUFFER];
            for (File file : directory.listFiles()) {

                if (!file.isDirectory() && !file.getName().endsWith("zip")) {
                    String fileName = file.getName();
                    FileInputStream fi = new FileInputStream(file);
                    origin = new BufferedInputStream(fi, BUFFER);
                    ZipEntry entry = new ZipEntry(fileName.substring(fileName.lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    origin.close();
                }
            }

            out.close();
            return "zip";
        } catch (Exception e) {
            e.printStackTrace();
            return typeOnError;
        }
    }

    public static <T> List<T> filter(List<T> list, Pre<T, Boolean> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().filter(p -> pre.get(p)).collect(Collectors.toList());
        } else {
            List<T> col = new ArrayList<T>();
            for (int i = 0; i < list.size(); i++)
                if (pre.get(list.get(i)))
                    col.add(list.get(i));
            return col;
        }
    }


    public static File getStorageDirectory(String child, boolean mkDir) {
        if(mkDir){
            return getStorageDirectory(child);
        }else{
            return fileFactory(child);
        }
    }


    public static File getStorageDirectory(String child) {
        File root = fileFactory(child);

        if (root != null) {
            if (!root.exists()) {
                root.mkdirs();
            }
        }

        return root;
    }

    public static File fileFactory(String child) {
        File root;

        if (hasStorage(true)) {
            root = SingletonContext.get().getExternalFilesDir(child);
        } else {
            root = new File(SingletonContext.get().getFilesDir(), child);
        }
        return root;
    }

    public static String criarNomeArquivoFoto(String numCliente, String rota,
                                              String dataFoto, String tipoFoto, String contador) {
        String nomeArquivo = dataFoto + "_UC_" + numCliente + "_Lote_" + rota;

        if (tipoFoto.equals("1")) {
            nomeArquivo = nomeArquivo + "_opcional";
        }

        if (tipoFoto.equals("3")) {
            nomeArquivo = nomeArquivo + "_fiscalizacao";
        }
        //nomeArquivo = nomeArquivo + "_" + contador;

        return nomeArquivo;
    }

    public static <T> T toTrim(T t) {
        Field[] fields = t.getClass().getFields();
        for (Field field : fields) {
            try {
                if (field.get(t) instanceof String) {
                    Object o = field.get(t);
                    String s = (String) o;
                    field.set(t, s.trim());
                }
            } catch (IllegalAccessException e) {
                // log.info("Error converting field "+ field.getName() );
            }
        }
        return t;
    }

    public static String capitalize(String str) {
        if (str == null) return null;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    static public boolean hasStorage(boolean requireWriteAccess) {
        //TODO: After fix the bug,  add "if (VERBOSE)" before logging errors.
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (requireWriteAccess) {
                boolean writable = checkFsWritable();
                return writable;
            } else {
                return true;
            }
        } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private static boolean checkFsWritable() {
        // Create a temporary file to see whether a volume is really writeable.
        // It's important not to put it in the root directory which may have a
        // limit on the number of files.

        File directory = SingletonContext.get().getExternalFilesDir("DCIM");

        if (directory != null && !directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        return directory != null && directory.canWrite();
    }

    public interface Pre<T, R> {
        R get(T item);
    }


    public static void enableHttpResponseCache(File cacheDir) {
        try {
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            File httpCacheDir = new File(cacheDir, "http");
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
            Log.d("Cache Erro", "HTTP response cache is unavailable.");
        }
    }

}
