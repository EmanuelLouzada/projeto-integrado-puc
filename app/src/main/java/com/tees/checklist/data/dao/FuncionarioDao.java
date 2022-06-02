package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Funcionario;

@Dao
public abstract   class  FuncionarioDao extends BaseDao<Funcionario> {
    public FuncionarioDao() {  super(TableNames.FUNCIONARIO);  }
}
