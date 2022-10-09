package com.twocheckout.model.Api.Auth;

import com.twocheckout.exception.TwocheckoutException;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

@RequiredArgsConstructor
public class AuthApi {
    @NonNull
    private String merchantCode;
    @NonNull
    private String secretKey;

    public HashMap<String, String> getHeaders() throws TwocheckoutException {
        HashMap<String, String> headers = new HashMap<>();
        String hash = "";

        DateFormat df = new SimpleDateFormat("y-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmtDate = df.format(new Date());
        String finalString = this.merchantCode.length() + this.merchantCode + gmtDate.length() + gmtDate;
        hash = this.hmacDigest(finalString, this.secretKey, "HmacMD5");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("X-Avangate-Authentication", "code=\"" + this.merchantCode + "\" date=\"" + gmtDate + "\" hash=\"" + hash + "\"");
        return headers;
    }

    public String hmacDigest(String msg, String keyString, String algo) throws TwocheckoutException {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes(StandardCharsets.UTF_8), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes(StandardCharsets.US_ASCII));

            StringBuffer hash = new StringBuffer();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new TwocheckoutException("We encountered an error while creating the HMAC authentication.", 0, e);
        }
        return digest;
    }
}
