package cjmazur.homework.cs383.chirp.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import cjmazur.homework.cs383.chirp.volley.RequestQueueSingleton;

/**
 * @author Chris Mazur
 * @since 3/26/18
 *
 * Responsible for handling requests to and from the server
 */

//TODO deprecate this

public class RequestManager {

    private static RequestManager mInstance;

    private final String BASE_URL = "http://10.0.2.2:5010";
    private RequestQueue requestQueue;

    private RequestManager(Context c) {
        requestQueue = getRequestQueue(c);
    }

    public static RequestManager getInstance(Context c) {
        if (mInstance == null) mInstance = new RequestManager(c);
        return mInstance;
    }

    public void sendListUsersRequest(Context c) {
        final Context context = c;
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL + "/users";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("HTTP", "response is: " + response);
                Gson gson = new Gson();
                User[] users = gson.fromJson(response, User[].class);
                ListUsersHandler.getInstance().setUsersList(users);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP", "request has failed");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            sendListUsersRequest(context);
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

    public synchronized void sendUserVerificationRequest(Context c, String username, String password) {
        final String pw = password;
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL + "/users/" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("HTTP", "response is: " + response);
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);
                if (user.getPassword().equals(pw))
                    ActiveUserHandler.getInstance().setUser(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP", "request has failed");
            }
        });

        queue.add(stringRequest);
    }

    private RequestQueue getRequestQueue(Context c) {
        if (requestQueue == null)
            requestQueue = RequestQueueSingleton.getInstance(c).getRequestQueue();
        return requestQueue;
    }

}
