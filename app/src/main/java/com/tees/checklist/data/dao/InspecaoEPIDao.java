package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.InspecaoEPI;

@Dao
public abstract   class InspecaoEPIDao extends BaseDao<InspecaoEPI> {
    public InspecaoEPIDao() {  super(TableNames.INSPECAO_EPI);  }
}
