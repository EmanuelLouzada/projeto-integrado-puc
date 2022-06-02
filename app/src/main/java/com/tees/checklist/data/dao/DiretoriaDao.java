package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Diretoria;

@Dao
public abstract   class  DiretoriaDao extends BaseDao<Diretoria> {
    public DiretoriaDao() {  super(TableNames.DIRETORIA);  }
}
