package com.twocheckout.model.Api.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class AuthBuyLink {
    @NonNull
    private String merchantCode;
    @NonNull
    private String secretWord;
    public static long ttlMillis = 3600000;

    public String createJWT(long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        HashMap<String,Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS512");

        HashMap<String,Object> payload = new HashMap<>();
        payload.put("sub", this.merchantCode);
        payload.put("iat", now);
        payload.put("exp", exp);


        Algorithm algorithm = Algorithm.HMAC512(this.secretWord);
        return JWT.create()
                .withHeader(headers)
                .withPayload(payload)
                .sign(algorithm);
    }

    public String createJWT() {
        return this.createJWT(ttlMillis);
    }

    private static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String encode(JSONObject obj) {
        return encode(obj.toString().getBytes(StandardCharsets.UTF_8));
    }
}
