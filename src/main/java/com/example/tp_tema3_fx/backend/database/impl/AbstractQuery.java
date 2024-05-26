package com.example.tp_tema3_fx.backend.database.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains queries needed for the CRUD operations in the DAO objects.
 */
public class AbstractQuery<T> {

    private final Class<T> type;
    @SuppressWarnings("unchecked")
    public AbstractQuery() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public String getFindById(){
        return "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE id = ?;";
    }
    public String getFindAll(){
        return "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s;";
    }
    public String findByName(){
        return "SELECT FROM " + type.getSimpleName() + "s WHERE name = ?;";
    }
    public String getInsert(){
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(type.getSimpleName());
        query.append("s (");
        for(Field field : type.getDeclaredFields()){
            if(field.getName().equals("id")){
                continue;
            }
            query.append(field.getName());
            query.append(",");
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(") VALUES (");
        query.append("?,".repeat(type.getDeclaredFields().length - 1));
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(")");
        return query.toString();
    }
    public String getUpdate(){
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(type.getSimpleName());
        query.append("s SET ");
        for(Field field : type.getDeclaredFields()){
            if(field.getName().equals("id")){
                continue;
            }
            query.append(field.getName());
            query.append("=?,");
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(" WHERE id = ?;");
        return query.toString();
    }
    public String getDelete(){
        return "DELETE FROM " + type.getSimpleName() + "s WHERE id = ?;";
    }

}
