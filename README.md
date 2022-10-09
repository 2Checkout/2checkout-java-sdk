# 2Checkout Java SDK

Current 2Checkout platform Java SDK supporting Java 1.8 or later.
This SDK Provides bindings to the 2Checkout 6.0 API, IPN, and ConvertPlus platform.


Installation
------------

Add the included twocheckout-java-{version}.jar and it's dependencies listed below.
* org.json.json
* com.auth0.java-jwt - *(only needed for Convert Plus signature generation)*

*Maven Dependencies*
```xml
<dependency>
	<groupId>com.auth0</groupId>
	<artifactId>java-jwt</artifactId>
	<version>4.0.0</version>
</dependency>

<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20220924</version>
</dependency>
```


Example API Usage:
------------------

*Example Usage:*
```java
TwocheckoutConfig config = new TwocheckoutConfig();
config.setMerchantCode("YOUR_MERCHANT_CODE");
config.setSecretKey("YOUR_SECRET_KEY");
config.setSecretWord("YOUR_SECRET_WORD");

JSONObject billingDetails = new JSONObject();
JSONArray dynamicItems = new JSONArray();
JSONObject dynamicItem = new JSONObject();
JSONObject orderPayload = new JSONObject();
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

paymentDetails.put("Type", "TEST");
paymentDetails.put("Currency", "USD");
paymentDetails.put("CustomerIP", "127.0.0.1");

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

orderPayload.put("Country", "us");
orderPayload.put("Currency", "USD");
orderPayload.put("CustomerIP", "127.0.0.1");
orderPayload.put("External Reference", "CustOrd100");
orderPayload.put("Language", "en");
orderPayload.put("Source", "tcolib.local");
orderPayload.put("BillingDetails", billingDetails);
orderPayload.put("Items", dynamicItems);
orderPayload.put("PaymentDetails", paymentDetails);

ApiCore apiCore = new ApiCore(config);
TwocheckoutResponse result = null;
try {
    result = apiCore.call("/orders/", orderPayload, "POST");
    result.getJson();
} catch (TwocheckoutException e) {
    e.getMessage();
}
```

*Example Response*
```json
// org.json.JSONObject
{
	"Origin": "API",
	"TestOrder": true,
	"VAT": 0,
	"Affiliate": {
		"AffiliateName": null,
		"AffiliateUrl": null,
		"AffiliateCode": null,
		"AffiliateSource": null
	},
	"CustomerDetails": null,
	"ExtraInformation": null,
	"ExtraDiscountPercent": null,
	"ExternalReference": null,
	"Currency": "usd",
	"Items": [{
		"RecurringOptions": null,
		"SubscriptionStartDate": null,
		"Trial": null,
		"PurchaseType": "PRODUCT",
		"AdditionalFields": null,
		"Quantity": 1,
		"CrossSell": null,
		"Promotion": null,
		"UpSell": null,
		"PriceOptions": [],
		"ExternalReference": "",
		"Price": {
			"UnitDiscount": 0,
			"UnitVAT": 0,
			"Discount": 0,
			"UnitAffiliateCommission": 0,
			"UnitNetDiscountedPrice": 1,
			"GrossPrice": 1,
			"UnitGrossPrice": 1,
			"ItemUnitGrossPrice": 0,
			"VAT": 0,
			"AffiliateCommission": 0,
			"UnitGrossDiscountedPrice": 1,
			"ItemGrossPrice": 0,
			"GrossDiscountedPrice": 1,
			"UnitNetPrice": 1,
			"ItemUnitNetPrice": 0,
			"VATPercent": 0,
			"NetPrice": 1,
			"NetDiscountedPrice": 1,
			"HandlingFeeNetPrice": 0,
			"HandlingFeeGrossPrice": 0,
			"Currency": "usd",
			"ItemNetPrice": 0
		},
		"ProductDetails": {
			"Subscriptions": null,
			"Tangible": false,
			"RenewalStatus": false,
			"DeliveryInformation": {
				"Codes": [],
				"DownloadFile": null,
				"CodesDescription": "",
				"DeliveryDescription": "",
				"Delivery": "NO_DELIVERY"
			},
			"ExtraInfo": null,
			"IsDynamic": true,
			"Name": "Dynamic product",
			"ShortDescription": "Test description"
		},
		"LineItemReference": "a7b9d638d08ad8f234f8127cba6486e53dc15368",
		"SubscriptionCustomSettings": null,
		"SKU": null
	}],
	"Status": "AUTHRECEIVED",
	"LocalTime": null,
	"RefNo": "193342684",
	"AffiliateCommission": 0,
	"PartnerMarginPercent": null,
	"ExtraDiscount": null,
	"GrossDiscountedPrice": 1,
	"HasShipping": false,
	"Errors": null,
	"PODetails": null,
	"NetPrice": 1,
	"DeliveryFinalized": false,
	"MerchantCode": "250111206876",
	"Refunds": null,
	"GiftDetails": null,
	"ExtraMargin": null,
	"PayoutCurrency": "USD",
	"PaymentDetails": {
		"Type": "TEST",
		"Currency": "usd",
		"CustomerIP": "127.0.0.1",
		"PaymentMethod": {
			"Vendor3DSReturnURL": null,
			"Vendor3DSCancelURL": null,
			"CardType": "visa",
			"RecurringEnabled": false,
			"FirstDigits": "4111",
			"LastDigits": "1111",
			"InstallmentsNumber": null,
			"ExpirationMonth": "12",
			"ExpirationYear": "23"
		}
	},
	"WSOrder": null,
	"ApproveStatus": "WAITING",
	"Discount": 0,
	"Promotions": [],
	"GrossPrice": 1,
	"DeliveryDetails": {
		"Zip": "12345",
		"Company": null,
		"Email": "testcustomer@2Checkout.com",
		"Phone": null,
		"FirstName": "Customer",
		"State": "California",
		"Address2": null,
		"LastName": "2Checkout",
		"Address1": "Test Address",
		"City": "LA",
		"CountryCode": "us"
	},
	"FxRate": 1,
	"OrderNo": 0,
	"Source": "tcolib.local",
	"PartnerCode": null,
	"NetDiscountedPrice": 1,
	"Language": "en",
	"FxMarkup": 0,
	"AvangateCommission": 0.6,
	"ExtraMarginPercent": null,
	"CustomParameters": null,
	"PartnerMargin": null,
	"OrderFlow": "REGULAR",
	"AdditionalFields": null,
	"FinishDate": null,
	"BillingDetails": {
		"Zip": "12345",
		"Company": null,
		"Email": "testcustomer@2Checkout.com",
		"FiscalCode": null,
		"FirstName": "Customer",
		"Address2": null,
		"TaxOffice": null,
		"Address1": "Test Address",
		"City": "LA",
		"Phone": null,
		"State": "California",
		"LastName": "2Checkout",
		"CountryCode": "us"
	},
	"OrderDate": "2022-10-09 15:20:14",
	"ShopperRefNo": null,
	"DeliveryInformation": {
		"ShippingMethod": {
			"Comment": null,
			"TrackingNumber": null,
			"Code": null,
			"TrackingUrl": null
		}
	},
	"VendorApproveStatus": "OK"
}
```


Example IPN Usage:
------------------

*IPN Validation*

```java
TwocheckoutConfig config = new TwocheckoutConfig();
config.setMerchantCode("YOUR_MERCHANT_CODE");
config.setSecretWord("YOUR_SECRET_WORD");

// pass all parameters from IPN message as LinkedHashMap<String, String[]>
Ipn ipn = new Ipn(config);
Boolean valid = ipn.valid(parameters);
```


*Generate IPN Response*

```java
TwocheckoutConfig config = new TwocheckoutConfig();
config.setMerchantCode("YOUR_MERCHANT_CODE");
config.setSecretWord("YOUR_SECRET_WORD");

// pass parameters from IPN message as LinkedHashMap<String, String[]>
Ipn ipn = new Ipn(config);
String response = ipn.response(parameters, "20210701163700");
// <EPAYMENT>20210518112122|14942802c5c411489e408f512f69c5fe</EPAYMENT>
```


Example ConvertPlus Signature Generation
------------------

```java
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
HashMap<String, String> parameters = new HashMap<>();
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
parameters.put("merchant","YOUR_MERCHANT_CODE");
parameters.put("dynamic","1");

String buyLinkSignature = signature.create(parameters);
```


Exceptions:
------------------

TwocheckoutException exceptions are thrown by if an error has returned. It is best to catch these exceptions so that they can be gracefully handled in your application.

*Example Catch*

```java
try {
    ApiCore apiCore = new ApiCore(config);
    TwocheckoutResponse result = apiCore.call("/orders/", orderPayload, "POST");
} catch (TwocheckoutException e) {
    e.getMessage();
}
```
