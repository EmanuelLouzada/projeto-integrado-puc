package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.RiscoSeguranca;

@Dao
public abstract   class  RiscoSegurancaDao extends BaseDao<RiscoSeguranca> {
    public RiscoSegurancaDao() {  super(TableNames.RISCO_SEGURANCA);  }
}
