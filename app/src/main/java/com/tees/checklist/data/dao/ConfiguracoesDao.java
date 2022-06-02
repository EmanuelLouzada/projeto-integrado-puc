package com.tees.checklist.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Configuracoes;

@Dao
public abstract   class  ConfiguracoesDao extends BaseDao<Configuracoes> {
    public ConfiguracoesDao() {  super(TableNames.CONFIGURACOES);  }

    @Query("UPDATE tb016_configuracoes SET carregado = :carregado")
    public abstract void updateFlagCarregado(String carregado);

    @Query("UPDATE tb016_configuracoes SET carregado = :carregado, hr_trans =:hr_trans" )
    public abstract void updateFields(String carregado, Long hr_trans );
}
