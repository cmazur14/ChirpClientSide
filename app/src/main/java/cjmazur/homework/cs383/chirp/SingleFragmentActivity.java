package cjmazur.homework.cs383.chirp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        //now there's a comment in it!
    }

//    private void sendListOfUsersRequest() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://10.0.2.2:5010/users";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response, new Response.ErrorListener());
//        queue.add(stringRequest);
//    }

}
