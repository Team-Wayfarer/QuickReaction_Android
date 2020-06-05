package com.example.QuickReactionMJ;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://192.168.0.10:8080/cfcqr/api/spots/";
    private Map<String, String> map;

    public RegisterRequest(String contact, String name, Response.Listener listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("contact", contact);
        map.put("name", name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
