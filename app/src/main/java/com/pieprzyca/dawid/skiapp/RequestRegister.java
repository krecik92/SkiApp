package com.pieprzyca.dawid.skiapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dawid Pieprzyca on 16.04.2017.
 * RequestRegister is responsible for get params from database
 */
public class RequestRegister extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://dawidpieprzyca.hostei.com/Register.php";
    private Map<String,String> params;

    public RequestRegister(String name, String userName, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("userName", userName);
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * getParams()
     * @return params from labels.
     */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
