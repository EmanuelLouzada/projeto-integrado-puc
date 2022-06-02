package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.InspecaoAPR;

@Dao
public abstract   class InspecaoAPRDao extends BaseDao<InspecaoAPR> {
    public InspecaoAPRDao() {  super(TableNames.INSPECAO_APR);  }
}
