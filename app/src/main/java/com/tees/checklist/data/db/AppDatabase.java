package com.tees.checklist.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.Utils;
import com.tees.checklist.data.dao.AnalisePessoalDao;
import com.tees.checklist.data.dao.AtividadeDao;
import com.tees.checklist.data.dao.ConfiguracoesDao;
import com.tees.checklist.data.dao.DiretoriaAtividadeDao;
import com.tees.checklist.data.dao.DiretoriaDao;
import com.tees.checklist.data.dao.EPISConvencionaisDao;
import com.tees.checklist.data.dao.EPISEspecificosDao;
import com.tees.checklist.data.dao.FuncionarioDao;
import com.tees.checklist.data.dao.InspecaoAPRDao;
import com.tees.checklist.data.dao.InspecaoEPCDao;
import com.tees.checklist.data.dao.InspecaoEPIDao;
import com.tees.checklist.data.dao.InspecaoVeicularDao;
import com.tees.checklist.data.dao.LocalidadeDao;
import com.tees.checklist.data.dao.RiscoSegurancaDao;
import com.tees.checklist.data.dao.UsuarioDao;
import com.tees.checklist.data.dao.VeiculoDao;
import com.tees.checklist.data.model.AnalisePessoal;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Diretoria;
import com.tees.checklist.data.model.DiretoriaAtividade;
import com.tees.checklist.data.model.EPISConvencionais;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.model.InspecaoEPC;
import com.tees.checklist.data.model.InspecaoEPI;
import com.tees.checklist.data.model.InspecaoVeicular;
import com.tees.checklist.data.model.Localidade;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.data.model.Usuario;
import com.tees.checklist.data.model.Veiculo;

/**
 * The Room database.
 */

@Database(entities =
        {AnalisePessoal.class, Atividade.class, Configuracoes.class, Diretoria.class, DiretoriaAtividade.class, EPISConvencionais.class, EPISEspecificos.class,
                Funcionario.class, InspecaoAPR.class, InspecaoEPC.class, InspecaoEPI.class, InspecaoVeicular.class, Localidade.class,
                RiscoSeguranca.class, Usuario.class, Veiculo.class}

        , version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AnalisePessoalDao analisePessoal();
    public abstract AtividadeDao atividade();
    public abstract ConfiguracoesDao configuracoes();
    public abstract DiretoriaDao diretoria();
    public abstract DiretoriaAtividadeDao diretoriaAtividade();
    public abstract EPISConvencionaisDao episConvencionais();
    public abstract EPISEspecificosDao episEspecificos();
    public abstract FuncionarioDao funcionario();
    public abstract InspecaoAPRDao inspecaoAPR();
    public abstract InspecaoEPCDao inspecaoEPC();
    public abstract InspecaoEPIDao inspecaoEPI();
    public abstract InspecaoVeicularDao inspecaoVeicular();
    public abstract LocalidadeDao localidade();
    public abstract RiscoSegurancaDao riscoSeguranca();
    public abstract UsuarioDao usuario();
    public abstract VeiculoDao veiculo();




    private static final String DB_NAME = Constants.DatabaseName;
    private static AppDatabase instance;

    public AppDatabase() {
    }

    // Use this to call on any place
    public static AppDatabase getInstance() {
        return instance;
    }

    // Use once to Create and setup the object
    public static AppDatabase setInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    closeBDConn();
                    DatabaseCopier db = new DatabaseCopier();
                         db.copyAttachedDatabase(context, DB_NAME);
                        instance = Room.databaseBuilder(context,
                                AppDatabase.class, Utils.getStorageDirectory("database",false).getAbsolutePath()+"/"+DB_NAME)
                                //.fallbackToDestructiveMigration()
                                .build();
                 }
            }
        }
        return instance;
    }

    public static void disposeInstance() {
        instance = null;
    }

    public static boolean isDatabaseIntegrityOk(){
        return instance.mDatabase.isDatabaseIntegrityOk();
    }

    public static void closeBDConn() {
        if (AppDatabase.getInstance() != null) {
            if (AppDatabase.getInstance().isOpen()) {
                AppDatabase.getInstance().close();
            }
        }
        AppDatabase.disposeInstance();
    }
}