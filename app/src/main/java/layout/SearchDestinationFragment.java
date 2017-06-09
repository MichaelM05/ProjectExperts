package layout;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.Domain.Site;
import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.os.Build.VERSION_CODES.M;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDestinationFragment extends Fragment
        {

    String[] duracion = {"1 - 2 Horas","2 - 4 Horas","4 - 6 Horas","6 - 8 Horas","8 o más Horas"};
    String[] actividad = {"Cultural","Montaña","Ecológico","Recreativo"};
    String[] distancia = {"1 - 2 Km","2 - 4 Km","4 - 6 Km","6 - 8 Km","8 o más Km"};
    String[] precio = {"$0 - $5","$5 - $10","$10 - $20","$20 - $30","$30 o más"};
    String[] partida = {"Mi ubicación","San José","Cartago","Limón"};
    private boolean flag = false;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    protected LocationManager locationManager;
    protected Context context;
    protected boolean gps_enabled, network_enabled;
    private LocationListener locationListener;
    private ProgressDialog progressDialog;
    private Location lastLocation;
    private FragmentTransaction ft;
    private MaterialBetterSpinner materialDesignSpinnerDistance;
    private MaterialBetterSpinner materialDesignSpinnerActivity;
    private MaterialBetterSpinner materialDesignSpinnerPrice;
    private MaterialBetterSpinner materialDesignSpinnerTT;
    private MaterialBetterSpinner materialDesignSpinnerOrigin;
    private String lat,leng;

    public SearchDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_destination, container, false);

        lastLocation = ((MenuActivity)getActivity()).lastLocation;

        ArrayAdapter<String> arrayAdapterDistance = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, distancia);
        materialDesignSpinnerDistance = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_distancia);
        materialDesignSpinnerDistance.setAdapter(arrayAdapterDistance);


        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, actividad);
        materialDesignSpinnerActivity = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_actividad);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);


        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, duracion);
        materialDesignSpinnerTT = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_duracion);
        materialDesignSpinnerTT.setAdapter(arrayAdapterTT);


        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, precio);
        materialDesignSpinnerPrice = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_price);
        materialDesignSpinnerPrice.setAdapter(arrayAdapterPrice);


        ArrayAdapter<String> arrayAdapterOrigin = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, partida);
        materialDesignSpinnerOrigin = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_origin);
        materialDesignSpinnerOrigin.setAdapter(arrayAdapterOrigin);

        String [] parameters = ((MenuActivity) getActivity()).parameters;
        if( parameters != null){
            materialDesignSpinnerDistance.setText(parameters[0]);
            materialDesignSpinnerActivity.setText(parameters[1]);
            materialDesignSpinnerPrice.setText(parameters[2]);
            materialDesignSpinnerTT.setText(parameters[3]);
            materialDesignSpinnerOrigin.setText(parameters[4]);
        }else{
            materialDesignSpinnerDistance.setText(distancia[0]);
            materialDesignSpinnerActivity.setText(actividad[0]);
            materialDesignSpinnerTT.setText(duracion[0]);
            materialDesignSpinnerPrice.setText(precio[0]);
            materialDesignSpinnerOrigin.setText(partida[0]);
        }



        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");

        progressDialog.setCancelable(false);





        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_search);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] parameters = new String[5];
                parameters[0] = materialDesignSpinnerDistance.getText().toString();
                parameters[1] = materialDesignSpinnerActivity.getText().toString();
                parameters[2] = materialDesignSpinnerPrice.getText().toString();
                parameters[3] = materialDesignSpinnerTT.getText().toString();
                parameters[4] = materialDesignSpinnerOrigin.getText().toString();
                ((MenuActivity) getActivity()).parameters = parameters;


                if(parameters[4].equals("Mi ubicación")) {
                    if (lastLocation == null) {
                        getLocation();
                        progressDialog.show();
                        progressDialog.setTitle("Buscando su ubicación");
                    } else {
                        searchRoutes(getActivity(),lastLocation.getLatitude()+
                                "",lastLocation.getLongitude()+"");
                    }
                }else{
                    switch (parameters[4]){
                        case "Cartago":
                            lat = "9.862251741694937";
                            leng = "-83.91546249389648";
                            break;
                        case "San José":
                            lat = "9.915826049729528";
                            leng = "-84.06944274902344";
                            break;
                        case "Limón":
                            lat = "9.98805634887536";
                            leng = "-83.04376602172852";
                            break;
                    }
                    searchRoutes(v.getContext(),lat,leng);
                }



            }
        });

        return v;
    }



            public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
            private void checkLocationPermission() {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Location Permission Needed")
                                .setMessage("This app needs the Location permission, please accept to use location functionality")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Prompt the user once explanation has been shown
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_LOCATION );
                                    }
                                })
                                .create()
                                .show();


                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION );
                    }
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode,
                                                   String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case MY_PERMISSIONS_REQUEST_LOCATION: {
                        // If request is cancelled, the result arrays are empty.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            // permission was granted, yay! Do the
                            // location-related task you need to do.
                            if (ContextCompat.checkSelfPermission(getActivity(),
                                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {

                                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                                gps_enabled = locationManager
                                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                                network_enabled = locationManager
                                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                                if (gps_enabled) {
                                    locationManager.requestLocationUpdates(
                                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                                } else if (network_enabled) {
                                    locationManager.requestLocationUpdates(
                                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                                }



                            }

                        } else {

                            // permission denied, boo! Disable the
                            // functionality that depends on this permission.
                            Toast.makeText(getActivity(), "Permisos denegados", Toast.LENGTH_LONG).show();
                        }
                        return;
                    }

                    // other 'case' lines to check for other
                    // permissions this app might request
                }
            }

            public void getLocation(){

                locationListener = new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        ((MenuActivity)getActivity()).lastLocation = location;
                        lastLocation = location;
                        progressDialog.dismiss();
                        searchRoutes(getActivity(),lastLocation.getLatitude()+
                                "",lastLocation.getLongitude()+"");


                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Log.d("Latitude", "disable");
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Log.d("Latitude", "enable");
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.d("Latitude", "status");
                    }
                };
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                gps_enabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (android.os.Build.VERSION.SDK_INT >= M) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (gps_enabled) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        } else if (network_enabled) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        }
                    } else {
                        //Request Location Permission
                        checkLocationPermission();

                    }
                }
            }

            private void searchRoutes(final Context context,final String lat,final String leng){

                RequestQueue queue = Volley.newRequestQueue(context);
                final String URL = "http://rutascr.esy.es/WebServices/routes";
                final String[] parameters = ((MenuActivity) getActivity()).parameters;
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("activity",parameters[1]);
                params.put("price",parameters[2]);
                params.put("duration",parameters[3]);
                params.put("distance",parameters[0]);
                params.put("initPoint",lat+","+leng);
                progressDialog.setTitle("Buscando rutas");
                progressDialog.show();

                JsonArrayRequest request_json = new JsonArrayRequest(URL, new JSONObject(params),
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if(parseRoutes(response)) {
                                    progressDialog.dismiss();
                                    RoutesFoundFragment routesFoundFragment = new RoutesFoundFragment();
                                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame, routesFoundFragment, "routesFoundFragment");
                                    ft.addToBackStack("routesFoundFragment");
                                    ft.commit();
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Hubo un error, intente de nuevo.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage()+" !");
                        Toast.makeText(getActivity(), "Error problemas de conexión", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });
                int socketTimeout = 15000; // 30 seconds. You can change it
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request_json.setRetryPolicy(policy);
                queue.add(request_json);
            }

            private boolean parseRoutes(JSONArray response){
                ArrayList<Route> routes = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray routesArray = response.getJSONArray(i);
                        Route route = new Route();
                        route.setNameRoute("Ruta "+(i+1));
                        ArrayList<Site> sitios = new ArrayList<>();
                        for (int j = 0; j < routesArray.length(); j++) {
                            Site sitio = new Site();
                            JSONObject jsonSite = routesArray.getJSONObject(j);
                            sitio.setIdSite(Integer.parseInt(jsonSite.getString("idTouristicPlace")));
                            sitio.setNameSite(jsonSite.getString("nameTouristicPlace"));
                            sitio.setDescriptionSite(jsonSite.getString("descriptionTouristicPlace"));
                            sitio.setLatSite(jsonSite.getString("latitude"));
                            sitio.setLengSite(jsonSite.getString("length"));
                            sitio.setPriceSite((jsonSite.getString("price").equals(""))?0:Integer.parseInt(jsonSite.getString("price")));
                            sitio.setTypeActivity(jsonSite.getString("typeActivity"));
                            sitios.add(sitio);
                        }
                        route.setSites(sitios);
                        routes.add(route);
                    }
                }catch (JSONException ex){
                    return false;
                }
                ((MenuActivity) getActivity()).routeList = routes;
                return true;
            }
}

