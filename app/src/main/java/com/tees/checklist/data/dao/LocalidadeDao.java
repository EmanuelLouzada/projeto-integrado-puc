package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Localidade;

@Dao
public abstract   class  LocalidadeDao extends BaseDao<Localidade> {
    public LocalidadeDao() {  super(TableNames.LOCALIDADE);  }
}
