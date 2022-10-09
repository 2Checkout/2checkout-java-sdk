package com.twocheckout;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Signature;
import com.twocheckout.model.TwocheckoutConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BuyLinkSignatureTest {
    private TwocheckoutConfig twocheckoutConfig;
    private HashMap<String, String> parameters = new HashMap<>();

    @BeforeEach
    void setUp() {
        TwocheckoutConfig config = new TwocheckoutConfig();
        config.setMerchantCode(TestsConfig.MERCHANT_CODE);
        config.setSecretKey(TestsConfig.SECRET_KEY);
        config.setSecretWord(TestsConfig.SECRET_WORD);
        this.twocheckoutConfig = config;

        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        parameters.put("address","Test Address");
        parameters.put("city","Columbus");
        parameters.put("country","US");
        parameters.put("name","Customer 2Checkout");
        parameters.put("phone","0770678987");
        parameters.put("zip","12345");
        parameters.put("email","testcustomer@2Checkout.com");
        parameters.put("company-name","Verifone");
        parameters.put("state","California");
        parameters.put("ship-name","Customer 2Checkout");
        parameters.put("ship-address","Test Address");
        parameters.put("ship-city","Columbus");
        parameters.put("ship-country","US");
        parameters.put("ship-state","California");
        parameters.put("ship-email","testcustomer@2Checkout.com");
        parameters.put("prod","Buy link Dynamic product test product from API");
        parameters.put("price","1");
        parameters.put("qty","1");
        parameters.put("type","PRODUCT");
        parameters.put("tangible","0");
        parameters.put("return-url","http://tcoLib.example/paymentCallback.php");
        parameters.put("return-type","redirect");
        parameters.put("expiration", simpleDateFormat.format(new Date()));
        parameters.put("order-ext-ref","CustOrd100");
        parameters.put("item-ext-ref","20210423094943");
        parameters.put("customer-ext-ref","testcustomer@2Checkout.com");
        parameters.put("currency","usd");
        parameters.put("language","en");
        parameters.put("","1");
        parameters.put("merchant",this.twocheckoutConfig.getMerchantCode());
        parameters.put("dynamic","1");
    }

    @Test
    public void shouldCreateSignature() {
        try {
            Signature signature = new Signature(this.twocheckoutConfig);

            String buyLinkSignature = signature.create(parameters);

            parameters.put("signature",buyLinkSignature);
            String link = parameters.entrySet().stream()
                    .map(p -> urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(p.getValue()))
                    .reduce((p1, p2) -> p1 + "&" + p2)
                    .orElse("");
            System.out.print("https://secure.2checkout.com/checkout/buy?" + link);
            Assertions.assertNotNull(buyLinkSignature);
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowException() {
        try {
            TwocheckoutConfig badConfig = new TwocheckoutConfig();
            Signature signature = new Signature(badConfig);
            signature.create(parameters);
        } catch (TwocheckoutException e) {
            Assertions.assertEquals(e.getClass().getName(), "com.twocheckout.exception.TwocheckoutException");
        }
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }


}
