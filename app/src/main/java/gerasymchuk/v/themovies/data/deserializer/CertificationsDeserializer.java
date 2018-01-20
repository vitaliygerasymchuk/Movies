package gerasymchuk.v.themovies.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

import gerasymchuk.v.themovies.data.model.Certification;
import gerasymchuk.v.themovies.data.model.response.Certifications;
import gerasymchuk.v.themovies.shared.Logger;

public class CertificationsDeserializer implements JsonDeserializer<Certifications> {

    @SuppressWarnings("unused")
    private static final String TAG = CertificationsDeserializer.class.getSimpleName();

    @Override
    public Certifications deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        final Certifications certifications = new Certifications();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final String key = entry.getKey();
            Logger.d(TAG, ":::::::::::::::::::::::::::::::::::::: [   " + key + "   ] :::::::::::::::::::::::::::::::::::::: [");

            final JsonElement keyElement = entry.getValue();
            if (keyElement.isJsonArray()) {
                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    final JsonElement jsonElement = jsonArray.get(i);
                    final Certification certification = new Gson().fromJson(jsonElement, Certification.class);
                    certification.countryKey = key;
                    certifications.certifications.add(certification);
                    Logger.d(TAG, certification.toString());
                }
            }
            Logger.d(TAG, ":::::::::::::::::::::::::::::::::::::: [   " + key + "   ] :::::::::::::::::::::::::::::::::::::: [");

        }

        return certifications;
    }
}
