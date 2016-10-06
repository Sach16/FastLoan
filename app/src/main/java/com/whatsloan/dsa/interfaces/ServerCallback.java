package com.whatsloan.dsa.interfaces;

import com.android.volley.VolleyError;


/**
 * @author Ramesh S
 * Callback Interface used for Server API Response
 */
public interface ServerCallback<T> {
    public abstract void complete(int code);

    /**
     * @param response
     * @param apiMethod
     */
    public abstract void onAPIResponse(Object response, String apiMethod);

    /**
     * @param error
     * @param apiMethod
     */
    public abstract void onErrorResponse(VolleyError error, String apiMethod);
};
