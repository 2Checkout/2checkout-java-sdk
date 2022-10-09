package com.twocheckout.model;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Auth.AuthBuyLink;
import com.twocheckout.model.Api.Rest.TwocheckoutResponse;
import com.twocheckout.util.ApiClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class Signature {
    @NonNull
    private TwocheckoutConfig twocheckoutConfig;

    public String create(Map<String, String> params) throws TwocheckoutException {
        return this.create(new JSONObject(params));
    }

    public String create(JSONObject params) throws TwocheckoutException {
        URL url;
        String jwt;
        try {
            AuthBuyLink authBuyLink = new AuthBuyLink(this.twocheckoutConfig.getMerchantCode(), this.twocheckoutConfig.getSecretWord());
            jwt = authBuyLink.createJWT();
            url = new URL(this.twocheckoutConfig.getBuyLinkUrl());
        } catch (MalformedURLException | NullPointerException e) {
            throw new TwocheckoutException(e.getMessage(), 0, e);
        }

        HashMap<String, String> headers = this.getHeaders(jwt);

        TwocheckoutResponse result = ApiClient.makeRequest(url, "POST", headers, params);

        if (result.getHttpStatus() >= 200 && result.getHttpStatus() < 300) {
            try {
                JSONObject responseObject = new JSONObject(result.getBody());
                return responseObject.get("signature").toString();
            } catch (JSONException e) {
                throw new TwocheckoutException("An error encountered while parsing the signature response.", 0, e);
            }
        } else {
            throw new TwocheckoutException(result.getBody(), result.getHttpStatus(), null);
        }
    }

    private HashMap<String, String> getHeaders(String jwt) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("merchant-token", jwt);
        return headers;
    }
}
