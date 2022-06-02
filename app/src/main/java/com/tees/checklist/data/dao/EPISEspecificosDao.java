package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.EPISEspecificos;

@Dao
public abstract   class EPISEspecificosDao extends BaseDao<EPISEspecificos> {
    public EPISEspecificosDao() {  super(TableNames.EPIS_ESPECIFICOS);  }
}
