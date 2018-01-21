package gerasymchuk.v.themovies.data;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by vitaliygerasymchuk on 1/21/18
 */

public class Converters {

    @TypeConverter
    public static String fromIntArrayList(@NonNull ArrayList<Integer> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static ArrayList<String> toIntArrayList(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }
}
