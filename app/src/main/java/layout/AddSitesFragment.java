package layout;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mjb.projectexperts.AddSiteAdapter;

import com.mjb.projectexperts.Domain.PredesignedRoute;
import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.Domain.User;

import com.mjb.projectexperts.Domain.Site;

import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSitesFragment extends Fragment {


    private RecyclerView recyclerView;
    private AddSiteAdapter adapter;
    private boolean flag;
    private ArrayList<Site> siteList;
    private ProgressDialog progressDialog;

    public AddSitesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_sites, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        siteList = new ArrayList<>();

        siteList = ((MenuActivity)getActivity()).sites;


        adapter = new AddSiteAdapter(this, siteList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new AddSitesFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setCancelable(false);
        Button btnAdd = (Button)v.findViewById(R.id.btn_create_route);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = ((MenuActivity) getActivity()).isUpdate;
                if(flag == false) {

                    if (createRoute(v.getContext())) {
                        Toast.makeText(v.getContext(), "Creación exitosa", Toast.LENGTH_LONG).show();
                        ((MenuActivity) getActivity()).sitesCreate.clear();
                        ModifyRouteFragment modifyRoutesFragment = new ModifyRouteFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, modifyRoutesFragment, "modifyRouteFragment");
                        ft.addToBackStack("modifyRouteFragment");
                        ft.commit();
                    } else {
                        Toast.makeText(v.getContext(), "Error al crear", Toast.LENGTH_LONG).show();
                    }
                }else if(updateRoute(v.getContext())){
                    Toast.makeText(v.getContext(), "Actualización exitosa", Toast.LENGTH_LONG).show();
                    ((MenuActivity) getActivity()).sitesCreate.clear();
                    ((MenuActivity) getActivity()).isUpdate = false;
                    ModifyRouteFragment modifyRoutesFragment = new ModifyRouteFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, modifyRoutesFragment, "modifyRouteFragment");
                    ft.addToBackStack("modifyRouteFragment");
                    ft.commit();

                }else{
                    Toast.makeText(v.getContext(), "Error al actualizar", Toast.LENGTH_LONG).show();
                }


            }
        });


        return v;
    }




    private boolean createRoute(final Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/predesignedroutes";

        ArrayList<Site> sites = ((MenuActivity) getActivity()).sitesCreate;
        ArrayList<Integer> idSites = new ArrayList<>();
        for (int i = 0; i < sites.size(); i++){
            idSites.add(sites.get(i).getIdSite());
        }
        int idUser = Integer.parseInt(((MenuActivity) getActivity()).user.getIdUser());
        String nameRoute = ((MenuActivity) getActivity()).parameters[5];
        PredesignedRoute predesignedRoute = new PredesignedRoute(nameRoute,idUser,idSites);

        Gson gson = new Gson();
        String json = gson.toJson(predesignedRoute);

        JsonObjectRequest request_json = null;
        try {
            request_json = new JsonObjectRequest(URL, new JSONObject(json),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            flag = createSuccess(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    flag = false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);
        ((MenuActivity) getActivity()).isUpdate = false;
        return flag;

    }


    private boolean updateRoute(final Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/predesignedroutes";

        ArrayList<Site> sites = ((MenuActivity) getActivity()).sitesCreate;
        ArrayList<Integer> idSites = new ArrayList<>();
        for (int i = 0; i < sites.size(); i++){
            idSites.add(sites.get(i).getIdSite());
        }
        int idUser = Integer.parseInt(((MenuActivity) getActivity()).user.getIdUser());
        int idRouteUpdate = ((MenuActivity) getActivity()).idRouteUpdate;
        String nameRoute = ((MenuActivity) getActivity()).nameUpdate;
        PredesignedRoute predesignedRoute = new PredesignedRoute(nameRoute,idUser,idRouteUpdate,idSites);

        Gson gson = new Gson();
        String json = gson.toJson(predesignedRoute);


        JsonObjectRequest request_json = null;
        try {
            request_json = new JsonObjectRequest(Request.Method.PUT,URL, new JSONObject(json),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            flag = createSuccess(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    flag = false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);

        return flag;

    }

    public boolean createSuccess(JSONObject response){

        try{
            if(response.getString("status").toString().equalsIgnoreCase("success")){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){}

        return false;
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
