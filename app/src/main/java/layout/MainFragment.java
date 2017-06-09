package layout;



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
import com.mjb.projectexperts.RouteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RouteAdapter adapter;
    private ArrayList<Route> routeList;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);


        ArrayList<Route> preRoute = ((MenuActivity)getActivity()).preRouteList;
        if(preRoute == null){
            searchRoutes(getContext(),this);
        }else{
            routeList = preRoute;
            adapter = new RouteAdapter(this,routeList);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new MainFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }





        Button btnbuscar = (Button)v.findViewById(R.id.btn_search);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDestinationFragment searchDestinationFragment = new SearchDestinationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, searchDestinationFragment, "searchDestinationFragment");
                ft.addToBackStack("searchDestinationFragment");
                ft.commit();
                //finish();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    private void searchRoutes(final Context context,final Fragment frag){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/predisignedroutes";

        JsonArrayRequest request_json = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(!parseRoutes(response)) {
                            Toast.makeText(getActivity(), "Hubo un error, intente de nuevo.", Toast.LENGTH_SHORT).show();
                        }else{
                            adapter = new RouteAdapter(frag,routeList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.addItemDecoration(new MainFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage()+" !");
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
                    JSONArray images = jsonSite.getJSONArray("images");
                    for (int k = 0; k < images.length(); k++) {
                        sitio.getImagesSite().add(images.getString(k));
                    }
                    JSONArray videos = jsonSite.getJSONArray("videos");
                    sitio.setPathVideo(videos.getString(0));
                    sitios.add(sitio);
                }
                route.setSites(sitios);
                routes.add(route);
            }
        }catch (JSONException ex){
            return false;
        }
        ((MenuActivity) getActivity()).preRouteList = routes;
        return true;
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
