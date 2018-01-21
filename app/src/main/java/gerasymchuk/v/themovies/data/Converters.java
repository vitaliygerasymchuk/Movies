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

    /**
     * Converts {@link ArrayList<Integer>} to String in order to persist in {@link android.arch.persistence.room.Room}
     *
     * @param list ArrayList
     * @return String
     */
    @TypeConverter
    public static String intArrayListToString(@NonNull ArrayList<Integer> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


    /**
     * Converts {@link String} to ArrayList<Integer></>
     *
     * @param value String
     * @return ArrayList
     */
    @TypeConverter
    public static ArrayList<Integer> stringToIntArrayList(@NonNull String value) {
        Type listType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }
}
