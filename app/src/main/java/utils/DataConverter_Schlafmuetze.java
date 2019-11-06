package utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import Database.Schlafmuetze.Schlafmuetze_Spieler;

public class DataConverter_Schlafmuetze implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Schlafmuetze_Spieler> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Schlafmuetze_Spieler>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Schlafmuetze_Spieler> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Schlafmuetze_Spieler>>() {
        }.getType();
        List<Schlafmuetze_Spieler> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}