package com.twocheckout.model.Api.Rest;

import com.twocheckout.exception.TwocheckoutException;
import org.jetbrains.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    private ApiCore apiCore;

    public TwocheckoutResponse create(JSONObject params) throws TwocheckoutException {
        return this.apiCore.call("/orders/", params, "POST");
    }

    public TwocheckoutResponse get(@NotNull String refNo) throws TwocheckoutException {
        String endpoint = "/orders/" + refNo + "/";
        return this.apiCore.call(endpoint, null, "GET");
    }

    public TwocheckoutResponse list(HashMap<String, String> params) throws TwocheckoutException {
        String endpoint = "/orders/";
        JSONObject paramsObject = new JSONObject(params);
        return this.apiCore.call(endpoint, paramsObject, "GET");
    }
}
