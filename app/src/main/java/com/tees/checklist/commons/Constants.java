package com.tees.checklist.commons;
import java.util.Locale;

public class Constants {

    public static Locale INVARIANT_LOCALE = new Locale("en", "US");
    public static String FORMAT_DATE_BR = "dd/MM/yyyy";
    public static String FORMAT_DATE_TIME = "MM/dd/yyyy HH:mm:ss";
    public static String FORMAT_DATE_DB = "yyyy-MM-dd";
    public static String FORMAT_DATE_SLASH = "yyyy/MM/dd";
    public static String FORMAT_DATE_TIME_DB = "yyyy-MM-dd 00:00:00.000";
    public static String FORMAT_DATE_TIME_DB_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String FORMAT_DATE_API = "yyyy-MM-dd'T'HH:mm:ss";

    /*Controle de testes e impressão*/
    public static boolean TEST = false;
    public static boolean TEST_LOAD_UNLOAD = false;
    public static boolean LOADED_DB = false;
    public static boolean AUTOLOGIN = false;
    public static boolean PRINT = true;
    public static boolean ATUALIZA_SISLEC = true;

    public static final int LOCATION_REQUEST = 1000;

    /*Carga e descarga*/
    /// Checar versão do WebAPI e do coletor.
    public static boolean ChecarVersaoWS = false;
    /// Tempo de espera de resposta do Webservice
    public static int OnlineConnectTimeOut = 30;
    public static int OnlineReadWriteTimeOut = 30;
    public static String SECRET_KEY = "31BF3856AD364E36";
    public static String CARGA = "";
    public static String DESCARGA = "";
    /// Número de Tentativas de Carga
    public static final int TentativaCarga = 5;
    /// Caminho do WebAPI
    public final static String UrlWebAPI = "http://10.152.20.173:6401";

    public final static String IPWebAPI = "10.152.20.173";
    public final static String PortaWebAPI = "6401";

    /*Banco de Dados */
    public static String DatabaseName = "DBCheckListPRO.db";
    public static String DatabasePath = "database/" + DatabaseName;
    public static String INSERT = "inserir";
    public static String UPDATE = "atualizar";
    public static String DELETE = "deletar";


}
