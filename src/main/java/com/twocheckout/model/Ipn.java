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

        String algorithm = getHashAlgorithm(parameters);
        String receivedRequestSignature;
        switch (algorithm) {
            case "HmacSHA3-256": receivedRequestSignature = parameters.get("SIGNATURE_SHA3_256")[0];
                break;
            case "HmacSHA256": receivedRequestSignature = parameters.get("SIGNATURE_SHA2_256")[0];
                break;
            default: receivedRequestSignature = parameters.get("HASH")[0];
                break;
        }

        String calculatedRequestSignature = calculateRequestSignature(parameters, algorithm);
        return calculatedRequestSignature.equals(receivedRequestSignature);
    }

    public String response(Map<String, String[]> parameters) throws TwocheckoutException {
        String algorithm = getHashAlgorithm(parameters);
        String responseDate = getResponseDate();
        String responseSignature = calculateResponseSignature(parameters, responseDate);
        return this.formatResponse(algorithm, responseDate, responseSignature);
    }

    public String response(Map<String, String[]> parameters, String date) throws TwocheckoutException {
        String algorithm = getHashAlgorithm(parameters);
        String responseSignature = calculateResponseSignature(parameters, date);
        return this.formatResponse(algorithm, date, responseSignature);
    }

    private String calculateResponseSignature(Map<String, String[]> parameters, String responseDate) throws TwocheckoutException {
        String algorithm = getHashAlgorithm(parameters);
        String responseStringToSign = "";
        try {
            responseStringToSign = parameters.get("IPN_PID[]")[0].length() + parameters.get("IPN_PID[]")[0] +
                    parameters.get("IPN_PNAME[]")[0].length() + parameters.get("IPN_PNAME[]")[0] +
                    parameters.get("IPN_DATE")[0].length() + parameters.get("IPN_DATE")[0] +
                    responseDate.length() + responseDate;
        } catch (NullPointerException e) {
            throw new TwocheckoutException("An error occurred during IPN validation check.", 0, e);
        }
        return hmac(responseStringToSign, algorithm);
    }

    private String getResponseDate() {
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    private String calculateRequestSignature(Map<String, String[]> parameters, String algorithm) throws TwocheckoutException {
        String requestStringToVerify = "";
        String[] hashKeys = {"HASH","SIGNATURE_SHA2_256","SIGNATURE_SHA3_256"};
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (Arrays.asList(hashKeys).contains(entry.getKey())) continue;
            ArrayList<String> valueList = new ArrayList<String>(Arrays.asList(entry.getValue()));
            requestStringToVerify = valueList
                    .stream()
                    .reduce(requestStringToVerify,
                            (partialResult, value)
                                    -> partialResult + (value == null ? 0 : value.getBytes().length + value));
        }

        return hmac(requestStringToVerify, algorithm);
    }

    private String hmac(String stringForHash, String algorithm) throws TwocheckoutException {
        try {
            SecretKeySpec key = new SecretKeySpec((this.twocheckoutConfig.getSecretKey()).getBytes(), "HmacMD5");
            Mac mac = Mac.getInstance(algorithm);
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

    private String getHashAlgorithm(Map<String, String[]> parameters) {
        String receivedAlgo = "HmacMD5";

        if (parameters.containsKey("SIGNATURE_SHA3_256")) {
            receivedAlgo ="HmacSHA3-256";
        } else if (parameters.containsKey("SIGNATURE_SHA2_256")) {
            receivedAlgo ="HmacSHA256";
        }

        return receivedAlgo;
    }

    private String formatResponse(String algorithm, String date, String signature) {
        String response;
        switch(algorithm) {
            case "HmacSHA3-256": response = "<sig algo=\"sha3-256\" date=\"" + date + "\">" + signature + "</sig>";
                break;
            case "HmacSHA256": response = "<sig algo=\"sha256\" date=\"" + date + "\">" + signature + "</sig>";
                break;
            default: response = "<EPAYMENT>" + date + "|" + signature + "</EPAYMENT>";
                break;
        }

        return response;
    }
}
