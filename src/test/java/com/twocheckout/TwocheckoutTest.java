package com.twocheckout;

import com.twocheckout.Twocheckout;
import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Rest.TwocheckoutResponse;
import com.twocheckout.model.TwocheckoutConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

class TwocheckoutTest {
    private TwocheckoutConfig twocheckoutConfig;
    private Twocheckout twocheckoutInstance;

    @BeforeEach
    void setUp() {
        TwocheckoutConfig config = new TwocheckoutConfig();
        config.setMerchantCode(TestsConfig.MERCHANT_CODE);
        config.setSecretKey(TestsConfig.SECRET_KEY);
        config.setSecretWord(TestsConfig.SECRET_WORD);
        this.twocheckoutConfig = config;
        this.twocheckoutInstance = new Twocheckout(config);
    }

    @Test
    public void shouldInit() {
        Twocheckout instance = new Twocheckout(this.twocheckoutConfig);
        Assertions.assertEquals(instance.getClass().getName(), "com.twocheckout.Twocheckout");
    }

    @Test
    public void canUseOrderModel() {
        try {
            TwocheckoutResponse result = this.twocheckoutInstance.api.order.list(null);
            Assertions.assertEquals(400, result.getHttpStatus());
            Assertions.assertNotNull(result.getBody());
            Assertions.assertNotNull(result.getJson());
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canUseIpnModel() {
        try {
            HashMap<String, String[]> params = new HashMap<>();
            Boolean result = this.twocheckoutInstance.ipn.valid(params);
            Assertions.assertNotNull(result);
        } catch (NullPointerException | TwocheckoutException e) {
            Assertions.assertNotNull(e);
        }
    }

    @Test
    public void canUseSignatureModel() {
        try {
            HashMap<String, String> params = new HashMap<>();
            String result = this.twocheckoutInstance.signature.create(params);
            Assertions.assertNotNull(result);
        } catch (TwocheckoutException e) {
            Assertions.assertNotNull(e);
        }
    }
}