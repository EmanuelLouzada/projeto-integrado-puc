package com.tees.checklist.commons;
public class Messages {

    public static final String SUCCESS_MESSAGE = "ok";
    public static final String FAIL_INSERT_MESSAGE = "Falha ao inserir";
    public static final String FAIL_UPDATE_MESSAGE = "Falha ao atualizar";
    public static final String FAIL_DELETE_MESSAGE = "Falha ao excluir";
    public static final String GENERIC_ERROR_MESSAGE = "Erro!";
    public static final String WEBSERVICE_ERROR_MESSAGE = "Erro na comunicação com o webservice!";
    public static final String NO_CONNECTION_MESSAGE = "Falha na rede!";
    public static final String TIMEOUT_MESSAGE = "O tempo de espera da requisição chegou ao limite!Tente novamente!";
    public static  final String NO_DATA_FOUND =   "A consulta não retornou nenhum resultado";

    public static final String UPDATE_SUCCESS_MESSAGE = "Dados atualizados com sucesso!";
    public static final String INSERT_SUCCESS_MESSAGE = "Dados inseridos com sucesso!";
    public static final String SUCCESS_LOAD = "Carga Finalizada!";
    public static final String SUCCESS_UNLOAD = "Descarga Finalizada!";
    public static final String SUCCESS_PARTIAL_UNLOAD = "Descarga Parcial Finalizada!";

    public static String error(String operation, String table){
        return "Erro ao "+operation+". Tabela " + table;
    }

    public static String errorPostOnline(String processo){
        return "Ocorreu um erro na descarga de "+processo+". Tente descarregar novamente.";
    }
    public static String noOnlineDataFound(String table){
        return "A Tabela "+table+" não retornou dados do Webservice. ";
    }


}
