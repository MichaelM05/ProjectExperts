package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.mjb.projectexperts.Domain.VolleyS;
import com.mjb.projectexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    String points;
    MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_map, container, false);


        points = "";
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        makeRequest();
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        googleMap.setOnMarkerClickListener(this);
        if (!points.equals("")) {
            List<LatLng> latLngs = PolyUtil.decode(points);
            googleMap.addPolyline(new PolylineOptions().addAll(latLngs));
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(latLngs.get(0));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
            googleMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(0))
                    .title("Origen"));
            googleMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size()-1))
                    .title("Destino"));
        }
    }


    private void makeRequest(){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=Cartago,CR&destination=San+Jose,CR&key="+getString(R.string.google_api_key);
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //DO
                try {

                    JSONArray rutas =  jsonObject.getJSONArray("routes");
                    JSONObject rutasContenido = rutas.getJSONObject(0);
                    JSONObject ov_polyLine = rutasContenido.getJSONObject("overview_polyline");
                    points = ov_polyLine.getString("points");
                    updateMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            fRequestQueue.add(request);
        }
    }

    public void onPreStartConnection() {
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    public void updateMap(){
        mMapView.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getActivity(), "Latitude:" + marker.getPosition().latitude + ", Longitude:"
                + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
        DetailSiteFragment detailSiteFragment = new DetailSiteFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, detailSiteFragment, "detailSiteFragment");
        ft.addToBackStack("detailSiteFragment");
        ft.commit();
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}