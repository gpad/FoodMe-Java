package com.foodme.application;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class Serialization {
    private final static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializer()).create();
    }

    public static <T extends Object> T deserialize(String serialization, final Class<T> type) {
        T instance = gson.fromJson(serialization, type);
        return instance;
    }

    public static <T extends Object> T deserialize(String serialization, final Type type) {
        T instance = gson.fromJson(serialization, type);
        return instance;
    }

    public static String serialize(final Object instance) {
        final String serialization = gson.toJson(instance);
        return serialization;
    }

    private static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(source.getTime()));
        }
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context) throws JsonParseException {
            long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
            return new Date(time);
        }
    }
}
