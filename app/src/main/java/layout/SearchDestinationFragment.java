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

import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import static android.os.Build.VERSION_CODES.M;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDestinationFragment extends Fragment
        {

    String[] duracion = {"1 HORA","2 HORA","3 HORA"};
    String[] actividad = {"Actividad 1","Actividad 2"};
    String[] distancia = {"1 KM","2 KM"};
    String[] precio = {"100","200"};
    String[] partida = {"Mi ubicación","San Jose"};
            // The minimum distance to change Updates in meters
            private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
            // The minimum time between updates in milliseconds
            private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
            protected LocationManager locationManager;
            protected Context context;
            protected boolean gps_enabled, network_enabled;
            private LocationListener locationListener;
            ProgressDialog progressDialog;
            Location lastLocation;


    public SearchDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_destination, container, false);

        lastLocation = ((MenuActivity)getActivity()).lastLocation;

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerLocation = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_distancia);
        materialDesignSpinnerLocation.setAdapter(arrayAdapterLocation);
        materialDesignSpinnerLocation.setText(distancia[0]);

        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, actividad);
        MaterialBetterSpinner materialDesignSpinnerActivity = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_actividad);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);
        materialDesignSpinnerActivity.setText(actividad[0]);

        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, duracion);
        MaterialBetterSpinner materialDesignSpinnerTT = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_duracion);
        materialDesignSpinnerTT.setAdapter(arrayAdapterTT);
        materialDesignSpinnerTT.setText(duracion[0]);

        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, precio);
        MaterialBetterSpinner materialDesignSpinnerPrice = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_price);
        materialDesignSpinnerPrice.setAdapter(arrayAdapterPrice);
        materialDesignSpinnerPrice.setText(precio[0]);

        ArrayAdapter<String> arrayAdapterOrigin = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, partida);
        MaterialBetterSpinner materialDesignSpinnerOrigin = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_origin);
        materialDesignSpinnerOrigin.setAdapter(arrayAdapterOrigin);
        materialDesignSpinnerOrigin.setText(partida[0]);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setTitle("Buscando su ubicación");
        progressDialog.setCancelable(false);





        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_search);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RoutesFoundFragment routesFoundFragment = new RoutesFoundFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, routesFoundFragment, "routesFoundFragment");
                //ft.commit();

                if(lastLocation == null){
                    getLocation();
                    progressDialog.show();
                }else{
                    Toast.makeText(getActivity(), "Latitude:" + lastLocation.getLatitude() + ", Longitude:"
                            + lastLocation.getLongitude(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), "Latitude:" + lastLocation.getLatitude() + ", Longitude:"
                                + lastLocation.getLongitude(), Toast.LENGTH_LONG).show();
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
}

