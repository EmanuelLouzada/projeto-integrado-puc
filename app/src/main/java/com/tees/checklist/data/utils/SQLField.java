package com.tees.checklist.data.utils;

import java.util.ArrayList;
import java.util.List;

public class SQLField {

    private String nameField;
    private String valueField;

    public SQLField() {

    }

    public SQLField(String nameField, String valueField)  {
        addField(nameField,valueField);
    }


    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }



    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }



    public List<SQLField> list = new ArrayList<>();


    public void  addField(String field, String value){
        SQLField c = new SQLField();
        c.setNameField(field);
        c.setValueField(value);
        list.add(c);

    }

    public List<SQLField> getFields(){
        return list;
    }

}
