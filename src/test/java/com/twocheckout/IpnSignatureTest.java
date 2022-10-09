package com.twocheckout;

import com.twocheckout.exception.TwocheckoutException;
import com.twocheckout.model.Ipn;
import com.twocheckout.model.TwocheckoutConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.LinkedHashMap;

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
        parameters.put("SALEDATE", new String[]{"2021-01-08 10:24:55"});
        parameters.put("PAYMENTDATE", new String[]{"2021-01-08 10:36:20"});
        parameters.put("REFNO", new String[]{"141905560"});
        parameters.put("REFNOEXT", new String[]{"381"});
        parameters.put("SHOPPER_REFERENCE_NUMBER", new String[]{""});
        parameters.put("ORDERNO", new String[]{"5210"});
        parameters.put("ORDERSTATUS", new String[]{"COMPLETE"});
        parameters.put("PAYMETHOD", new String[]{"Visa/MasterCard"});
        parameters.put("PAYMETHOD_CODE", new String[]{"CCVISAMC"});
        parameters.put("FIRSTNAME", new String[]{"Ovidiu"});
        parameters.put("LASTNAME", new String[]{"Guzgan"});
        parameters.put("COMPANY", new String[]{""});
        parameters.put("REGISTRATIONNUMBER", new String[]{""});
        parameters.put("FISCALCODE", new String[]{""});
        parameters.put("TAX_OFFICE", new String[]{""});
        parameters.put("CBANKNAME", new String[]{""});
        parameters.put("CBANKACCOUNT", new String[]{""});
        parameters.put("ADDRESS1", new String[]{"Address 1"});
        parameters.put("ADDRESS2", new String[]{"Address 2"});
        parameters.put("CITY", new String[]{"Abbeville"});
        parameters.put("STATE", new String[]{"Alabama"});
        parameters.put("ZIPCODE", new String[]{"36310"});
        parameters.put("COUNTRY", new String[]{"United States of America"});
        parameters.put("COUNTRY_CODE", new String[]{"us"});
        parameters.put("PHONE", new String[]{"012313213211"});
        parameters.put("FAX", new String[]{""});
        parameters.put("CUSTOMEREMAIL", new String[]{"cristian.cojocea@2checkout.com"});
        parameters.put("FIRSTNAME_D", new String[]{"Ovidiu"});
        parameters.put("LASTNAME_D", new String[]{"Guzgan"});
        parameters.put("COMPANY_D", new String[]{"My company SRL"});
        parameters.put("ADDRESS1_D", new String[]{"Address 1"});
        parameters.put("ADDRESS2_D", new String[]{"Address 2"});
        parameters.put("CITY_D", new String[]{"Abbeville"});
        parameters.put("STATE_D", new String[]{"Alabama"});
        parameters.put("ZIPCODE_D", new String[]{"36310"});
        parameters.put("COUNTRY_D", new String[]{"United States of America"});
        parameters.put("COUNTRY_D_CODE", new String[]{"us"});
        parameters.put("PHONE_D", new String[]{"012313213211"});
        parameters.put("EMAIL_D", new String[]{"cristian.cojocea@2checkout.com"});
        parameters.put("IPADDRESS", new String[]{"84.232.193.75"});
        parameters.put("IPCOUNTRY", new String[]{"Romania"});
        parameters.put("COMPLETE_DATE", new String[]{"2021-01-08 10:36:22"});
        parameters.put("TIMEZONE_OFFSET", new String[]{"GMT+02:00"});
        parameters.put("CURRENCY", new String[]{"EUR"});
        parameters.put("LANGUAGE", new String[]{"en"});
        parameters.put("ORDERFLOW", new String[]{"REGULAR"});
        parameters.put("IPN_PID[]", new String[]{"33654953"});
        parameters.put("IPN_PNAME[]", new String[]{"Cart_381"});
        parameters.put("IPN_PCODE[]", new String[]{""});
        parameters.put("IPN_EXTERNAL_REFERENCE[]", new String[]{""});
        parameters.put("IPN_INFO[]", new String[]{""});
        parameters.put("IPN_QTY[]", new String[]{"1"});
        parameters.put("IPN_PRICE[]", new String[]{"0.03"});
        parameters.put("IPN_VAT[]", new String[]{"0.00"});
        parameters.put("IPN_VAT_RATE[]", new String[]{"0.00"});
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
        parameters.put("MESSAGE_ID", new String[]{"250687018394"});
        parameters.put("MESSAGE_TYPE", new String[]{"COMPLETE"});
        parameters.put("IPN_LICENSE_PROD[]", new String[]{"33654953"});
        parameters.put("IPN_LICENSE_TYPE[]", new String[]{"REGULAR"});
        parameters.put("IPN_LICENSE_REF[]", new String[]{"TOF6T8VPY3"});
        parameters.put("IPN_LICENSE_EXP[]", new String[]{"9999-12-31 23:59:59"});
        parameters.put("IPN_LICENSE_START[]", new String[]{"2021-01-08 10:36:20"});
        parameters.put("IPN_LICENSE_LIFETIME[]", new String[]{"YES"});
        parameters.put("IPN_LICENSE_ADDITIONAL_INFO[]", new String[]{""});
        parameters.put("IPN_DELIVEREDCODES[]", new String[]{""});
        parameters.put("IPN_DOWNLOAD_LINK", new String[]{""});
        parameters.put("IPN_TOTAL[]", new String[]{"0.03"});
        parameters.put("IPN_TOTALGENERAL", new String[]{"0.03"});
        parameters.put("IPN_SHIPPING", new String[]{"0.00"});
        parameters.put("IPN_SHIPPING_TAX", new String[]{"0.00"});
        parameters.put("AVANGATE_CUSTOMER_REFERENCE", new String[]{"486361141"});
        parameters.put("EXTERNAL_CUSTOMER_REFERENCE", new String[]{"cristian.cojocea@2checkout.com"});
        parameters.put("IPN_PARTNER_MARGIN_PERCENT", new String[]{"0.00"});
        parameters.put("IPN_PARTNER_MARGIN", new String[]{"0.00"});
        parameters.put("IPN_EXTRA_MARGIN", new String[]{"0.00"});
        parameters.put("IPN_EXTRA_DISCOUNT", new String[]{"0.00"});
        parameters.put("IPN_COUPON_DISCOUNT", new String[]{"0.00"});
        parameters.put("IPN_COMMISSION", new String[]{"0.50"});
        parameters.put("REFUND_TYPE", new String[]{""});
        parameters.put("CHARGEBACK_RESOLUTION", new String[]{"NONE"});
        parameters.put("CHARGEBACK_REASON_CODE", new String[]{""});
        parameters.put("TEST_ORDER", new String[]{"0"});
        parameters.put("IPN_ORDER_ORIGIN", new String[]{"Web"});
        parameters.put("FRAUD_STATUS", new String[]{"APPROVED"});
        parameters.put("CARD_TYPE", new String[]{"visa"});
        parameters.put("CARD_LAST_DIGITS", new String[]{"6551"});
        parameters.put("CARD_EXPIRATION_DATE", new String[]{"01/24"});
        parameters.put("GATEWAY_RESPONSE", new String[]{"Approved"});
        parameters.put("IPN_DATE", new String[]{"20210108142708"});
        parameters.put("FX_RATE", new String[]{"1.178496"});
        parameters.put("FX_MARKUP", new String[]{"4"});
        parameters.put("PAYABLE_AMOUNT", new String[]{"-0.55"});
        parameters.put("PAYOUT_CURRENCY", new String[]{"USD"});
        parameters.put("VENDOR_CODE", new String[]{"250111206876"});
        parameters.put("PROPOSAL_ID", new String[]{""});
        parameters.put("HASH", new String[]{"cb11edf583142c0b2b480541b98340ab"});
    }

    @Test
    public void isValid() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            Boolean valid = ipn.valid(this.parameters);
            Assertions.assertEquals(true, valid, "The IPN response is valid");
        } catch (TwocheckoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isResponseValid() {
        try {
            Ipn ipn = new Ipn(this.twocheckoutConfig);
            String response = ipn.response(this.parameters, "20210701163700");
            Assertions.assertEquals("<EPAYMENT>20210701163700|22db39817d62a82007bd69f589bec955</EPAYMENT>", response, "The IPN response is valid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
