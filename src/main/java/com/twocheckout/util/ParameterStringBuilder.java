package com.twocheckout.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static String getParamsString(JSONObject params) throws UnsupportedEncodingException{
        Iterator<String> keys = params.keys();
        StringBuilder stringBuilder = new StringBuilder("?");
        while(keys.hasNext()) {
            String key = keys.next();
            if ((params.get(key) instanceof JSONObject)
                    || (params.get(key) instanceof JSONArray)) {
                continue;
            }
            else {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(params.get(key));
            }
            if(keys.hasNext())
                stringBuilder.append("&");
        }
        byte[] stringBytes = stringBuilder.toString().getBytes();
        return new String(stringBytes, StandardCharsets.US_ASCII);
    }
}