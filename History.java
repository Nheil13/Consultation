import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;
import android.widget.Toast;

private void getVisitHistory(ArrayList<String> customerIDs) {
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append("http://your_server_url/get_visit_history.php?customerIDs=");

    for (int i = 0; i < customerIDs.size(); i++) {
        urlBuilder.append(customerIDs.get(i));

        if (i != customerIDs.size() - 1) {
            urlBuilder.append(",");
        }
    }

    String url = urlBuilder.toString();

    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // Process the JSON response and display the visit history data for each customer
                        for (String customerID : customerIDs) {
                            JSONArray jsonArray = response.getJSONArray(customerID);

                            if (jsonArray.length() > 0) {
                                // Visit history found for the current customer
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject visit = jsonArray.getJSONObject(i);

                                    String date = visit.getString("date");
                                    String time = visit.getString("time");
                                    String reason = visit.getString("reason");

                                    // Display or process the visit history data as needed
                                    Log.d("Visit History", "Customer ID: " + customerID + ", Date: " + date + ", Time: " + time + ", Reason: " + reason);
                                }
                            } else {
                                // No visit history found for the current customer
                                Log.d("Visit History", "No visit history found for Customer ID: " + customerID);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            });

    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(request);
}
