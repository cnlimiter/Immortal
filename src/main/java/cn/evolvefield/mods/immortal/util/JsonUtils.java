package cn.evolvefield.mods.immortal.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/8 21:58
 * Version: 1.0
 */
public class JsonUtils {
    public static String getStringOr(String pKey, JsonObject pJson, String pDefaultValue) {
        JsonElement jsonelement = pJson.get(pKey);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? pDefaultValue : jsonelement.getAsString();
        } else {
            return pDefaultValue;
        }
    }

    public static int getIntOr(String pKey, JsonObject pJson, int pDefaultValue) {
        JsonElement jsonelement = pJson.get(pKey);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? pDefaultValue : jsonelement.getAsInt();
        } else {
            return pDefaultValue;
        }
    }

    public static long getLongOr(String pKey, JsonObject pJson, long pDefaultValue) {
        JsonElement jsonelement = pJson.get(pKey);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? pDefaultValue : jsonelement.getAsLong();
        } else {
            return pDefaultValue;
        }
    }

    public static double getDoubleOr(String pKey, JsonObject pJson, double pDefaultValue) {
        JsonElement jsonelement = pJson.get(pKey);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? pDefaultValue : jsonelement.getAsDouble();
        } else {
            return pDefaultValue;
        }
    }

    public static boolean getBooleanOr(String pKey, JsonObject pJson, boolean pDefaultValue) {
        JsonElement jsonelement = pJson.get(pKey);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? pDefaultValue : jsonelement.getAsBoolean();
        } else {
            return pDefaultValue;
        }
    }

    public static Date getDateOr(String pKey, JsonObject pJson) {
        JsonElement jsonelement = pJson.get(pKey);
        return jsonelement != null ? new Date(Long.parseLong(jsonelement.getAsString())) : new Date();
    }

    public static JsonArray getArray(String string, JsonObject obj) {
        if (obj.get(string) != null) {
            JsonElement el = obj.get(string);
            if (el.isJsonArray()) {
                return obj.get(string).getAsJsonArray();
            } else {
                return new JsonArray();
            }
        } else {
            return new JsonArray();
        }
    }
}
