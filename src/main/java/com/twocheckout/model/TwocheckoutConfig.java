package com.twocheckout.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwocheckoutConfig {
    private String merchantCode;
    private String secretWord;
    private String secretKey;
    private final String restApiUrl = "https://api.2checkout.com/rest/6.0";
    private final String buyLinkUrl = "https://secure.2checkout.com/checkout/api/encrypt/generate/signature";
}
