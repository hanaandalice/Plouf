package com.allbino.plouf.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConvertor {
    @TypeConverter
    public static List<String> stringToList(String value) {
        if(value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){
        }.getType();
        List<String> dateList = gson.fromJson(value, type);
        return dateList;
    }

    @TypeConverter
    public static String fromdateList(List<String> dateList) {
        if(dateList == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){
        }.getType();
        String json = gson.toJson(dateList, type);
        return json;
    }

}
