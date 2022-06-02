package com.tees.checklist.commons;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LogArquivo {
    public static String _nameLog = "log.txt";
    public static boolean LogDetalhado;


    /// <summary>
    ///
    /// </summary>
    /// <param name="arquivo"></param>
    /// <param name="conteudo"></param>
    /// <param name="append"></param>
    public static void GravaArquivoTexto(String arquivo, String conteudo, boolean append) {

        Log.d("LOG", conteudo);
        //Barreto
        try {
            File root = Utils.getStorageDirectory("Logs");

            Calendar c = new GregorianCalendar();
            //gerar nome do arquivo
            DateFormat formato = new SimpleDateFormat("yyyyMMdd");
            String nome = "Checklist" + formato.format(c.getTime()) + ".log";
            //Apagar arquivo antigo de 7 dias atrás
            c.add(Calendar.DATE, -7);
            String arquivoApagar = "Checklist" + formato.format(c.getTime()) + ".log";
            for (File f : File.listRoots()) {
                if (f.getName().equals(arquivoApagar)) {
                    f.delete();
                }
            }

            File gpxfile = new File(root, nome);
            FileWriter writer = new FileWriter(gpxfile, append);
            writer.append(DateHelper.getFormattedDate("HH:mm:ss.SSS", new Date()) + " - " + conteudo);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.d("Erro", "Erro ao Gerar o Log");
            e.printStackTrace();
        }
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="conteudo"></param>
    public static void GravaArquivoTexto(String conteudo) {
        if (_nameLog == null) {
            _nameLog = "log.txt";
        }
        Log.d("LOG_ARQUIVO_TEXTO", conteudo);
        GravaArquivoTexto(_nameLog, conteudo, true);
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="conteudo"></param>
    public static void GravaArquivoTextoDetalhado(String conteudo) {
        if (LogDetalhado) {
            GravaArquivoTexto(conteudo);
        }
    }

    /// <summary>
    ///
    /// </summary>
    public static void UpdateLogDetalhado() {
        UpdateLogDetalhado("LOGFAT", "LF00");
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="nomtabla"></param>
    /// <param name="codigo"></param>
    public static void UpdateLogDetalhado(String nomtabla, String codigo) {
//            DataSet ds = new DataSet();
//            FachadaGenerica fachada = new FachadaGenerica(typeof(string));

//            String SQL = String.Format(
//                         @"SELECT valor_alf FROM tabla
//                           WHERE nomtabla = '{0}'
//                           AND codigo = '{1}';"
//                           , nomtabla
//                           , codigo);
//            while (true)
//            {
//                try
//                {
//                    ds = fachada.ConsultarSql(SQL, null);


//                    if (ds.Tables.Count == 0 || ds.Tables[0].Rows.Count == 0) logDetalhado = false;
//                    else
//                    {
//                        logDetalhado = Boolean.Parse(ds.Tables[0].Rows[0]["valor_alf"].ToString(CultureInfo.InvariantCulture));
//                    }
//                }
//                catch (Exception ex)
//                {
//                    LogArquivo.GravaArquivoTextoDetalhado("KeepAlive com banco de dados, nao conseguiu completar a transação. Verifique conexao com o banco.");
//                    LogArquivo.GravaArquivoTextoDetalhado(ex.Message);
//                    LogArquivo.GravaArquivoTextoDetalhado(ex.StackTrace);
//                    LogArquivo.GravaArquivoTextoDetalhado(ex.InnerException.Message);
//                    LogArquivo.GravaArquivoTextoDetalhado(ex.InnerException.StackTrace);
//                }

//                LogArquivo.GravaArquivoTextoDetalhado("Grava log detalhado: " + logDetalhado);
//                Thread.Sleep(5 * 60 * 1000); //Atualiza em 5 em 5 minutos.
//            }
    }

    /// <summary>
    ///
    /// </summary>
    /// <returns></returns>
    public static void StatusLog(Integer modoDebug) {
        LogDetalhado = (modoDebug == 1) ? true : false;
    }

}