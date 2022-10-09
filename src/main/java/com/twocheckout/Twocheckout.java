package com.twocheckout;

import com.twocheckout.model.Api.Api;
import com.twocheckout.model.Api.Auth.AuthApi;
import com.twocheckout.model.Api.Auth.AuthBuyLink;
import com.twocheckout.model.Api.Auth.AuthFactory;
import com.twocheckout.model.Ipn;
import com.twocheckout.model.Signature;
import com.twocheckout.model.TwocheckoutConfig;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Twocheckout {
    private TwocheckoutConfig twocheckoutConfig;
    private AuthApi authApi;
    private AuthBuyLink authBuyLink;
    public Api api;
    public Signature signature;
    public Ipn ipn;

    public Twocheckout(TwocheckoutConfig config) {
        this.twocheckoutConfig = config;
        this.authApi = (AuthApi) (new AuthFactory(this.twocheckoutConfig)).getAuth("Api");
        this.authBuyLink = (AuthBuyLink) (new AuthFactory(this.twocheckoutConfig)).getAuth("BuyLink");
        this.api = new Api(this.twocheckoutConfig);
        this.signature = new Signature(this.twocheckoutConfig);
        this.ipn = new Ipn(this.twocheckoutConfig);
    }
}
