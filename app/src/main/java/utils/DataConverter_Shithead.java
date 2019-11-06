package utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import Database.Shithead.Shithead_Spieler;

public class DataConverter_Shithead implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Shithead_Spieler> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Shithead_Spieler>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Shithead_Spieler> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Shithead_Spieler>>() {
        }.getType();
        List<Shithead_Spieler> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}