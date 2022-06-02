package com.tees.checklist.data.dao;

import androidx.room.Dao;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.AnalisePessoal;

@Dao
public abstract   class  AnalisePessoalDao extends BaseDao<AnalisePessoal> {
    public AnalisePessoalDao() {  super(TableNames.ANALISE_PESSOAL);  }
}
