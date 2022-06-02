package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.DiretoriaAtividade;

@Dao
public abstract   class  DiretoriaAtividadeDao extends BaseDao<DiretoriaAtividade> {
    public DiretoriaAtividadeDao() {  super(TableNames.DIRETORIA_ATIVIDADE);  }
}
