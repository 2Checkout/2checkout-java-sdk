package com.twocheckout.model.Api.Rest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@AllArgsConstructor
@Getter
@Setter
public class TwocheckoutResponse {
    int httpStatus;
    String body;
    JSONObject json;

    public TwocheckoutResponse(int code, String body) {
        this.httpStatus = code;
        this.body = body;
        if (body != null) {
            try {
                this.json = new JSONObject(body);
            } catch (JSONException e) {
                this.json = null;
            }
        }
    }
}
