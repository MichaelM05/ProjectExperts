package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.GridViewImageAdapter;
import com.mjb.projectexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailSiteFragment extends Fragment {


    public String lat,leng, video;


    public DetailSiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_site, container, false);


        Button btnAccept = (Button)v.findViewById(R.id.btn_view_video);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoRouteFragment videoRouteFragment = new VideoRouteFragment();
                videoRouteFragment.url = video;
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, videoRouteFragment, "videoRouteFragment");
                ft.addToBackStack("videoRouteFragment");
                ft.commit();

            }
        });
        getSite(getContext(),lat,leng,v);
        return v;
    }


    private void getSite(final Context context, final String lat, final String leng,final View v){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/touristicplaces";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("latitude",lat);
        params.put("length",leng);

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            TextView name = (TextView)v.findViewById(R.id.txvNameSite);
                            TextView description = (TextView)v.findViewById(R.id.txvDescriptionSite);
                            name.setText(response.getString("nameTouristicPlace"));
                            description.setText(response.getString("descriptionTouristicPlace"));
                            ArrayList<String> images = new ArrayList<>();
                            JSONArray imagesSite = response.getJSONArray("images");
                            for (int i = 0; i < imagesSite.length() ; i++) {
                                images.add(imagesSite.getString(i));
                            }

                            GridView gridView = (GridView) v.findViewById( R.id.GridView1 );
                            gridView.setAdapter( new GridViewImageAdapter(context,images) );
                            JSONArray videos = response.getJSONArray("videos");
                            video = videos.getString(0);
                            if(!video.equals("")){
                                Button btnAccept = (Button)v.findViewById(R.id.btn_view_video);
                                btnAccept.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException ex){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getActivity(), "Error problemas de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);



    }

}
