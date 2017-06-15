package com.pieprzyca.dawid.skiapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dawid on 17.04.2017.
 */
public class RequestLogin extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://dawidpieprzyca.hostei.com/Login.php";
    private Map<String,String> params;

    public RequestLogin(String userName, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userName", userName);
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
