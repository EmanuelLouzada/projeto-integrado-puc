package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Veiculo;

@Dao
public abstract   class  VeiculoDao extends BaseDao<Veiculo> {
    public VeiculoDao() {  super(TableNames.VEICULO);  }
}
