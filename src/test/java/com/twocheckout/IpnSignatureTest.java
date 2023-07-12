package com.twocheckout;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Ipn;
import com.twocheckout.model.TwocheckoutConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.LinkedHashMap;
import java.util.Map;

public class IpnSignatureTest {
    private TwocheckoutConfig twocheckoutConfig;
    private LinkedHashMap<String, String[]> parameters = new LinkedHashMap<>();

    @BeforeEach
    void setUp() {
        TwocheckoutConfig config = new TwocheckoutConfig();
        config.setMerchantCode(TestsConfig.MERCHANT_CODE);
        config.setSecretKey(TestsConfig.SECRET_KEY);
        config.setSecretWord(TestsConfig.SECRET_WORD);
        this.twocheckoutConfig = config;

        parameters.put("GIFT_ORDER", new String[]{"0"});
        parameters.put("SALEDATE", new String[]{"2023-06-09 15:26:18"});
        parameters.put("PAYMENTDATE", new String[]{"2023-06-09 15:31:18"});
        parameters.put("REFNO", new String[]{"211153389"});
        parameters.put("REFNOEXT", new String[]{"REST_API_AVANGTE"});
        parameters.put("SHOPPER_REFERENCE_NUMBER", new String[]{""});
        parameters.put("ORDERNO", new String[]{"25430"});
        parameters.put("ORDERSTATUS", new String[]{"COMPLETE"});
        parameters.put("PAYMETHOD", new String[]{"Visa/MasterCard"});
        parameters.put("PAYMETHOD_CODE", new String[]{"CCVISAMC"});
        parameters.put("FIRSTNAME", new String[]{"Customer"});
        parameters.put("LASTNAME", new String[]{"2Checkout"});
        parameters.put("COMPANY", new String[]{""});
        parameters.put("REGISTRATIONNUMBER", new String[]{""});
        parameters.put("FISCALCODE", new String[]{""});
        parameters.put("TAX_OFFICE", new String[]{""});
        parameters.put("CBANKNAME", new String[]{""});
        parameters.put("CBANKACCOUNT", new String[]{""});
        parameters.put("ADDRESS1", new String[]{"Test Address"});
        parameters.put("ADDRESS2", new String[]{""});
        parameters.put("CITY", new String[]{"LA"});
        parameters.put("STATE", new String[]{"DF"});
        parameters.put("ZIPCODE", new String[]{"70403-900"});
        parameters.put("COUNTRY", new String[]{"United States of America"});
        parameters.put("COUNTRY_CODE", new String[]{"us"});
        parameters.put("PHONE", new String[]{"556133127400"});
        parameters.put("FAX", new String[]{""});
        parameters.put("CUSTOMEREMAIL", new String[]{"customer@2Checkout.com"});
        parameters.put("FIRSTNAME_D", new String[]{"Customer"});
        parameters.put("LASTNAME_D", new String[]{"2Checkout"});
        parameters.put("COMPANY_D", new String[]{""});
        parameters.put("ADDRESS1_D", new String[]{"Test Address"});
        parameters.put("ADDRESS2_D", new String[]{""});
        parameters.put("CITY_D", new String[]{"LA"});
        parameters.put("STATE_D", new String[]{"DF"});
        parameters.put("ZIPCODE_D", new String[]{"70403-900"});
        parameters.put("COUNTRY_D", new String[]{"United States of America"});
        parameters.put("COUNTRY_D_CODE", new String[]{"us"});
        parameters.put("PHONE_D", new String[]{"556133127400"});
        parameters.put("EMAIL_D", new String[]{"customer@2Checkout.com"});
        parameters.put("IPADDRESS", new String[]{"91.220.121.21"});
        parameters.put("IPCOUNTRY", new String[]{"Romania"});
        parameters.put("COMPLETE_DATE", new String[]{"2023-06-09 15:31:23"});
        parameters.put("TIMEZONE_OFFSET", new String[]{"GMT+03:00"});
        parameters.put("CURRENCY", new String[]{"RON"});
        parameters.put("LANGUAGE", new String[]{"en"});
        parameters.put("ORDERFLOW", new String[]{"REGULAR"});
        parameters.put("IPN_PID[]", new String[]{"40898000"});
        parameters.put("IPN_PNAME[]", new String[]{"Dynamic product"});
        parameters.put("IPN_PCODE[]", new String[]{""});
        parameters.put("IPN_EXTERNAL_REFERENCE[]", new String[]{""});
        parameters.put("IPN_INFO[]", new String[]{""});
        parameters.put("IPN_QTY[]", new String[]{"1"});
        parameters.put("IPN_PRICE[]", new String[]{"0.01"});
        parameters.put("IPN_VAT[]", new String[]{"0.00"});
        parameters.put("IPN_VAT_RATE[]", new String[]{"0.0000"});
        parameters.put("IPN_VER[]", new String[]{"1"});
        parameters.put("IPN_DISCOUNT[]", new String[]{"0.00"});
        parameters.put("IPN_PROMOTION_CATEGORY[]", new String[]{""});
        parameters.put("IPN_PROMONAME[]", new String[]{""});
        parameters.put("IPN_PROMOCODE[]", new String[]{""});
        parameters.put("IPN_ORDER_COSTS[]", new String[]{"0"});
        parameters.put("IPN_SKU[]", new String[]{""});
        parameters.put("IPN_PARTNER_CODE", new String[]{""});
        parameters.put("IPN_PGROUP[]", new String[]{"0"});
        parameters.put("IPN_PGROUP_NAME[]", new String[]{""});
        parameters.put("MESSAGE_ID", new String[]{"254510700124"});
        parameters.put("MESSAGE_TYPE", new String[]{"COMPLETE"});
        parameters.put("IPN_LICENSE_PROD[]", new String[]{"40898000"});
        parameters.put("IPN_LICENSE_TYPE[]", new String[]{"REGULAR"});
        parameters.put("IPN_LICENSE_REF[]", new String[]{"XRILXB9ZI3"});
        parameters.put("IPN_LICENSE_EXP[]", new String[]{"9999-12-31 23:59:59"});
        parameters.put("IPN_LICENSE_START[]", new String[]{"2023-06-09 15:31:18"});
        parameters.put("IPN_LICENSE_LIFETIME[]", new String[]{"YES"});
        parameters.put("IPN_LICENSE_ADDITIONAL_INFO[]", new String[]{""});
        parameters.put("IPN_DELIVEREDCODES[]", new String[]{""});
        parameters.put("IPN_DOWNLOAD_LINK", new String[]{""});
        parameters.put("IPN_TOTAL[]", new String[]{"0.01"});
        parameters.put("IPN_TOTALGENERAL", new String[]{"0.01"});
        parameters.put("IPN_SHIPPING", new String[]{"0.00"});
        parameters.put("IPN_SHIPPING_TAX", new String[]{"0.00"});
        parameters.put("AVANGATE_CUSTOMER_REFERENCE", new String[]{"756227060"});
        parameters.put("EXTERNAL_CUSTOMER_REFERENCE", new String[]{"IOUER"});
        parameters.put("IPN_PARTNER_MARGIN_PERCENT", new String[]{"0.00"});
        parameters.put("IPN_PARTNER_MARGIN", new String[]{"0.00"});
        parameters.put("IPN_EXTRA_MARGIN", new String[]{"0.00"});
        parameters.put("IPN_EXTRA_DISCOUNT", new String[]{"0.00"});
        parameters.put("IPN_COUPON_DISCOUNT", new String[]{"0.00"});
        parameters.put("IPN_LINK_SOURCE", new String[]{"testAPI.com"});
        parameters.put("IPN_COMMISSION", new String[]{"2.7678"});
        parameters.put("REFUND_TYPE", new String[]{""});
        parameters.put("CHARGEBACK_RESOLUTION", new String[]{"NONE"});
        parameters.put("CHARGEBACK_REASON_CODE", new String[]{""});
        parameters.put("TEST_ORDER", new String[]{"0"});
        parameters.put("IPN_ORDER_ORIGIN", new String[]{"API"});
        parameters.put("FRAUD_STATUS", new String[]{"APPROVED"});
        parameters.put("CARD_TYPE", new String[]{"mastercard"});
        parameters.put("CARD_LAST_DIGITS", new String[]{"5547"});
        parameters.put("CARD_EXPIRATION_DATE", new String[]{"05/27"});
        parameters.put("GATEWAY_RESPONSE", new String[]{"Approved"});
        parameters.put("IPN_DATE", new String[]{"20230619190629"});
        parameters.put("FX_RATE", new String[]{"0.20810660205937"});
        parameters.put("FX_MARKUP", new String[]{"4"});
        parameters.put("PAYABLE_AMOUNT", new String[]{"-0.57"});
        parameters.put("PAYOUT_CURRENCY", new String[]{"USD"});
        parameters.put("VENDOR_CODE", new String[]{"250111206876"});
        parameters.put("PROPOSAL_ID", new String[]{""});
        parameters.put("HASH", new String[]{"dad1c7a601a4a990cec21a57c1d2b702"});
        parameters.put("SIGNATURE_SHA2_256", new String[]{"d38b877262d8a7abbd7b8877c1d60f3ea6a5c11e2315fa5c3209ccaa9f2eb797"});
        parameters.put("SIGNATURE_SHA3_256", new String[]{"12b2a5d6bf2ddb8581d57787964bc351fb791655e5e9a7961ba8b9619e372adb"});
    }

    @Test
    public void isValidSha3() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            Boolean valid = ipn.valid(this.parameters);
            Assertions.assertEquals(true, valid, "The IPN response is valid");
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isValidSha256() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            LinkedHashMap<String, String[]> parameters = this.parameters;
            parameters.remove("SIGNATURE_SHA3_256");
            Boolean valid = ipn.valid(parameters);
            Assertions.assertEquals(true, valid, "The IPN response is valid");
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isValidMd5() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            LinkedHashMap<String, String[]> parameters = this.parameters;
            parameters.remove("SIGNATURE_SHA3_256");
            parameters.remove("SIGNATURE_SHA2_256");
            Boolean valid = ipn.valid(this.parameters);
            Assertions.assertEquals(true, valid, "The IPN response is valid");
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isSha3ResponseValid() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            LinkedHashMap<String, String[]> parameters = this.parameters;
            String response = ipn.response(parameters, "20210701163700");
            Assertions.assertEquals("<sig algo=\"sha3-256\" date=\"20210701163700\">7dde4c2867305d74a5a48dee16542fb3d5ba6c44b35fd157e6e9554e14eec83d</sig>", response, "The IPN response is valid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isSha256ResponseValid() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            LinkedHashMap<String, String[]> parameters = this.parameters;
            parameters.remove("SIGNATURE_SHA3_256");
            String response = ipn.response(parameters, "20210701163700");
            Assertions.assertEquals("<sig algo=\"sha256\" date=\"20210701163700\">3995c5a6401057b4f4c0032089c6a951823b4d804e1abe2ac04d4d68362dff2f</sig>", response, "The IPN response is valid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isMd5ResponseValid() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            LinkedHashMap<String, String[]> parameters = this.parameters;
            parameters.remove("SIGNATURE_SHA3_256");
            parameters.remove("SIGNATURE_SHA2_256");
            String response = ipn.response(parameters, "20210701163700");
            Assertions.assertEquals("<EPAYMENT>20210701163700|3d58c9996861940deacd16215da33c1e</EPAYMENT>", response, "The IPN response is valid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
