package gerasymchuk.v.themovies.shared;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;


public class DateSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    @SuppressWarnings("unused")
    private static final String TAG = DateSerializer.class.getSimpleName();

    @Override
    public synchronized JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public synchronized DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            final String timeString = jsonElement.getAsString().replace(" UTC", "");
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC().parseDateTime(timeString);
        } catch (Exception e) {
            Logger.e("DateSerializer", "deserialize " + e.toString());
        }
        return null;
    }
}