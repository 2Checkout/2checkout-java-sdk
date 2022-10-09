package com.twocheckout.model.Api;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Rest.ApiCore;
import com.twocheckout.model.Api.Rest.Order;
import com.twocheckout.model.Api.Rest.TwocheckoutResponse;
import com.twocheckout.model.TwocheckoutConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@AllArgsConstructor
@Getter
public class Api {
    private TwocheckoutConfig config;
    private ApiCore apiCore;
    public Order order;

    public Api(TwocheckoutConfig config) {
        this.apiCore = new ApiCore(config);
        this.order = new Order(this.apiCore);
    }

    public TwocheckoutResponse call(String endpoint, JSONObject params, String method) throws TwocheckoutException {
        return this.apiCore.call(endpoint, params, method);
    }
}
