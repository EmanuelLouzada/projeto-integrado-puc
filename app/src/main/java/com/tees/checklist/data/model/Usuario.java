package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.USUARIO)
    public class Usuario {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        @NonNull
        public int id;

        @ColumnInfo(name = "no_usuario")
        @NonNull
        public String no_usuario;

        @ColumnInfo(name = "no_login")
        @NonNull
        public String no_login;

        @ColumnInfo(name = "de_senha")
        @NonNull
        public String de_senha;

        @ColumnInfo(name = "ts_atualizacao")
        @TypeConverters(DateConverter.class)
        public Date ts_atualizacao;

    }
