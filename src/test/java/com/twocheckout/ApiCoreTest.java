package com.twocheckout;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Api.Rest.ApiCore;
import com.twocheckout.model.Api.Rest.TwocheckoutResponse;
import com.twocheckout.model.TwocheckoutConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiCoreTest {
    private TwocheckoutConfig twocheckoutConfig;
    private JSONObject dynamicProduct = new JSONObject();

    @BeforeEach
    void setUp() {
        TwocheckoutConfig config = new TwocheckoutConfig();
        config.setMerchantCode(TestsConfig.MERCHANT_CODE);
        config.setSecretKey(TestsConfig.SECRET_KEY);
        config.setSecretWord(TestsConfig.SECRET_WORD);

        this.twocheckoutConfig = config;

        JSONObject billingDetails = new JSONObject();
        JSONArray dynamicItems = new JSONArray();
        JSONArray catalogItems = new JSONArray();
        JSONObject dynamicItem = new JSONObject();
        JSONObject paymentDetails = new JSONObject();
        JSONObject price = new JSONObject();
        JSONObject paymentMethodDetails = new JSONObject();

        billingDetails.put("Address1", "Test Address");
        billingDetails.put("City", "LA");
        billingDetails.put("State", "California");
        billingDetails.put("CountryCode", "US");
        billingDetails.put("Email", "testcustomer@2Checkout.com");
        billingDetails.put("FirstName", "Customer");
        billingDetails.put("LastName", "2Checkout");
        billingDetails.put("Zip", "12345");

        dynamicItem.put("Name", "Dynamic product");
        dynamicItem.put("Description", "Test description");
        dynamicItem.put("Quantity", 1);
        dynamicItem.put("IsDynamic", true);
        dynamicItem.put("Tangible", false);
        dynamicItem.put("PurchaseType", "PRODUCT");

        price.put("Amount", 1);
        price.put("Type", "CUSTOM");
        dynamicItem.put("Price", price);

        dynamicItems.put(0, dynamicItem);
        catalogItems.put(0, catalogItems);

        paymentDetails.put("Type", "TEST");
        paymentDetails.put("Currency", "USD");
        paymentDetails.put("CustomerIP", "91.220.121.21");

        paymentMethodDetails.put("CardNumber", "4111111111111111");
        paymentMethodDetails.put("CardType", "VISA");
        paymentMethodDetails.put("Vendor3DSReturnURL", "www.success.com");
        paymentMethodDetails.put("Vendor3DSCancelURL", "www.fail.com");
        paymentMethodDetails.put("ExpirationYear", "2023");
        paymentMethodDetails.put("ExpirationMonth", "12");
        paymentMethodDetails.put("CCID", "123");
        paymentMethodDetails.put("HolderName", "John Doe");
        paymentMethodDetails.put("RecurringEnabled", false);
        paymentMethodDetails.put("HolderNameTime", 1);
        paymentMethodDetails.put("CardNumberTime", 1);

        paymentDetails.put("PaymentMethod", paymentMethodDetails);

        dynamicProduct.put("Country", "us");
        dynamicProduct.put("Currency", "USD");
        dynamicProduct.put("CustomerIP", "91.220.121.21");
        dynamicProduct.put("External Reference", "CustOrd100");
        dynamicProduct.put("Language", "en");
        dynamicProduct.put("Source", "tcolib.local");
        dynamicProduct.put("BillingDetails", billingDetails);
        dynamicProduct.put("Items", dynamicItems);
        dynamicProduct.put("PaymentDetails", paymentDetails);
    }

    @Test
    public void shouldMakePostRequest() {
        try {
            ApiCore apiCore = new ApiCore(this.twocheckoutConfig);
            TwocheckoutResponse result = apiCore.call("/orders/", this.dynamicProduct, "POST");
            Assertions.assertEquals(201, result.getHttpStatus());
            Assertions.assertNotNull(result.getBody());
            Assertions.assertNotNull(result.getJson().getString("Status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldMakeGetRequest() {
        try {
            JSONObject objectQuery = new JSONObject();
            objectQuery.put("Limit", "10");
            ApiCore apiCore = new ApiCore(this.twocheckoutConfig);
            TwocheckoutResponse result = apiCore.call("/products/", null, "GET");
            Assertions.assertEquals(200, result.getHttpStatus());
            Assertions.assertNotNull(result.getBody());
            Assertions.assertNotNull(result.getJson().getJSONArray("Items"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowErrorWithBadConfig() {
        try {
            TwocheckoutConfig badConfig = new TwocheckoutConfig();
            badConfig.setMerchantCode("foo");
            badConfig.setSecretKey("bar");
            badConfig.setSecretWord("baz");
            ApiCore apiCore = new ApiCore(badConfig);
            TwocheckoutResponse result = apiCore.call("/orders/", this.dynamicProduct, "POST");
            Assertions.assertEquals(401, result.getHttpStatus());
            Assertions.assertNotNull(result.getBody());
        } catch (TwocheckoutException e) {
            Assertions.assertNotNull(e.getMessage());
        }
    }
}
