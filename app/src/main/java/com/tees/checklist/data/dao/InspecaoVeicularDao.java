package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.InspecaoVeicular;

@Dao
public abstract   class  InspecaoVeicularDao extends BaseDao<InspecaoVeicular> {
    public InspecaoVeicularDao() {  super(TableNames.INSPECAO_VEICULAR);  }
}
