package cjmazur.homework.cs383.chirp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * @author Chris Mazur
 * @since 3/26/18
 *
 * Responsible for handling requests to and from the server
 */

public class RequestManager {

    private static final String BASE_URL = "http://10.0.2.2:5010";
    private RequestQueue requestQueue;

    public void sendListUsersRequest(Context c, ListUsersRequestHandler h) {
        final Context context = c;
        final ListUsersRequestHandler handler = h;
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL + "/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("HTTP", "response is: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP", "request has failed");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                            sendListUsersRequest(context, handler);
                        } catch (InterruptedException e) {
                            Log.d("THREAD", "Sending new request failed");
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        queue.add(stringRequest);
    }

    private RequestQueue getRequestQueue(Context c) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(c);
        return requestQueue;
    }

}
