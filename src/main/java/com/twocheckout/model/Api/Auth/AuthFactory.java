package com.twocheckout.model.Api.Auth;

import com.twocheckout.model.TwocheckoutConfig;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthFactory {
    private TwocheckoutConfig twocheckoutConfig;

    public Object getAuth(String type) {
        if(type.equals("Api")) {
            return new AuthApi(this.twocheckoutConfig.getMerchantCode(), this.twocheckoutConfig.getSecretKey());
        } else if(type.equals("BuyLink")) {
            return new AuthBuyLink(this.twocheckoutConfig.getMerchantCode(), this.twocheckoutConfig.getSecretWord());
        }
        return null;
    }
}
