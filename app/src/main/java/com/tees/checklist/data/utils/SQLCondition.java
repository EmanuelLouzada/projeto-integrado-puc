package com.tees.checklist.data.utils;

import java.util.ArrayList;
import java.util.List;

public class SQLCondition {

    private String nameField;
    private Object valueField;

    public SQLCondition() {

    }

    public SQLCondition(String nameField, Object valueField) {
        addCondition(nameField,valueField);
    }


    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }



    public Object getValueField() {
        return valueField;
    }

    public void setValueField(Object valueField) {
        this.valueField = valueField;
    }



    public List<SQLCondition> list = new ArrayList<>();


    public void  addCondition(String field, Object value){
        SQLCondition c = new SQLCondition();
        c.setNameField(field);
        c.setValueField(value);
        list.add(c);

    }

    public List<SQLCondition> getConditions(){
        return list;
    }

}
