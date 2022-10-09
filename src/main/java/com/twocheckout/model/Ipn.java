package com.twocheckout.model;

import com.twocheckout.exception.TwocheckoutException;
import lombok.AllArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@AllArgsConstructor
public class Ipn {
    private TwocheckoutConfig twocheckoutConfig;

    public Boolean valid(Map<String, String[]> parameters) throws TwocheckoutException {
        String receivedRequestSignature = parameters.get("HASH")[0];
        String calculatedRequestSignature = calculateRequestSignature(parameters);
        return calculatedRequestSignature.equals(receivedRequestSignature);
    }

    public String response(Map<String, String[]> parameters) throws TwocheckoutException {
        String responseDate = getResponseDate();
        String responseSignature = calculateResponseSignature(parameters, responseDate);
        return "<EPAYMENT>" + responseDate + "|" + responseSignature + "</EPAYMENT>";
    }

    public String response(Map<String, String[]> parameters, String date) throws TwocheckoutException {
        String responseSignature = calculateResponseSignature(parameters, date);
        return "<EPAYMENT>" + date + "|" + responseSignature + "</EPAYMENT>";
    }

    private String calculateResponseSignature(Map<String, String[]> parameters, String responseDate) throws TwocheckoutException {
        String responseStringToSign = "";
        try {
            responseStringToSign = parameters.get("IPN_PID[]")[0].length() + parameters.get("IPN_PID[]")[0] +
                    parameters.get("IPN_PNAME[]")[0].length() + parameters.get("IPN_PNAME[]")[0] +
                    parameters.get("IPN_DATE")[0].length() + parameters.get("IPN_DATE")[0] +
                    responseDate.length() + responseDate;
        } catch (NullPointerException e) {
            throw new TwocheckoutException("An error occurred during IPN validation check.", 0, e);
        }
        return hmac(responseStringToSign);
    }

    private String getResponseDate() {
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    private String calculateRequestSignature(Map<String, String[]> parameters) throws TwocheckoutException {
        String requestStringToVerify = "";
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (entry.getKey().equals("HASH")) continue;
            ArrayList<String> valueList = new ArrayList<String>(Arrays.asList(entry.getValue()));
            requestStringToVerify = valueList
                    .stream()
                    .reduce(requestStringToVerify,
                            (partialResult, value)
                                    -> partialResult + (value == null ? 0 : value.getBytes().length + value));
        }
        return hmac(requestStringToVerify);
    }

    private String hmac(String stringForHash) throws TwocheckoutException {
        try {
            SecretKeySpec key = new SecretKeySpec((this.twocheckoutConfig.getSecretKey()).getBytes(), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);
            byte[] bytes = mac.doFinal(stringForHash.getBytes());
            return toHexString(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NullPointerException e) {
            throw new TwocheckoutException("An error occurred during IPN validation check.", 0, e);
        }
    }

    private String toHexString(byte[] bytes) {
        Formatter form = new Formatter();
        String result;

        for (byte b : bytes) {
            form.format("%02x", b);
        }
        result = form.toString();
        form.close();
        return result;
    }
}
