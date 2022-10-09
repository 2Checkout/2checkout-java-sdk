package com.twocheckout.model.Api.Rest;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Auth.AuthApi;
import com.twocheckout.model.TwocheckoutConfig;
import com.twocheckout.util.ApiClient;
import com.twocheckout.util.ParameterStringBuilder;
import lombok.*;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RequiredArgsConstructor
@Getter
@Setter
public class ApiCore {
    @NonNull
    private TwocheckoutConfig twocheckoutConfig;

    public TwocheckoutResponse call(String endpoint, JSONObject params, String method) throws TwocheckoutException {
        AuthApi auth = new AuthApi(this.twocheckoutConfig.getMerchantCode(), this.twocheckoutConfig.getSecretKey());
        try {
            URL url;
            String encodedParams;
            if(method.equals("GET") && params != null) {
                encodedParams = encodeValue(ParameterStringBuilder.getParamsString(params));
                url = new URL(this.twocheckoutConfig.getRestApiUrl() + endpoint + encodedParams);
            }
            else {
                url = new URL(this.twocheckoutConfig.getRestApiUrl() + endpoint);
            }
            HashMap<String, String> headers = auth.getHeaders();

            return ApiClient.makeRequest(url, method, headers, params);
        } catch (IOException e) {
            throw new TwocheckoutException("We encountered an error while connecting to the 2Checkout API.", 0, e);
        }
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

}