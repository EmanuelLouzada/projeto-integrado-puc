package com.tees.checklist.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tees.checklist.base.BaseDao;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.model.Usuario;

import java.util.List;

@Dao
public abstract   class  UsuarioDao extends BaseDao<Usuario> {
    public UsuarioDao() {  super(TableNames.USUARIO);  }

    @Query("SELECT * FROM tb001_usuario WHERE trim(no_login) = trim(:username)")
    public abstract List<Usuario> getByUser(String username);

}
