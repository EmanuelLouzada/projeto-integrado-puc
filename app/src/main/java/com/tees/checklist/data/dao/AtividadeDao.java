package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Atividade;

@Dao
public abstract   class  AtividadeDao extends BaseDao<Atividade> {
    public AtividadeDao() {  super(TableNames.ATIVIDADE);  }
}
