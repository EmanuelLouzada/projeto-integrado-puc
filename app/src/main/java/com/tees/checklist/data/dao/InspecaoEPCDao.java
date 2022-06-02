package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.InspecaoEPC;

@Dao
public abstract   class InspecaoEPCDao extends BaseDao<InspecaoEPC> {
    public InspecaoEPCDao() {  super(TableNames.INSPECAO_EPC);  }
}
