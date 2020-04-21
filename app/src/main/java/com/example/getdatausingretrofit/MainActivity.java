package com.example.getdatausingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<HashMap<String, Object>> all_data = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         listView = findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Model> call = api.getStatus();
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonResponse = response.body().toString();
                        write(jsonResponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void write(String response) {

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if (obj.optString("success").equals("1")) {

                ArrayList<Model> modelArrayList = new ArrayList<>();
                JSONArray dataArray = obj.getJSONArray("retrofit_tbl");

                for (int i = 0; i < dataArray.length(); i++) {

                    Model model = new Model();
                    JSONObject dataObj = dataArray.getJSONObject(i);

                    model.setName(dataObj.getString("name"));
                    model.setLast_name(dataObj.getString("last_name"));
                    model.setStudent_number(dataObj.getString("student_number"));
                    modelArrayList.add(model);
                }

                for (int j = 0; j < modelArrayList.size(); j++) {

                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("Name", modelArrayList.get(j).getName());
                    hashMap.put("LastName", modelArrayList.get(j).getLast_name());
                    hashMap.put("StudentID", modelArrayList.get(j).getStudent_number());

                    all_data.add(hashMap);
                }


                String[] from = {"name", "last_name", "student_number"};

                int[] to = {R.id.txt_name, R.id.txt_last_name, R.id.txt_stu_id};

                SimpleAdapter myAdapter = new SimpleAdapter(
                        getBaseContext(), all_data,
                        R.layout.data_list_row, from, to
                );

                listView.setAdapter(myAdapter);

            } else {
                Toast.makeText(getApplicationContext(), obj.optString("message") + "", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
