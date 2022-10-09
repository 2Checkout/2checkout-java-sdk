package com.twocheckout.util;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Rest.TwocheckoutResponse;
import lombok.Cleanup;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    public static TwocheckoutResponse makeRequest(URL url, String method, HashMap<String, String> headers, JSONObject params) throws TwocheckoutException {

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if(!method.equals("GET") && params != null) {
                con.setDoOutput(true);
                OutputStreamWriter output = new OutputStreamWriter(con.getOutputStream());
                output.write(params.toString());
                output.flush();
            }

            final int responseCode = con.getResponseCode();

            final InputStream responseStream =
                    (responseCode >= 200 && responseCode < 300)
                            ? con.getInputStream()
                            : con.getErrorStream();

            final StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[1024];
            @Cleanup final Reader in = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            int charsRead = 0;
            while ((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
                sb.append(buffer, 0, charsRead);
            }

            String responseBody = sb.toString();
            responseStream.close();

            return new TwocheckoutResponse(responseCode, responseBody);

        } catch (IOException e) {
            throw new TwocheckoutException("We encountered an error while connecting to the 2Checkout API.", 0, e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
