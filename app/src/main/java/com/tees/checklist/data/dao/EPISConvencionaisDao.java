package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.EPISConvencionais;

@Dao
public abstract   class EPISConvencionaisDao extends BaseDao<EPISConvencionais> {
    public EPISConvencionaisDao() {  super(TableNames.EPIS_CONVENCIONAIS);  }
}
